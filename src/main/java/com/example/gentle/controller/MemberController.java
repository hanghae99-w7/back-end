package com.example.gentle.controller;

import com.example.gentle.dto.TokenDto;
import com.example.gentle.dto.requestDto.LoginIdCheckRequestDto;
import com.example.gentle.dto.requestDto.LoginRequestDto;
import com.example.gentle.dto.requestDto.NicknameCheckRequestDto;
import com.example.gentle.dto.requestDto.SignupRequestDto;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/signup")
    public ResponseEntity<?> signupMembers(@RequestBody SignupRequestDto requestDto) {
        return memberService.signupMember(requestDto);
    }

    @PostMapping("/members/nicknamecheck")
    public ResponseEntity<?> nicknameDubCheck(@RequestBody NicknameCheckRequestDto requestDto) {
        return memberService.nicknameDubCheck(requestDto);
    }

    @PostMapping("/members/idcheck")
    public ResponseEntity<?> idDubCheck(@RequestBody LoginIdCheckRequestDto requestDto) {
        return memberService.loginIdDubCheck(requestDto);
    }

    @PostMapping("/members/login")
    public ResponseEntity<?> loginMembers(@RequestBody LoginRequestDto requestDto, HttpServletResponse response) {
        return memberService.loginMembers(requestDto,response);
    }

    @GetMapping("/members/kakao/callback")
    public ResponseEntity<?> kakaoLogin(@RequestParam String code, HttpServletResponse response) throws JsonProcessingException {
        TokenDto tokenDto= memberService.kakaoLogin(code);
        tokenDto.tokenToHeaders(response);
        return new ResponseEntity<>(Message.success("로그인에 성공하였습니다."), HttpStatus.OK);
    }

}
