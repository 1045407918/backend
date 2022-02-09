package com.example.cinema.vo;

import com.example.cinema.po.MovieRecentBoxOffice;

/**
 * @author Karson
 * @date 2019/5/6 19:34
 */

public class MovieRecentBoxOfficeVO {
    private Integer movieId;
    /**
     * 票房(单位：元)，(PS:如果后续数据量大，可自行处理单位，如改成单位：万元)
     */
    private Integer boxOffice;
    private String name;

    public MovieRecentBoxOfficeVO(MovieRecentBoxOffice movieRecentBoxOffice){
        this.movieId = movieRecentBoxOffice.getMovieId();
        this.boxOffice = movieRecentBoxOffice.getBoxOffice();
        this.name = movieRecentBoxOffice.getName();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getBoxOffice() {
        return boxOffice;
    }

    public void setBoxOffice(Integer boxOffice) {
        this.boxOffice = boxOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
