package com.example.cinema.vo;

import java.util.Date;

public class TicketInfoVO {
    private int tickid;
    /**
     * 电影名字
     */
    private String movieName;

    /**
     * 列号
     */
    private int columnIndex;
    /**
     * 排号
     */
    private int rowIndex;

    /**
     * 订单状态
     */
    private String state;
    /**
     * 影厅name
     */
    private String hallName;
    /**
     * 开始放映时间
     */
    private Date startTime;
    /**
     * 结束放映时间
     */
    private Date endTime;

    private int canRefund;

    public int getTickid() {
        return tickid;
    }

    public void setTickid(int tickid) {
        this.tickid = tickid;
    }

    public int getCanRefund() {
        return canRefund;
    }

    public void setCanRefund(int canRefund) {
        this.canRefund = canRefund;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }



    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
}
