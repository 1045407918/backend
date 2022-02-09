package com.example.cinema.vo;

import com.example.cinema.po.VIPDiscountStrategy;

public class VIPDiscountStrategyVO {
    /**
     * id
     */
    private Integer id;
    /**
     * 满额，满200送30的200
     */
    private Integer quota;
    /**
     * 赠额，满200送30的30
     */
    private Integer gift;
    public VIPDiscountStrategyVO(){
    }

    public VIPDiscountStrategyVO(VIPDiscountStrategy vipDiscountStrategy){
        this.id=vipDiscountStrategy.getId();
        this.quota=vipDiscountStrategy.getQuota();
        this.gift=vipDiscountStrategy.getGift();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuota() {
        return quota;
    }

    public void setQuota(Integer quota) {
        this.quota = quota;
    }

    public Integer getGift() {
        return gift;
    }

    public void setGift(Integer gift) {
        this.gift = gift;
    }



}
