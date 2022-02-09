package com.example.cinema.data.sales;

import com.example.cinema.po.RefundStrategy;
import com.example.cinema.po.Ticket;
import com.example.cinema.vo.RefundStrategyForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

/**
 * Created by liying on 2019/4/16.
 */
@Mapper
@Repository
public interface TicketMapper {

    int insertTicket(Ticket ticket);

    int insertTickets(List<Ticket> tickets);

    void deleteTicket(int ticketId);

    void updateTicketState(@Param("ticketId") int ticketId, @Param("state") int state);

    void updateTicketCanRefund(@Param("ticketId") int ticketId, @Param("canRefund") int canRefund);

    List<Ticket> selectTicketsBySchedule(int scheduleId);

    List<Ticket> selectTicketsByOrderID(int orderID);

    Ticket selectTicketByScheduleIdAndSeat(@Param("scheduleId") int scheduleId, @Param("column") int columnIndex, @Param("row") int rowIndex);

    Ticket selectTicketById(int id);

    List<Ticket> selectTicketByUser(@Param("userID")int userId);

    int insertRefundStrategy(RefundStrategy refundStrategy);

    void updateRefundStrategy(RefundStrategy refundStrategy);

    void updateRefundStrategyByInUse(@Param("inUse") int inuse);

    List<RefundStrategy> selectAllRefundStrategy();

    void deleteRefundStrategy(int id);

    RefundStrategy getRefundStrategy(@Param("inUse") int inUse);

    RefundStrategy getRefundStrategyById(@Param("id") int id);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredTicket();
}

