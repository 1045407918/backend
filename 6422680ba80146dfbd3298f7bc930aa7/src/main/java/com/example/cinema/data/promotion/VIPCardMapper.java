package com.example.cinema.data.promotion;

import com.example.cinema.po.VIPCard;
import com.example.cinema.po.VIPDiscountStrategy;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
@Repository
public interface VIPCardMapper {

    int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(@Param("id") int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

    VIPCard selectCardByUserId(int userId);

    List<VIPCard> selectAllVip();

        //以下为尝试内容，看着删
    int insertDiscountStrategy(@Param("quota") int quota,@Param("gift")int gift);

    void deleteDiscountStrategy(int StrategyId);

    VIPDiscountStrategy selectDiscountStrategyById(int StrategyId);

    VIPDiscountStrategy selectDiscountStrategyByQuota(int quota);

    List<VIPDiscountStrategy> selectAllDiscountStrategy();

    //选择满足条件的最大的满额
    VIPDiscountStrategy selectSuitableDiscountStrategy(int amount);

    void updateDiscountStrategy(@Param("id")int id,@Param("quota")int quota,@Param("gift")int gift);


}
