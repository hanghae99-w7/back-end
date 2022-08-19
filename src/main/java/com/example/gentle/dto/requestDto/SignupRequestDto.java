package com.example.gentle.dto.requestDto;


import lombok.Getter;

import javax.persistence.GeneratedValue;

@Getter
public class SignupRequestDto {
    private String email;
    private String password;

}
