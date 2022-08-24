package com.example.gentle.service;

import com.example.gentle.domain.Member;
import com.example.gentle.domain.ServiceCenter;
import com.example.gentle.dto.requestDto.ServiceCenterRequestDto;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.dto.responseDto.ServiceCenterResponseDto;
import com.example.gentle.jwt.TokenProvider;
import com.example.gentle.repository.ServiceCenterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceCenterService {

    private final TokenProvider tokenProvider;
    private final ServiceCenterRepository serviceCenterRepository;

    public ServiceCenterService(TokenProvider tokenProvider,
                                ServiceCenterRepository serviceCenterRepository) {
        this.tokenProvider = tokenProvider;
        this.serviceCenterRepository = serviceCenterRepository;
    }

    @Transactional
    public ResponseEntity<?> createContact(ServiceCenterRequestDto serviceCenterRequestDto,
                                           HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        if (null == request.getHeader("Authorization")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }

        ServiceCenter serviceCenter = ServiceCenter.builder()
                .title(serviceCenterRequestDto.getTitle())
                .content(serviceCenterRequestDto.getContent())
                .member(member)
                .adminCheck(false)
                .build();

        serviceCenterRepository.save(serviceCenter);

        return getResponseEntity(serviceCenter);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getContact() {

        List<ServiceCenter> serviceCenterList = serviceCenterRepository.findAll();
        List<ServiceCenterResponseDto> serviceCenterResponseDtoList = new ArrayList<>();

        for (ServiceCenter serviceCenter : serviceCenterList) {
            serviceCenterResponseDtoList.add(
                    ServiceCenterResponseDto.builder()
                            .id(serviceCenter.getId())
                            .username(serviceCenter.getMember().getName())
                            .email(serviceCenter.getMember().getEmail())
                            .title(serviceCenter.getTitle())
                            .content(serviceCenter.getContent())
                            .adminChecked(serviceCenter.getAdminCheck())
                            .createdAt(serviceCenter.getCreatedAt())
                            .modifiedAt(serviceCenter.getModifiedAt())
                            .build()
            );
        }

        return new ResponseEntity<>(Message.success(serviceCenterResponseDtoList), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<?> updateAdminCheck(Long id,
                                              HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        if (null == request.getHeader("Authorization")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }

        ServiceCenter serviceCenter = getPresentServiceCenter(id);
        if (null == serviceCenter) {
            return new ResponseEntity<>(Message.fail("CONTACT_NOT_FOUND", "존재하지 않는 문의사항 입니다."), HttpStatus.NOT_FOUND);
        }

        serviceCenter.updateAdminCheck();

        return getResponseEntity(serviceCenter);
    }

    @Transactional
    public ResponseEntity<?> updateContact(Long id,
                                           ServiceCenterRequestDto requestDto,
                                           HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        if (null == request.getHeader("Authorization")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }

        ServiceCenter serviceCenter = getPresentServiceCenter(id);
        if (null == serviceCenter) {
            return new ResponseEntity<>(Message.fail("CONTACT_NOT_FOUND", "존재하지 않는 문의사항 입니다."), HttpStatus.NOT_FOUND);
        }

        serviceCenter.updateContact(requestDto);

        return getResponseEntity(serviceCenter);
    }

    @Transactional
    public ResponseEntity<?> deleteContact(Long id,
                                           HttpServletRequest request) {

        if (null == request.getHeader("Refresh-Token")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        if (null == request.getHeader("Authorization")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다."), HttpStatus.UNAUTHORIZED);
        }

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }

        ServiceCenter serviceCenter = getPresentServiceCenter(id);
        if (null == serviceCenter) {
            return new ResponseEntity<>(Message.fail("CONTACT_NOT_FOUND", "존재하지 않는 문의사항 입니다."), HttpStatus.NOT_FOUND);
        }

        serviceCenterRepository.delete(serviceCenter);

        return getResponseEntity(serviceCenter);
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public ServiceCenter getPresentServiceCenter(Long id) {
        Optional<ServiceCenter> optionalServiceCenter = serviceCenterRepository.findById(id);
        return optionalServiceCenter.orElse(null);
    }

    private ResponseEntity<?> getResponseEntity(ServiceCenter serviceCenter) {
        return new ResponseEntity<>(
                Message.success(
                        ServiceCenterResponseDto.builder()
                                .id(serviceCenter.getId())
                                .username(serviceCenter.getMember().getName())
                                .email(serviceCenter.getMember().getEmail())
                                .title(serviceCenter.getTitle())
                                .content(serviceCenter.getContent())
                                .adminChecked(serviceCenter.getAdminCheck())
                                .createdAt(serviceCenter.getCreatedAt())
                                .modifiedAt(serviceCenter.getModifiedAt())
                                .build()
                ),
                HttpStatus.OK
        );
    }

}