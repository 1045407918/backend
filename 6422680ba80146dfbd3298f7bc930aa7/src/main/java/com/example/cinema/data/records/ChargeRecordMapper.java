package com.example.cinema.data.records;

import com.example.cinema.po.ChargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ChargeRecordMapper {

    int insertChargeRecord(ChargeRecord chargeRecord);

    ChargeRecord selectChargeRecordById(int recordId);

    List<ChargeRecord> selectAllChargeRecord();

    List<ChargeRecord> selectChargeRecordByVIPID(int VIPID);
}
