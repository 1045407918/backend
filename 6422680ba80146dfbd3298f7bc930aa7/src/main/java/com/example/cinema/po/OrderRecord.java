package com.example.cinema.po;

import java.sql.Timestamp;

public class OrderRecord {
    private int orderID;
    private Timestamp payed_time;
    private  Timestamp create_time;
    private int movieID;
    private int scheduleID;
    private int state;
    private double initial_amount;
    private double actual_payed_amount;
    /*0是尚未支付
     * 1是银行卡，2是会员卡*/
    private int payment_method=0;
    private int user_id;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }



    public int getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(int payment_method) {
        this.payment_method = payment_method;
    }



    public Timestamp getPayed_time() {
        return payed_time;
    }

    public void setPayed_time(Timestamp payed_time) {
        this.payed_time = payed_time;
    }

    public Timestamp getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Timestamp create_time) {
        this.create_time = create_time;
    }

    public int getMovieID() {
        return movieID;
    }

    public void setMovieID(int movieID) {
        this.movieID = movieID;
    }

    public int getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(int scheduleID) {
        this.scheduleID = scheduleID;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public double getInitial_amount() {
        return initial_amount;
    }

    public void setInitial_amount(double initial_amount) {
        this.initial_amount = initial_amount;
    }

    public double getActual_payed_amount() {
        return actual_payed_amount;
    }

    public void setActual_payed_amount(double actual_payed_amount) {
        this.actual_payed_amount = actual_payed_amount;
    }


    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }


}
