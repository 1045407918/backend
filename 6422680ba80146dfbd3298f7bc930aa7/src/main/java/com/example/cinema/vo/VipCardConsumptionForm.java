package com.example.cinema.vo;

public class VipCardConsumptionForm {
    /**
     * vip用户id
     */
    private int userId;
    /**
     * 用户历史消费金额
     */
    private int amount;
    /**
     * VipcardId
     */
    private int vipCardId;

    public int getAmount() {
        return amount;
    }

    public int getVipCardId() {
        return vipCardId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setVipCardId(int vipCardId) {
        this.vipCardId = vipCardId;
    }
}
