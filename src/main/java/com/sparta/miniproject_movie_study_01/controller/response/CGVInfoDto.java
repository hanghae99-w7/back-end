package com.sparta.miniproject_movie_study_01.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CGVInfoDto {

    @JsonIgnore
    private String rank;
    private String img;
    private String movieAge;
    private String movieTitle;
    private String movieRate; //예매율
    private String movieOpenDate; //개봉일
    private String like;
    private int seq;

    public CGVInfoDto() {
        // TODO Auto-generated constructor stub
    }


//    public CGVInfoDto(String rank, String img, String movieAge, String movieTitle, String movieRate,
//                      String movieOpenDate, String like, int seq) {
//        super();
//        this.rank = rank;
//        this.img = img;
//        this.movieAge = movieAge;
//        this.movieTitle = movieTitle;
//        this.movieRate = movieRate;
//        this.movieOpenDate = movieOpenDate;
//        this.like = like;
//        this.seq = seq;
//    }

    public CGVInfoDto(String img, String movieAge, String movieTitle, String movieRate,
                      String movieOpenDate) {

        super();
        this.img = img;
        this.movieAge = movieAge;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieOpenDate = movieOpenDate;


    }

    public CGVInfoDto(String img, String movieAge, String movieTitle, String movieRate,
                      String movieOpenDate, int seq) {
        super();
        this.img = img;
        this.movieAge = movieAge;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieOpenDate = movieOpenDate;
        this.seq = seq;
    }

    public CGVInfoDto(String img, String movieAge, String movieTitle, String movieRate,
                      String movieOpenDate, String like, int seq) {
        super();
        this.rank = rank;
        this.img = img;
        this.movieAge = movieAge;
        this.movieTitle = movieTitle;
        this.movieRate = movieRate;
        this.movieOpenDate = movieOpenDate;
        this.like = like;
        this.seq = seq;
    }


    @Override
    public String toString() {
        return "CGVInfoDto [rank=" + rank + ", img=" + img + ", movieAge=" + movieAge + ", movieTitle=" + movieTitle
                + ", movieRate=" + movieRate + ", movieOpenDate=" + movieOpenDate + ", like=" + like + ", seq=" + seq
                + "]";
    }


}
