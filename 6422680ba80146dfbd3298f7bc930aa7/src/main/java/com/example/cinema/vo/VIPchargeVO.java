package com.example.cinema.vo;

public class VIPchargeVO {
    /**
     * vip卡id
     */
    private int vipId;

    /**
     * 付款金额
     */
    private double amount;

    private int cardNumber;

    private int password;

    private double cardBalance;

    public int getCardNumber(){
        return cardNumber;
    }

    public void setCardNumber(int cardNumber){
        this.cardNumber=cardNumber;
    }

    public int getPassword(){
        return password;
    }

    public void setPassword(int password){
        this.password=password;
    }

    public double getcardBalance(){
        return cardBalance;
    }

    public void setcardBalance(double cardBalance){
        this.cardBalance=cardBalance;
    }


    public int getVipId() {
        return vipId;
    }

    public void setVipId(int vipId) {
        this.vipId = vipId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
