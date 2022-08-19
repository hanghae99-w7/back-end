package com.sparta.miniproject_movie_study_01.controller.response;

import lombok.Getter;
import org.json.JSONObject;

// 왜 ??
@Getter
//@Setter
public class MovieNowRankResponseDto {

    String rnum;
    String rank;
    String rankInten;
    String rankOldAndNew;
    String movieCd;
    String movieNm;
    String openDt;
    String salesAmt;
    String salesShare;
    String salesInten;
    String salesChange;
    String salesAcc;
    String audiCnt;
    String audiInten;
    String audiChange;
    String audiAcc;
    String scrnCnt;
    String showCnt;

    //생성자에서도 get을 쓰면 @Getter가 필요하다.
    public MovieNowRankResponseDto(JSONObject jsonObject){

        this.rnum = jsonObject.getString("rnum");
        this.rank = jsonObject.getString("rank");
        this.rankInten = jsonObject.getString("rankInten");
        this.rankOldAndNew = jsonObject.getString("rankOldAndNew");
        this.movieCd = jsonObject.getString("movieCd");
        this.movieNm = jsonObject.getString("movieNm");
        this.openDt = jsonObject.getString("openDt");
        this.salesAmt = jsonObject.getString("salesAmt");
        this.salesShare = jsonObject.getString("salesShare");
        this.salesInten = jsonObject.getString("salesInten");
        this.salesChange = jsonObject.getString("salesChange");
        this.salesAcc = jsonObject.getString("salesAcc");
        this.audiCnt = jsonObject.getString("audiCnt");
        this.audiInten = jsonObject.getString("audiInten");
        this.audiChange = jsonObject.getString("audiChange");
        this.audiAcc = jsonObject.getString("audiAcc");
        this.scrnCnt = jsonObject.getString("scrnCnt");
        this.showCnt = jsonObject.getString("showCnt");




    }


}
