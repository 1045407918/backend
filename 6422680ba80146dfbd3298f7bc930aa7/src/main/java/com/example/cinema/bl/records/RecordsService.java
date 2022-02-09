package com.example.cinema.bl.records;

import com.example.cinema.po.ComsumptionRecord;
import com.example.cinema.vo.ResponseVO;

public interface RecordsService {

    ResponseVO getChargeRecords();

    ResponseVO getChargeRecordsById(int recordId);

    ResponseVO getAllComsumptionRecords();

    ResponseVO getComRecordsByOrderID(int orderID);

    ResponseVO getComRecordsByUserID(int userID);

    ResponseVO getChargeRecordsByUserID(int userID);

    ComsumptionRecord getComsumptionRecordByTicketID(int ticketID);

    ResponseVO getOrderRecordByOrderID(int orderID);

    ResponseVO getOrderRecordWithUserID(int userID);
}
