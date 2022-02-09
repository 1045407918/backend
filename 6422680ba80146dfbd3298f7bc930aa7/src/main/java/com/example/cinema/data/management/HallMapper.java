package com.example.cinema.data.management;

import com.example.cinema.po.Hall;
import com.example.cinema.vo.HallInfoForm;
import com.example.cinema.vo.HallVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
@Repository
public interface HallMapper {
    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 添加影院
     */
    int insertHall(HallInfoForm hallInfoForm);

    void updateHallInfo(HallVO hallVO);

    void deleteHallInfo(@Param("hallID")int hallID);
}
