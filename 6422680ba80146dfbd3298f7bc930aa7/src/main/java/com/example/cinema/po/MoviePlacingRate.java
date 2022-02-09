package com.example.cinema.po;

import com.example.cinema.vo.MoviePlacingRateVO;

public class MoviePlacingRate {
    private Integer movieId;
    private Integer hall1;
    private Integer hall2;
    private Integer num1;
    private Integer num2;

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getHall1() {
        return hall1;
    }

    public Integer getHall2() {
        return hall2;
    }

    public Integer getNum1() {
        return num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setHall1(Integer hall1) {
        this.hall1 = hall1;
    }

    public void setHall2(Integer hall2) {
        this.hall2 = hall2;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public MoviePlacingRateVO getVO(Movie movie){
        MoviePlacingRateVO moviePlacingRateVO=new MoviePlacingRateVO();
        moviePlacingRateVO.setMovieId(this.getMovieId());
        moviePlacingRateVO.setName(movie.getName());
        moviePlacingRateVO.setRate((this.getNum1()+this.getNum2())/(this.hall1*50+this.getHall2()*96));
        return moviePlacingRateVO;
    }


}
