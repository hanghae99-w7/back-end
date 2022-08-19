package com.example.gentle.dto.requestDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KakaoUserInfoDto {
    private Long kakaoIdInDb;
    private String email;
    private String encodedPassword;
    private String gender;
    private String name;
    private String birth;
    private String country;
}
