package com.example.cinema.bl.promotion;


import com.example.cinema.po.VIPDiscountStrategy;
import com.example.cinema.vo.*;


/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId, BankCardForm bankCardForm);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPchargeVO viPchargeVO);

    ResponseVO refund(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO cardBuy(VIPCardForm vipCardForm);

    ResponseVO addVIPDiscountStrategy(int quota,int gift);
    //quota是满200赠20的200（定额），gift是赠额20
    ResponseVO getDiscountStrategy();

    ResponseVO clearBalance(VIPCardForm vipCardForm);

    ResponseVO getUserByAmount(int amount);

    ResponseVO updateStrategy(VIPDiscountStrategy vipDiscountStrategy);

    ResponseVO deleteDiscountStrategy(int strategyID);

    ResponseVO giveCoupon(GiveCouponVO giveCouponVO);

    ResponseVO setCardPrice(double price);
}
