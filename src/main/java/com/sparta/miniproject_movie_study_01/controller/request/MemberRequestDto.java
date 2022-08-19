package com.sparta.miniproject_movie_study_01.controller.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MemberRequestDto {

  // 정규식 기본제공 메시지가 있음.
  // 만약 여기서 requestDto에서 정규식을 사용했으면
  // 해당 컨트롤러에 @Requestbody하고 @Valid를해야 정규식이 작동한다.

  @Email
  @NotBlank (message = "이메일을 입력해주세요")
  private String email;

  @NotBlank (message = "아이디를 입력해주세요")
  //@Size(min = 4, max = 12)
  //@Pattern(regexp = "[a-zA-Z\\d]*${3,12}")
  private String nickname;

  @NotBlank (message = "비밀번호를 입력해주세요")
  //@Size(min = 4, max = 32)
  //@Pattern(regexp = "[a-z\\d]*${3,32}")
  private String password;

  @NotBlank
  private String passwordConfirm;
}
