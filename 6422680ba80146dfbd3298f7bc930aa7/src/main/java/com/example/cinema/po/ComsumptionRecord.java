package com.example.cinema.po;

import java.sql.Timestamp;

public class ComsumptionRecord {
    private int id;
    /*
    * 折扣前的总额
    * */
    private double total_origin;
    /*
     * 折扣后使用优惠券的总额
     * */
    private double total_result;

    /*
    * 使用的优惠券ID
    * */
    private int couponID_used=0;


    private int VIPID=0;

    private int bankCardNumber=0;

    private int orderID;

    private int ticketID;
    private int UserID;
    private Timestamp time;

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }
    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public int getTicketID() {
        return ticketID;
    }

    public void setTicketID(int ticketID) {
        this.ticketID = ticketID;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getTotal_origin() {
        return total_origin;
    }

    public void setTotal_origin(double total_origin) {
        this.total_origin = total_origin;
    }

    public double getTotal_result() {
        return total_result;
    }

    public void setTotal_result(double total_result) {
        this.total_result = total_result;
    }

    public int getCouponID_used() {
        return couponID_used;
    }

    public void setCouponID_used(int couponID_used) {
        this.couponID_used = couponID_used;
    }



    public int getVIPID() {
        return VIPID;
    }

    public void setVIPID(int VIPID) {
        this.VIPID = VIPID;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public int getBankCardNumber(){
        return bankCardNumber;
    }

    public void setBankCardNumber(int bankCardNumber){
        this.bankCardNumber=bankCardNumber;
    }

}
