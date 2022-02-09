package com.example.cinema.blImpl.promotion;

import com.example.cinema.bl.bankcard.BankCardService;
import com.example.cinema.bl.management.ScheduleService;
import com.example.cinema.bl.promotion.ActivityService;
import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.bl.sales.TicketService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.records.ChargeRecordMapper;
import com.example.cinema.po.*;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    ChargeRecordMapper chargeRecordMapper;
    @Autowired
    BankCardService bankCardService;
    @Autowired
    TicketService ticketService;
    @Autowired
    ScheduleService scheduleService;
    @Autowired
    CouponService couponService;
    @Autowired
    ActivityService activityService;
    @Override
    public ResponseVO addVIPCard(int userId,BankCardForm bankCardForm) {
        BankCard bankCard=bankCardService.selectCardByNumber(bankCardForm.getCardNumber());
        if(bankCard==null){
            return ResponseVO.buildFailure("银行卡不存在");
        }
        if(bankCardForm.getPassword()!=bankCard.getPassword()){
            return ResponseVO.buildFailure("密码错误");
        }
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        if(bankCard.getcardBalance()<25){
            return ResponseVO.buildFailure("银行卡余额不足");
        }
        bankCardForm.setcardBalance(bankCard.getcardBalance()-25);
        bankCardService.changeCardBalance(bankCardForm);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        List<VIPDiscountStrategy> vipDiscountStrategyList=vipCardMapper.selectAllDiscountStrategy();
        String description="";
        for(VIPDiscountStrategy vipDiscountStrategy:vipDiscountStrategyList){
            description+="满";
            description+=String.valueOf(vipDiscountStrategy.getQuota());
            description+="送";
            description+=String.valueOf(vipDiscountStrategy.getGift());
            description+=";";
        }
        vipInfoVO.setDescription(description);
        vipInfoVO.setPrice(VIPCard.price);
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPchargeVO viPchargeVO) {
        VIPCard vipCard = vipCardMapper.selectCardById(viPchargeVO.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        BankCard bankCard=bankCardService.selectCardByNumber(viPchargeVO.getCardNumber());
        if(bankCard==null){
            return ResponseVO.buildFailure("银行卡不存在");
        }
        if(viPchargeVO.getPassword()!=bankCard.getPassword()){
            return ResponseVO.buildFailure("密码错误");
        }
        //以下几行是通过要充的数值，找到相应的优惠策略并存入这个VIPCard中

        double amount = viPchargeVO.getAmount();
        int nearestInt = (int) amount;
        if(bankCard.getcardBalance()<amount){
            return ResponseVO.buildFailure("银行卡余额不足");
        }
        BankCardForm bankCardForm=new BankCardForm();
        bankCardForm.setCardNumber(viPchargeVO.getCardNumber());
        bankCardForm.setcardBalance(bankCard.getcardBalance()-amount);
        bankCardService.changeCardBalance(bankCardForm);
        VIPDiscountStrategy vipDiscountStrategy = vipCardMapper.selectSuitableDiscountStrategy(nearestInt);
        if (vipDiscountStrategy != null) {
            vipCard.setQuota(vipDiscountStrategy.getQuota());
            vipCard.setGift(vipDiscountStrategy.getGift());
        }
        double balance = vipCard.calculate(viPchargeVO.getAmount());
        /*
         * 以下几行用来记录充值记录
         * */

        ChargeRecord chargeRecord = new ChargeRecord();
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        chargeRecord.setTime(currentTime);
        chargeRecord.setAmount(amount);
        chargeRecord.setVipID(viPchargeVO.getVipId());
        if (vipDiscountStrategy == null) {
            chargeRecord.setGift(0);
            chargeRecord.setRental(amount);
        } else{
            chargeRecord.setGift(vipDiscountStrategy.getGift());
        double rental = amount + (double) vipDiscountStrategy.getGift();
        chargeRecord.setRental(rental);
    }
        chargeRecord.setBalance(balance);
        chargeRecordMapper.insertChargeRecord(chargeRecord);


        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            vipCardMapper.updateCardBalance(viPchargeVO.getVipId(), vipCard.getBalance());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO refund(VIPCardForm vipCardForm){
        try {
            VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCardForm.getAmount()+vipCard.getBalance());
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            else {
                return ResponseVO.buildSuccess(vipCard);
            }
            } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO cardBuy(VIPCardForm vipCardForm){
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double amount = vipCardForm.getAmount();
        vipCard.setBalance(vipCard.getBalance() - amount);
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public     ResponseVO addVIPDiscountStrategy(int quota,int gift) {
        //quota是满200赠20的200（定额），gift是赠额
        try {
            if (vipCardMapper.selectDiscountStrategyByQuota(quota)!=null){
                return ResponseVO.buildFailure("存在同等满额优惠策略，请修改");
            }
            vipCardMapper.insertDiscountStrategy(quota, gift);
            VIPDiscountStrategy vipDiscountStrategy=vipCardMapper.selectDiscountStrategyByQuota(quota);
            VIPDiscountStrategyVO vipDiscountStrategyVO=vipDiscountStrategy.getVO();

            return ResponseVO.buildSuccess(vipDiscountStrategyVO);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getDiscountStrategy(){
        try {
            List<VIPDiscountStrategy> vipDiscountStrategyList=vipCardMapper.selectAllDiscountStrategy();
            List<VIPDiscountStrategyVO> vipDiscountStrategyVOList=new ArrayList<>();
            for(VIPDiscountStrategy vipDiscountStrategy:vipDiscountStrategyList){
                vipDiscountStrategyVOList.add(vipDiscountStrategy.getVO());
            }
            return ResponseVO.buildSuccess(vipDiscountStrategyVOList);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO clearBalance(VIPCardForm vipCardForm){
        try {
            int id=vipCardForm.getVipId();
            double balance=0.0;
            vipCardMapper.updateCardBalance(id,balance);
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


    @Override
    public ResponseVO updateStrategy(VIPDiscountStrategy vipDiscountStrategy) {
        try {
            int strategyID=vipDiscountStrategy.getId();
            int quota=vipDiscountStrategy.getQuota();
            int gift=vipDiscountStrategy.getGift();
            if (vipCardMapper.selectDiscountStrategyByQuota(quota)!=null){
                return ResponseVO.buildFailure("存在同等满额优惠策略，请修改");
            }
            vipCardMapper.updateDiscountStrategy(strategyID,quota,gift);
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteDiscountStrategy(int strategyID) {
        try {
            vipCardMapper.deleteDiscountStrategy(strategyID);
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getUserByAmount(int amount){
        try {
            List<VipCardConsumptionForm> vipCardConsumptionForms = new ArrayList<VipCardConsumptionForm>();
            List<VIPCard> vipCards = vipCardMapper.selectAllVip();
            for (VIPCard vipCard : vipCards) {
                List<Ticket> tickets =  ticketService.selectTicketByUser(vipCard.getUserId());
                int total = 0;
                for (Ticket ticket: tickets) {
                    ScheduleItemVO scheduleItemVO = (ScheduleItemVO) scheduleService.getScheduleById(ticket.getScheduleId()).getContent();
                    total = total + (int) scheduleItemVO.getFare();
                }
                if (total >= amount) {
                    VipCardConsumptionForm vipCardConsumptionForm = new VipCardConsumptionForm();
                    vipCardConsumptionForm.setAmount(total);
                    vipCardConsumptionForm.setUserId(vipCard.getUserId());
                    vipCardConsumptionForm.setVipCardId(vipCard.getId());
                    vipCardConsumptionForms.add(vipCardConsumptionForm);
                }
            }
            return ResponseVO.buildSuccess(vipCardConsumptionForms);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO giveCoupon(GiveCouponVO giveCouponVO){
        try {
            List<Integer> userId=giveCouponVO.getUserId();
            for(int i=0;i<userId.size();i++){
                couponService.issueCoupon(activityService.getActivityById(giveCouponVO.getactivityId()).getCoupon().getId(),userId.get(i));
            }
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO setCardPrice(double price) {
        try {
            VIPCard.price=price;
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
