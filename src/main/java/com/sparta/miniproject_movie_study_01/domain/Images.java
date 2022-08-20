package com.sparta.miniproject_movie_study_01.domain;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Images extends Timestamped {

    // 타임존 로컬시간은 우리나라
    // 우분투 시간이 우리나라 시간간이 아다 9시간 빠르다
    // 이거를 맞추는 것을 찾아야

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    Long id;

    @Column(name = "imgUrl")
    String imgUrl;

    @Column(name = "member_id")
    Long member_id;

    @Column(name = "post_id")
    Long post_id;


    public Images(String imgUrl, Long member_id){

        this.imgUrl = imgUrl;
        this.member_id = member_id;
    }

    public Images(String imgUrl, Long member_id, Long post_id){

        this.imgUrl = imgUrl;
        this.member_id = member_id;
        this.post_id = post_id;


    }

}
