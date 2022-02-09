package com.example.cinema.controller.records;

import com.example.cinema.bl.records.RecordsService;
import com.example.cinema.data.records.ChargeRecordMapper;
import com.example.cinema.po.ChargeRecord;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/records")
public class RecordsController {
    @Autowired
    RecordsService recordsService;

    @GetMapping("/getChargeRecords")
    public ResponseVO getAllChargeRecords(){return recordsService.getChargeRecords();}

    @GetMapping("/getChargeRecordById/{recordId}")
    public ResponseVO getChargeRecordById(@PathVariable int recordId){return recordsService.getChargeRecordsById(recordId);}

    @GetMapping("/selectChargeRecordByUserID/{userId}")
    public ResponseVO selectChargeRecordByUserID(@PathVariable int userId){return recordsService.getChargeRecordsByUserID(userId);}

    @GetMapping("/getComsumptionRecords")
    public ResponseVO getComsumptionRecords(){return recordsService.getAllComsumptionRecords();}

    @GetMapping("/getComsumptionRecordsByOrderID/{OrderID}")
    public ResponseVO getComsumptionRecordsByOrderID(@PathVariable int OrderID){return recordsService.getComRecordsByOrderID(OrderID);}

    @GetMapping("/getComsumptionRecordsByUserID/{userID}")
    public ResponseVO getComsumptionRecordsByUserID(@PathVariable int userID){return recordsService.getComRecordsByUserID(userID);}

    @GetMapping("/getOrderRecord/{orderID}")
    public ResponseVO getOrderRecord(@PathVariable int orderID){return recordsService.getOrderRecordByOrderID(orderID);}

    @GetMapping("/getOrderRecordByUser/{userID}")
    public ResponseVO getOrderRecordByUserID(@PathVariable int userID){return recordsService.getOrderRecordWithUserID(userID);}
}
