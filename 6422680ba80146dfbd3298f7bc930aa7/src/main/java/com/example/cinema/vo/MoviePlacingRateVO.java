package com.example.cinema.vo;

import com.example.cinema.po.MoviePlacingRate;

public class MoviePlacingRateVO {
    private Integer movieId;
    private String name;
    private double rate;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

}
