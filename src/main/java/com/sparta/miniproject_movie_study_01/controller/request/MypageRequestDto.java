package com.sparta.miniproject_movie_study_01.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MypageRequestDto {

    @NotBlank
    private String nickname;

}
