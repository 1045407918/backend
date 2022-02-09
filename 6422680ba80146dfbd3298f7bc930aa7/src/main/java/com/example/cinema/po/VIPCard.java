package com.example.cinema.po;


import java.sql.Timestamp;

/**
 * Created by liying on 2019/4/14.
 */

public class VIPCard {

    public static double price = 25;



    public static final String description="满200送30";

    /**
     * 用户id
     */
    private int userId;



    /**
     * 满额，满200送30的200
     */
    private int quota=0;

    /**
     * 赠额，满200送30的30
     */
    private int gift=0;

    /**
     * 会员卡id
     */
    private int id;

    /**
     * 会员卡余额
     */
    private double balance;

    /**
     * 办卡日期
     */
    private Timestamp joinDate;


    public VIPCard() {

    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getQuota() {
        return quota;
    }

    public void setQuota(int quota) {
        this.quota = quota;
    }

    public int getGift() {
        return gift;
    }

    public void setGift(int gift) {
        this.gift = gift;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Timestamp joinDate) {
        this.joinDate = joinDate;
    }

    public double calculate(double amount) {
        if(quota==0||gift==0){
            return amount;
        }else {
            if(amount>=quota){
                return gift+amount;
            }else {
                return amount;
            }
        }
    }
}
