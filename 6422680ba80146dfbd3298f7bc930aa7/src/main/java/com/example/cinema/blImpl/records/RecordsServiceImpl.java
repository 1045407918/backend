package com.example.cinema.blImpl.records;

import com.example.cinema.bl.records.RecordsService;
import com.example.cinema.data.promotion.VIPCardMapper;
import com.example.cinema.data.records.ChargeRecordMapper;
import com.example.cinema.data.records.ComsumptionRecordMapper;
import com.example.cinema.po.ChargeRecord;
import com.example.cinema.po.ComsumptionRecord;
import com.example.cinema.po.OrderRecord;
import com.example.cinema.po.VIPCard;
import com.example.cinema.vo.ChargeRecordVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecordsServiceImpl implements RecordsService {

    @Autowired
    ChargeRecordMapper chargeRecordMapper;
    @Autowired
    ComsumptionRecordMapper comsumptionRecordMapper;
    @Autowired
    VIPCardMapper vipCardMapper;

    @Override
    public ResponseVO getChargeRecords() {
        try {
            List<ChargeRecord> chargeRecords=chargeRecordMapper.selectAllChargeRecord();
            List<ChargeRecordVO> chargeRecordVOS=new ArrayList<>();
            for(ChargeRecord chargeRecord:chargeRecords){
                chargeRecordVOS.add(new ChargeRecordVO(chargeRecord));
            }
            return ResponseVO.buildSuccess(chargeRecordVOS);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getChargeRecordsById(int recordId) {
        try {
            ChargeRecord chargeRecord=chargeRecordMapper.selectChargeRecordById(recordId);
            ChargeRecordVO chargeRecordVO=new ChargeRecordVO(chargeRecord);
            return ResponseVO.buildSuccess(chargeRecordVO);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }
    @Override
    public ResponseVO getChargeRecordsByUserID(int userID) {
        try {
            VIPCard vipCard=vipCardMapper.selectCardByUserId(userID);
            if(vipCard==null){
                return ResponseVO.buildFailure("非会员");
            }
            int vipID=vipCard.getId();
            List<ChargeRecord>chargeRecords=chargeRecordMapper.selectChargeRecordByVIPID(vipID);
            return ResponseVO.buildSuccess(chargeRecords);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAllComsumptionRecords() {
        try {
            List<ComsumptionRecord> comsumptionRecords= comsumptionRecordMapper.getComsumptionRecords();
            return ResponseVO.buildSuccess(comsumptionRecords);
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getComRecordsByOrderID(int orderID) {
        try {
            List<ComsumptionRecord> comsumptionRecords=comsumptionRecordMapper.getComsumptionRecordsByOrderID(orderID);
            return ResponseVO.buildSuccess(comsumptionRecords);

        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getComRecordsByUserID(int userID) {
        try {
            List<ComsumptionRecord> comsumptionRecords=comsumptionRecordMapper.selectComsumptionRecordsByUserID(userID);
            return ResponseVO.buildSuccess(comsumptionRecords);

        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }



    @Override
    public ComsumptionRecord getComsumptionRecordByTicketID(int ticketID){
        try {
            return comsumptionRecordMapper.getComsumptionRecordByTicketID(ticketID);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO getOrderRecordByOrderID(int orderID) {
        try {
            OrderRecord orderRecord=comsumptionRecordMapper.selectOrderRecordByOrderID(orderID);
            return ResponseVO.buildSuccess(orderRecord);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public ResponseVO getOrderRecordWithUserID(int userID) {
        try {
            List<OrderRecord> orderRecords=comsumptionRecordMapper.getOrderRecordsByUserID(userID);
            return ResponseVO.buildSuccess(orderRecords);
        }catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

}
