package com.example.cinema.po;

public class RefundStrategy {
    /**
     *不可退票的时间，即电影开场前多长时间起不可退票，按小时记
     */
    private int time;

    private int id;

    private int inUse;

    public void setTime(int time){
        this.time=time;
    }

    public int getTime(){
        return time;
    }

    public void setId(int id){
        this.id=id;
    }

    public int getId(){
        return id;
    }

    public int getInUse() {
        return inUse;
    }

    public void setInUse(int inUse) {
        this.inUse = inUse;
    }
}
