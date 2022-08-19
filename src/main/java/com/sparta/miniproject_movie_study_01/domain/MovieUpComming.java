package com.sparta.miniproject_movie_study_01.domain;


import com.sparta.miniproject_movie_study_01.controller.response.CGVInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 디폴트가 null일때 나머지만 insert
@Entity
public class MovieUpComming extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String img;

    @Column(nullable = false)
    private String movieAge;

    @Column(nullable = false)
    private String movieTitle;

    @Column(nullable = false)
    private String movieRate; //예매율

    @Column(nullable = false)
    private String movieOpenDate; //개봉일

//    @Column(nullable = false)
//    private String like;

//    @Column(nullable = false)
//    private int seq;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;

    @Column(name = "likes_count")
    @ColumnDefault("0") //default 0
    private Integer likes_count;
    // 꺼꾸로 되었다. 부모가 자식이 되어버렸네??

    @Column(name = "comment_count")
    @ColumnDefault("0") //default 0
    private Integer comment_count;


    public MovieUpComming(CGVInfoDto cgvInfoDto){

        this.img = cgvInfoDto.getImg();
        this.movieAge = cgvInfoDto.getMovieAge();
        this.movieTitle = cgvInfoDto.getMovieTitle();
        this.movieRate = cgvInfoDto.getMovieRate();
        this.movieOpenDate = cgvInfoDto.getMovieOpenDate();


    }

    // 좋아요 갯수 업데이트
    public void updatelike_count(Integer postlike_count){
        this.likes_count = postlike_count;

    }

    // 댓글 갯수 업데이트
    public void updatecomment_count(Integer comment_count){
        this.comment_count = comment_count;

    }


}
