package com.example.cinema.blImpl.management.hall;

import com.example.cinema.bl.management.HallService;
import com.example.cinema.data.management.HallMapper;
import com.example.cinema.data.management.ScheduleMapper;
import com.example.cinema.po.Hall;
import com.example.cinema.po.ScheduleItem;
import com.example.cinema.vo.HallInfoForm;
import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public Hall getHallById(int id) {
        try {
            return hallMapper.selectHallById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            hallVOList.add(new HallVO(hall));
        }
        return hallVOList;
    }

    @Override
    public ResponseVO addOneHall(HallInfoForm hallInfoForm){
        try {

            hallMapper.insertHall(hallInfoForm);
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO changeHallInfo(HallVO hallVO) {
        try {
            int hallID=hallVO.getId();
            List<ScheduleItem> scheduleItems=scheduleMapper.selectScheduleByHallId(hallID);
            if(scheduleItems.size()!=0){
                return ResponseVO.buildFailure("影厅正在被使用");
            }else {
                hallMapper.updateHallInfo(hallVO);
                HallVO returnHallVO=new HallVO(hallMapper.selectHallById(hallID));
                return ResponseVO.buildSuccess(returnHallVO);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteHallInfo(int hallID){
        try {
            hallMapper.deleteHallInfo(hallID);
            return ResponseVO.buildSuccess();
        }catch (Exception ex){
            ex.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }
}
