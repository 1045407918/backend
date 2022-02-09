package com.example.cinema.po;

import java.sql.Timestamp;

public class ChargeRecord {
    private  int id=0;
    private Timestamp time;
    private double amount;
    private int gift;
    private int vipID;
    private double balance;
    /*
     *rental总额,充进去的值
     */
    private double rental;

    public ChargeRecord(){
    };

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getGift() {
        return gift;
    }

    public void setGift(int gift) {
        this.gift = gift;
    }

    public int getVipID() {
        return vipID;
    }

    public void setVipID(int vipID) {
        this.vipID = vipID;
    }

    public double getRental() {
        return rental;
    }

    public void setRental(double rental) {
        this.rental = rental;
    }

}
