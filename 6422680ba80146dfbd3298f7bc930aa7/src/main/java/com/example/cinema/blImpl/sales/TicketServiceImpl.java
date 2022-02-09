package com.example.cinema.blImpl.sales;

import com.example.cinema.bl.bankcard.BankCardService;
import com.example.cinema.bl.management.MovieService;
import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.bl.records.RecordsService;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.blImpl.management.hall.HallServiceForBl;
import com.example.cinema.blImpl.management.schedule.ScheduleServiceForBl;
import com.example.cinema.data.records.ComsumptionRecordMapper;
import com.example.cinema.data.sales.TicketMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import java.text.*;
import java.util.Calendar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/16.
 */
@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    TicketMapper ticketMapper;
    @Autowired
    ScheduleServiceForBl scheduleService;
    @Autowired
    HallServiceForBl hallService;
    @Autowired
    CouponService couponService;
    @Autowired
    ActivityService activityService;
    @Autowired
    MovieService movieService;
    @Autowired
    VIPService vipService;
    @Autowired
    BankCardService bankCardService;
    @Autowired
    RecordsService recordsService;
    @Autowired
    ComsumptionRecordMapper comsumptionRecordMapper;

    @Override
    @Transactional
    public ResponseVO addTicket(TicketForm ticketForm) {
        try {
            ScheduleItem scheduleItem=scheduleService.getScheduleItemById(ticketForm.getScheduleId());
            List<TicketVO> tickets=new ArrayList<TicketVO>();
            List<Ticket> ticketList=makeTicketListViaTicktForm(ticketForm);

            double total=0.0;
            total=total+scheduleItem.getFare()*ticketList.size();
            OrderRecord orderRecord=new OrderRecord();
            Timestamp currenttime = new Timestamp(System.currentTimeMillis());
            orderRecord.setCreate_time(currenttime);
            orderRecord.setMovieID(scheduleItem.getMovieId());
            orderRecord.setScheduleID(scheduleItem.getId());
            orderRecord.setState(0);
            orderRecord.setInitial_amount(total);
            orderRecord.setUser_id(ticketForm.getUserId());
            comsumptionRecordMapper.insertOrderRecord(orderRecord);
            for(Ticket ticket:ticketList){
                ticket.setOrder_id(orderRecord.getOrderID());
                ticketMapper.insertTicket(ticket);
                ticketMapper.updateTicketCanRefund(ticket.getId(), 1);
                tickets.add(ticket.getVO());
            }
            return ResponseVO.buildSuccess(orderRecord);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    @Transactional
    public ResponseVO completeTicket(List<Integer> id, int couponId,BankCardForm bankCardForm) {
        try {
            BankCard bankCard=bankCardService.selectCardByNumber(bankCardForm.getCardNumber());
            if(bankCard==null){
                return ResponseVO.buildFailure("银行卡不存在");
            }
            if(bankCardForm.getPassword()!=bankCard.getPassword()){
                return ResponseVO.buildFailure("密码错误");
            }
            int schid=10;
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketCanRefund(id.get(i),1);
                Ticket ticket=ticketMapper.selectTicketById(id.get(0));
                schid=ticket.getScheduleId();
            }
            ScheduleItem schedules = scheduleService.getScheduleItemById(schid);
            List<ActivityVO> activities = (List<ActivityVO>)activityService.getActivities().getContent();
            List<Coupon> coupons=new ArrayList<Coupon>();
            List<TicketVO> tickets=new ArrayList<TicketVO>();
            List<ActivityVO> usedActivities = new ArrayList<ActivityVO>();
            double total=0.0;

            /*
            * 用来传消费记录
            * */
            List<ComsumptionRecord> comsumptionRecords=new ArrayList<>();

            for(int i=0;i<id.size();i++){
                tickets.add(ticketMapper.selectTicketById(id.get(i)).getVO());

                /*向消费记录列表里添加记录*/
                ComsumptionRecord comsumptionRecord=new ComsumptionRecord();
                comsumptionRecord.setTicketID(id.get(i));
                comsumptionRecords.add(comsumptionRecord);

            }
            int userId=tickets.get(0).getUserId();
            int orderID=tickets.get(0).getOrderID();
            OrderRecord orderRecord=comsumptionRecordMapper.selectOrderRecordByOrderID(orderID);

            /*
             * 用来传消费记录，记录用户id
             * */
            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setUserID(userId);
            }

            for(int i=0;i<id.size();i++){
                total=total+schedules.getFare();
            }
            /*改初始总额*/
            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setTotal_origin(total);
                comsumptionRecord.setTotal_result(total);
            }
            Timestamp currenttime = new Timestamp(System.currentTimeMillis());

            /*
             * 用来传消费记录，记录时间
             * */
            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setTime(currenttime);
            }

            if(couponId!=0) {
                Coupon coupon = couponService.getCouponById(couponId);
                if (currenttime.before(coupon.getEndTime()) && currenttime.after(coupon.getStartTime()) && total >= coupon.getTargetAmount()) {
                    double discountAmount = coupon.getDiscountAmount();
                    coupons.add(coupon);
                    total = total - discountAmount;//符合优惠券使用条件得到总价
                    couponService.usedCoupon(couponId, userId);
                    for(int i=0;i<id.size();i++) {
                        ticketMapper.updateTicketCanRefund(id.get(i), 0);
                    }//特价票不允许退票

                    /*
                     * 用来传消费记录，记录使用的优惠券和折后总额
                     * */
                    for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                        comsumptionRecord.setCouponID_used(couponId);
                        comsumptionRecord.setTotal_result(total);
                    }
                }
            }
            for(int i=0;i<activities.size();i++){
                ActivityVO activity=activities.get(i);
                List<MovieVO> movies=activity.getMovieList();
                if(currenttime.before(activity.getEndTime()) && currenttime.after(activity.getStartTime())){
                    for(int j=0;j<id.size();j++){
                        MovieVO movie= new MovieVO(movieService.getMovieById(schedules.getMovieId()));
                        for(int k=0;k<movies.size();k++){
                            if(movies.get(k)==movie){
                                Coupon newCoupon=activity.getCoupon();//符合活动时间及电影送新优惠券
                                usedActivities.add(activity);
                                couponService.issueCoupon(newCoupon.getId(),userId);
                                for(int s=0;s<id.size();s++) {
                                    ticketMapper.updateTicketCanRefund(id.get(s), 0);
                                }//特价票不允许退票
                            }
                        }
                    }
                }
            }
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketState(id.get(i),1);
            }
            if(bankCard.getcardBalance()<total){
                return ResponseVO.buildFailure("银行卡余额不足");
            }
            bankCardForm.setcardBalance(bankCard.getcardBalance()-total);
            bankCardService.changeCardBalance(bankCardForm);

            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setOrderID(orderID);
                comsumptionRecord.setBankCardNumber(bankCardForm.getCardNumber());
                comsumptionRecordMapper.insertComsumptionRecord(comsumptionRecord);
            }
            orderRecord.setState(1);
            orderRecord.setPayment_method(1);
            orderRecord.setPayed_time(currenttime);
            orderRecord.setActual_payed_amount(total);
            comsumptionRecordMapper.updateOrderRecord(orderRecord);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    @Override
    public ResponseVO getBySchedule(int scheduleId) {
        try {
            List<Ticket> tickets = ticketMapper.selectTicketsBySchedule(scheduleId);
            ScheduleItem schedule=scheduleService.getScheduleItemById(scheduleId);
            Hall hall=hallService.getHallById(schedule.getHallId());
            int[][] seats=new int[hall.getRow()][hall.getColumn()];
            tickets.stream().forEach(ticket -> {
                seats[ticket.getRowIndex()][ticket.getColumnIndex()]=1;
            });
            ScheduleWithSeatVO scheduleWithSeatVO=new ScheduleWithSeatVO();
            scheduleWithSeatVO.setScheduleItem(schedule);
            scheduleWithSeatVO.setSeats(seats);
            return ResponseVO.buildSuccess(scheduleWithSeatVO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketByUser(int userId) {
        try {
            List<Ticket> ticketList = ticketMapper.selectTicketByUser(userId);
            List<TicketInfoVO> ticketInfoVOList = new ArrayList<>();
            for (Ticket ticket : ticketList) {
                ScheduleItem scheduleItem = scheduleService.getScheduleItemById(ticket.getScheduleId());
                TicketInfoVO ticketInfoVO = ticket.getInfoVoWithScheduleItem(scheduleItem);
                ticketInfoVO.setCanRefund(ticket.getCanRefund());
                ticketInfoVO.setTickid(ticket.getId());
                ticketInfoVOList.add(ticketInfoVO);
            }
            return ResponseVO.buildSuccess(ticketInfoVOList);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public List<Ticket> selectTicketByUser(int userId){
        try {
            List<Ticket> ticketList = ticketMapper.selectTicketByUser(userId);
            return ticketList;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    @Transactional
    public ResponseVO completeByVIPCard(List<Integer> id, int couponId) {
        try {
            int schid=0;
            for(int i=0;i<id.size();i++){
                ticketMapper.updateTicketCanRefund(id.get(i), 1);
                Ticket ticket=ticketMapper.selectTicketById(id.get(0));
                schid=ticket.getScheduleId();
            }
            ScheduleItem schedules = scheduleService.getScheduleItemById(schid);
            List<ActivityVO> activities = (List<ActivityVO>)activityService.getActivities().getContent();
            List<Coupon> coupons=new ArrayList<Coupon>();
            List<TicketVO> tickets=new ArrayList<TicketVO>();
            List<ActivityVO> usedActivities = new ArrayList<ActivityVO>();
            double total=0.0;

            /*
             * 用来传消费记录
             * */
            List<ComsumptionRecord> comsumptionRecords=new ArrayList<>();

            for(int i=0;i<id.size();i++){
                tickets.add(ticketMapper.selectTicketById(id.get(i)).getVO());

                /*向消费记录列表里添加记录*/
                ComsumptionRecord comsumptionRecord=new ComsumptionRecord();
                comsumptionRecord.setTicketID(id.get(i));
                comsumptionRecords.add(comsumptionRecord);

            }
            int userId=tickets.get(0).getUserId();
            int orderID=tickets.get(0).getOrderID();
            OrderRecord orderRecord=comsumptionRecordMapper.selectOrderRecordByOrderID(orderID);
            /*
             * 用来传消费记录，记录用户id
             * */
            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setUserID(userId);
            }
            for(int i=0;i<id.size();i++){
                total=total+schedules.getFare();
            }
            /*改初始总额*/
            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setTotal_origin(total);
                comsumptionRecord.setTotal_result(total);
            }

            Timestamp currenttime=new Timestamp(System.currentTimeMillis());

            /*
             * 用来传消费记录，记录时间
             * */
            for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                comsumptionRecord.setTime(currenttime);
            }
            if(couponId!=0) {
                Coupon coupon = couponService.getCouponById(couponId);
                if (currenttime.before(coupon.getEndTime()) && currenttime.after(coupon.getStartTime()) && total >= coupon.getTargetAmount()) {
                    double discountAmount = coupon.getDiscountAmount();
                    coupons.add(coupon);
                    total = total - discountAmount;//符合优惠券使用条件得到总价
                    couponService.usedCoupon(couponId, userId);
                    for(int i=0;i<id.size();i++) {
                        ticketMapper.updateTicketCanRefund(id.get(i), 0);
                    }//特价票不允许退票
                    /*
                     * 用来传消费记录，记录使用的优惠券和折后总额
                     * */
                    for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                        comsumptionRecord.setCouponID_used(couponId);
                        comsumptionRecord.setTotal_result(total);
                    }
                }
            }
            for(int i=0;i<activities.size();i++){
                ActivityVO activity=activities.get(i);
                List<MovieVO> movies=activity.getMovieList();
                if(currenttime.before(activity.getEndTime()) && currenttime.after(activity.getStartTime())){
                        MovieVO movie= new MovieVO(movieService.getMovieById(schedules.getMovieId()));
                        for(int k=0;k<movies.size();k++){
                            if(movies.get(k)==movie){
                                Coupon newCoupon=activity.getCoupon();//符合活动时间及电影送新优惠券
                                usedActivities.add(activity);
                                couponService.issueCoupon(newCoupon.getId(),userId);
                                for(int s=0;s<id.size();s++) {
                                    ticketMapper.updateTicketCanRefund(id.get(s), 0);
                                }//特价票不允许退票
                            }
                        }
                }
            }
            if(vipService.getCardByUserId(userId).getSuccess()==true && ((VIPCard)vipService.getCardByUserId(userId).getContent()).getBalance()>=total){
                int cardId=((VIPCard)vipService.getCardByUserId(userId).getContent()).getId();
                VIPCardForm cardForm=new VIPCardForm();
                cardForm.setAmount(total);
                cardForm.setVipId(cardId);
                for(int i=0;i<id.size();i++){
                    ticketMapper.updateTicketState(id.get(i),1);
                }
                orderRecord.setState(1);
                orderRecord.setPayment_method(2);
                orderRecord.setPayed_time(currenttime);
                orderRecord.setActual_payed_amount(total);
                comsumptionRecordMapper.updateOrderRecord(orderRecord);

                for(ComsumptionRecord comsumptionRecord:comsumptionRecords){
                    comsumptionRecord.setOrderID(orderID);
                    comsumptionRecord.setVIPID(cardId);
                    comsumptionRecordMapper.insertComsumptionRecord(comsumptionRecord);
                }
                return vipService.cardBuy(cardForm);
            }//会员卡支付扣费
            else {
                return ResponseVO.buildFailure("失败");
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cancelTicket(List<Integer> id) {
        try {
            for(int ticketId:id){
                ticketMapper.deleteTicket(ticketId);
            }
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO refundTickets(List<Integer> ticketsid){
        try{
            List<Ticket> tickets=new ArrayList<Ticket>();
            for(int i=0;i<ticketsid.size();i++){
                tickets.add(ticketMapper.selectTicketById(ticketsid.get(i)));
            }
            if(tickets.get(0).getCanRefund()==0){
                return ResponseVO.buildFailure("优惠票无法退票");
            }
            RefundStrategy refundStrategy=ticketMapper.getRefundStrategy(1);
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            Date currentTime_2 = formatter.parse(dateString);
            Calendar ca=Calendar.getInstance();
            ca.setTime(currentTime_2);
            ca.add(Calendar.HOUR_OF_DAY, refundStrategy.getTime());
            Date dsss=ca.getTime();
            if(dsss.after(scheduleService.getScheduleItemById(tickets.get(0).getScheduleId()).getStartTime())){
                return ResponseVO.buildFailure("不在退票时间内");
            }
            List<ComsumptionRecord> comsumptionRecords=new ArrayList<ComsumptionRecord>();
            for(int i=0;i<ticketsid.size();i++){
                comsumptionRecords.add(recordsService.getComsumptionRecordByTicketID(ticketsid.get(i)));
            }
            ScheduleItem scheduleItem=scheduleService.getScheduleItemById(tickets.get(0).getScheduleId());
            double total=scheduleItem.getFare()*tickets.size();
            if (comsumptionRecords.get(0).getVIPID()==0){
                BankCardForm bankCardForm=new BankCardForm();
                bankCardForm.setcardBalance(comsumptionRecords.get(0).getBankCardNumber());
                bankCardForm.setPassword(bankCardService.selectCardByNumber(comsumptionRecords.get(0).getBankCardNumber()).getPassword());
                bankCardForm.setcardBalance(bankCardService.selectCardByNumber(comsumptionRecords.get(0).getBankCardNumber()).getcardBalance()+total);
                bankCardService.changeCardBalance(bankCardForm);
                for(int ticketId:ticketsid){
                    ticketMapper.deleteTicket(ticketId);
                }
                return ResponseVO.buildSuccess("已退款至银行卡");
            }
            else{
                VIPCardForm vipCardForm=new VIPCardForm();
                vipCardForm.setVipId(comsumptionRecords.get(0).getVIPID());
                vipCardForm.setAmount(total);
                for(int ticketId:ticketsid){
                    ticketMapper.deleteTicket(ticketId);
                }
                return vipService.refund(vipCardForm);
            }

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO  addRefundStrategy (RefundStrategyForm refundStrategyForm){
        try {
            RefundStrategy refundStrategy=new RefundStrategy();
            refundStrategy.setTime(refundStrategyForm.getTime());
            refundStrategy.setInUse(1);
            ticketMapper.updateRefundStrategyByInUse(0);
            ticketMapper.insertRefundStrategy(refundStrategy);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateRefundStrategy (RefundStrategyForm refundStrategyForm){
        try {
            RefundStrategy refundStrategy=new RefundStrategy();
            refundStrategy.setId(refundStrategyForm.getId());
            refundStrategy.setTime(refundStrategyForm.getTime());
            refundStrategy.setInUse(1);
            ticketMapper.updateRefundStrategyByInUse(0);
            ticketMapper.updateRefundStrategy(refundStrategy);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
    @Override
    public ResponseVO selectAllRefundStrategy(){
        try {
            return ResponseVO.buildSuccess(ticketMapper.selectAllRefundStrategy());
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    };

    @Override
    public ResponseVO useRefundStrategyById(int id){
        try {
            System.out.println(id);
            RefundStrategy refundStrategy=ticketMapper.getRefundStrategyById(id);
            refundStrategy.setInUse(1);
            ticketMapper.updateRefundStrategyByInUse(0);
            ticketMapper.updateRefundStrategy(refundStrategy);
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTicketWithOrderID(int orderID) {
        try{
            List<Ticket> tickets=ticketMapper.selectTicketsByOrderID(orderID);
            return ResponseVO.buildSuccess(tickets);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<Ticket> makeTicketListViaTicktForm(TicketForm ticketForm){
        int userId=ticketForm.getUserId();
        int scheduleId=ticketForm.getScheduleId();
        List<SeatForm> seatFormList=ticketForm.getSeats();
        List<Ticket> ticketList=new ArrayList<>();
        for(SeatForm seatForm:seatFormList){
            Ticket ticket=new Ticket();
            int columnIndex=seatForm.getColumnIndex();
            int rowIndex=seatForm.getRowIndex();
            ticket.setScheduleId(scheduleId);
            ticket.setUserId(userId);
            ticket.setColumnIndex(columnIndex);
            ticket.setRowIndex(rowIndex);
            ticket.setState(0);
            ticket.setCanRefund(1);
            ticketList.add(ticket);
        }
        return ticketList;
    }


}
