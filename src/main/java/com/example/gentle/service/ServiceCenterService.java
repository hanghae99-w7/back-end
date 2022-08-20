package com.example.gentle.service;

import com.example.gentle.domain.Member;
import com.example.gentle.domain.ServiceCenter;
import com.example.gentle.dto.requestDto.ServiceCenterRequestDto;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.jwt.TokenProvider;
import com.example.gentle.repository.ServiceCenterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

@Service
public class ServiceCenterService {

    private final TokenProvider tokenProvider;
    private final ServiceCenterRepository serviceCenterRepository;

    public ServiceCenterService(TokenProvider tokenProvider,
                                ServiceCenterRepository serviceCenterRepository) {
        this.tokenProvider = tokenProvider;
        this.serviceCenterRepository = serviceCenterRepository;
    }

    public ResponseEntity<?> createContact(ServiceCenterRequestDto serviceCenterRequestDto,
                                           HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.NOT_FOUND);
        }

        if (null == request.getHeader("Authorization")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.NOT_FOUND);
        }

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }

        ServiceCenter serviceCenter = ServiceCenter.builder()
                .title(serviceCenterRequestDto.getTitle())
                .content(serviceCenterRequestDto.getContent())
                .member(member)
                .build();

        serviceCenterRepository.save(serviceCenter);

        return new ResponseEntity<>(Message.success(serviceCenter), HttpStatus.OK);
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

}
