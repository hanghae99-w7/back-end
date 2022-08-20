package com.sparta.miniproject_movie_study_01.controller.response;

import lombok.Getter;
import lombok.Setter;
import org.json.JSONObject;

@Getter
@Setter
public class CgvSearchResponseDto {

    String title;
    String link;
    String image;
    String subtitle;
    String pubDate;
    String director;
    String actor;
    String userRating;


    public CgvSearchResponseDto(JSONObject jsonObject){

        this.title = jsonObject.getString("title");
        this.link = jsonObject.getString("link");
        this.image = jsonObject.getString("image");
        this.subtitle = jsonObject.getString("subtitle");
        this.pubDate = jsonObject.getString("pubDate");
        this.director = jsonObject.getString("director");
        this.actor = jsonObject.getString("actor");
        this.userRating = jsonObject.getString("userRating");


    }

}
