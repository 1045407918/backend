package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.data.bankcard.BankCardMapper;
import com.example.cinema.po.VIPDiscountStrategy;
import com.example.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId, @RequestBody BankCardForm bankCardForm){
        return vipService.addVIPCard(userId,bankCardForm);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPchargeVO viPchargeVO){
        return vipService.charge(viPchargeVO);
    }

    @PostMapping("/clearBalance")
    public ResponseVO clear(@RequestBody VIPCardForm vipCardForm){return vipService.clearBalance(vipCardForm);}

    @PostMapping("/addDiscountStrategy")
    public ResponseVO addDiscountStrategy(@RequestBody VIPDiscountStrategyForm vipDiscountStrategyForm){return vipService.addVIPDiscountStrategy(vipDiscountStrategyForm.getQuota(),vipDiscountStrategyForm.getGift());}

    @GetMapping("/getAllDiscountStrategy")
    public ResponseVO getAllDiscountStrategy(){return vipService.getDiscountStrategy();}

    @PostMapping("/updateDiscountStrategy")
    public ResponseVO updateDiscountStrategy(@RequestBody VIPDiscountStrategy vipDiscountStrategy){return vipService.updateStrategy(vipDiscountStrategy);}

    @PostMapping("/deleteDiscountStrategy/{StrategyID}")
    public ResponseVO deleteStrategy(@PathVariable int StrategyID){return vipService.deleteDiscountStrategy(StrategyID);}


    @GetMapping("/getUserByAmount")
    public ResponseVO getUserByAmount(@RequestParam int amount){
        return vipService.getUserByAmount(amount);
    }

    @PostMapping("/setVIPCardPrice")
    public ResponseVO setVIPCardPrice(@RequestParam double price){return vipService.setCardPrice(price);}

    @PostMapping("/giveCoupon")
    public ResponseVO giveCoupon(@RequestBody GiveCouponVO giveCouponVO){
        return vipService.giveCoupon(giveCouponVO);
    }


}
