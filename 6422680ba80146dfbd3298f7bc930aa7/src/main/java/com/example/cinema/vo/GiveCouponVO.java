package com.example.cinema.vo;


import java.util.List;

public class GiveCouponVO {
    private List<Integer> userId;
    private int activityId;

    public int getactivityId() {
        return activityId;
    }

    public List<Integer> getUserId() {
        return userId;
    }

    public void setactivityId(int couponId) {
        this.activityId = couponId;
    }

    public void setUserId(List<Integer> userId) {
        this.userId = userId;
    }
}
