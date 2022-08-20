package com.sparta.miniproject_movie_study_01.controller.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MovieUpCommingResponseDto {

    private Long id;
    private String img;
    private String movieAge;
    private String movieTitle;
    private String movieRate; //예매율
    private String movieOpenDate; //개봉일
    // 해당 게시글 좋아요 개수
    private Integer likes;
    // 댓글개수로 수정
    private Integer commentCount;
    private List<CommentResponseDto> commentResponseDtoList;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;


}
