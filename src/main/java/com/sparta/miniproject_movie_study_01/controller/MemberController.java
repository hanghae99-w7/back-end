package com.sparta.miniproject_movie_study_01.controller;


import com.sparta.miniproject_movie_study_01.controller.request.EmailRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.LoginRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.MemberRequestDto;
import com.sparta.miniproject_movie_study_01.controller.request.NicknameRequestDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
public class MemberController {

  private final MemberService memberService;


  // 유저 이메일 중복 체크
  @RequestMapping(value = "/api/members/emailcheck", method = RequestMethod.POST)
  public ResponseDto<?> emailCheck(@RequestBody EmailRequestDto emailRequestDto) {
    return memberService.emailCheck(emailRequestDto);
  }
  // 닉네임 중복 체크
  @RequestMapping(value = "/api/members/nicknamecheck", method = RequestMethod.POST)
  public ResponseDto<?> nickNameCheck(@RequestBody NicknameRequestDto nicknameRequestDto) {
    return memberService.nickNameCheck(nicknameRequestDto);
  }

  // 회원 가입
  @RequestMapping(value = "/api/member/signup", method = RequestMethod.POST)
  public ResponseDto<?> signup(@RequestBody MemberRequestDto requestDto) {
    return memberService.createMember(requestDto);
  }


  //  로그인
  @RequestMapping(value = "/api/member/login", method = RequestMethod.POST)
  public ResponseDto<?> login(@RequestBody LoginRequestDto requestDto,
      HttpServletResponse response
  ) {
    return memberService.login(requestDto, response);
  }

  // 로그 아웃
  @RequestMapping(value = "/api/auth/member/logout", method = RequestMethod.POST)
  public ResponseDto<?> logout(HttpServletRequest request) {
    return memberService.logout(request);
  }
}
