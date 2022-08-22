package com.example.gentle.service;

import com.example.gentle.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SunglassesService {

    private final MemberRepository memberRepository;

    private static Logger logger = LoggerFactory.getLogger(SunglassesService.class);

//    @Transactional
//    public ResponseDto<?> SunglassesCrawling() {
//
//    }

}
