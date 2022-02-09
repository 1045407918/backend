package com.example.cinema.po;

import com.example.cinema.vo.VIPDiscountStrategyVO;

public class VIPDiscountStrategy {
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

    public VIPDiscountStrategyVO getVO(){
        VIPDiscountStrategyVO vipDiscountStrategyVO=new VIPDiscountStrategyVO();
        vipDiscountStrategyVO.setGift(this.getGift());
        vipDiscountStrategyVO.setId(this.getId());
        vipDiscountStrategyVO.setQuota(this.getQuota());
        return vipDiscountStrategyVO;
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
