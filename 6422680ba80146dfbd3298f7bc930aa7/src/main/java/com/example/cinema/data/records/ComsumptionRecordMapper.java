package com.example.cinema.data.records;

import com.example.cinema.po.ComsumptionRecord;
import com.example.cinema.po.OrderRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ComsumptionRecordMapper {
    int insertComsumptionRecord(ComsumptionRecord comsumptionRecord);

    int insertOrderRecord(OrderRecord orderRecord);

    void updateOrderRecord(OrderRecord orderRecord);

    OrderRecord selectOrderRecordByOrderID(int orderID);

    List<OrderRecord> getOrderRecordsByUserID(int userID);

    List<ComsumptionRecord> getComsumptionRecords();

    List<ComsumptionRecord> getComsumptionRecordsByOrderID(int orderID);

    ComsumptionRecord getComsumptionRecordByTicketID(int ticketID);

    List<ComsumptionRecord> selectComsumptionRecordsByUserID(int UserID);

    @Scheduled(cron = "0/1 * * * * ?")
    void cleanExpiredOrder();

}
