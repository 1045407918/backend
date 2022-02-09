package com.example.cinema.bl.sales;

import com.example.cinema.po.Ticket;
import com.example.cinema.vo.BankCardForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.TicketForm;
import com.example.cinema.vo.RefundStrategyForm;

import java.util.List;


/**
 * Created by liying on 2019/4/16.
 */
public interface TicketService {
    /**
     * 锁座【增加票但状态为未付款】
     *
     * @param ticketForm
     * @return
     */
    ResponseVO addTicket(TicketForm ticketForm);

    /**
     * 完成购票【不使用会员卡】流程包括校验优惠券和根据优惠活动赠送优惠券
     *
     * @param id
     * @param couponId
     * @return
     */
    ResponseVO completeTicket(List<Integer> id, int couponId, BankCardForm bankCardForm);

    /**
     * 获得该场次的被锁座位和场次信息
     *
     * @param scheduleId
     * @return
     */
    ResponseVO getBySchedule(int scheduleId);

    /**
     * 获得用户买过的票
     *
     * @param userId
     * @return
     */
    ResponseVO getTicketByUser(int userId);

    List<Ticket> selectTicketByUser(int userId);

    /**
     * 完成购票【使用会员卡】流程包括会员卡扣费、校验优惠券和根据优惠活动赠送优惠券
     *
     * @param id
     * @param couponId
     * @return
     */
    ResponseVO completeByVIPCard(List<Integer> id, int couponId);

    /**
     * 取消锁座（只有状态是"锁定中"的可以取消）
     *
     * @param id
     * @return
     */
    ResponseVO cancelTicket(List<Integer> id);

    /**
     * 退票
     * @param id
     * @return
     */
    ResponseVO  refundTickets(List<Integer> id);

    /**
     * 新增退票策略
     *
     * @param \
     * @return
     */
    ResponseVO  addRefundStrategy (RefundStrategyForm refundStrategyForm);


    /**
     * 修改退票策略
     *
     * @param \
     * @return
     */
    ResponseVO  updateRefundStrategy (RefundStrategyForm refundStrategyForm);

    ResponseVO selectAllRefundStrategy();

    ResponseVO useRefundStrategyById(int id);

    ResponseVO getTicketWithOrderID(int orderID);
}
