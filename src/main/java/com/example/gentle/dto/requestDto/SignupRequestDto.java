package com.example.gentle.dto.requestDto;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.GeneratedValue;

@Getter
@Setter
public class SignupRequestDto {
    private String email;
    private String password;
    private String birth;
    private String name;
    private String country;
    private String gender;
    private boolean admin = false;
    private String adminToken = "";
}