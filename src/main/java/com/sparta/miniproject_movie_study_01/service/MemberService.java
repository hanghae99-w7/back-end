package com.sparta.miniproject_movie_study_01.service;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.sparta.miniproject_movie_study_01.awshandler.FileTypeErrorException;
import com.sparta.miniproject_movie_study_01.controller.request.*;
import com.sparta.miniproject_movie_study_01.controller.response.MemberProfileResponseDto;
import com.sparta.miniproject_movie_study_01.controller.response.ResponseDto;
import com.sparta.miniproject_movie_study_01.domain.Images;
import com.sparta.miniproject_movie_study_01.domain.Member;
import com.sparta.miniproject_movie_study_01.jwt.TokenProvider;
import com.sparta.miniproject_movie_study_01.repository.ImagesRepository;
import com.sparta.miniproject_movie_study_01.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;
    //  private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;

    private final AmazonS3Client amazonS3Client;

    private final ImagesRepository imagesRepository;


    // s3관련 전역변수
    @Value("${cloud.aws.s3.bucket}")
    public String bucket;  // S3 버킷 이름

    // 이메일 중복확인.
    @Transactional
    public ResponseDto<?> emailCheck(EmailRequestDto email) {

        String emailCheck = email.getEmail();

        if (emailCheck.equals("")) {

            return ResponseDto.success("이메일을 입력해주세요.");

        }
        if (!emailCheck.contains("@")) {
            return ResponseDto.success("이메일 형식이 아닙니다.");
        }

        if (null != isPresentMemberEmail(emailCheck)) { // 이메일 중복이면
            return ResponseDto.success(false);
        } else { // 이메일 중복 아니면
            return ResponseDto.success(true);
        }
    }

    // 닉네임 중복확인.
    @Transactional
    public ResponseDto<?> nickNameCheck(NicknameRequestDto nickname) {

        String nickNameCheck = nickname.getNickname();

        if (nickNameCheck.equals("")) {
            log.info("빈값이다.");
            return ResponseDto.success("닉네임을 입력해주세요");

        } else {
            log.info("빈값이 아니다.");
            if (null != isPresentMember(nickNameCheck)) { // 넥네임 중복이면
                return ResponseDto.success(false);
            } else { // 닉네임 중복 아니면
                return ResponseDto.success(true);
            }

        }

    }

    @Transactional
    public ResponseDto<?> getProfile(HttpServletRequest request) {

//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        return ResponseDto.success(
                MemberProfileResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .profileimg(member.getProfileimg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );


    }



    // 프로필 이미지 변경
    @Transactional
    public ResponseDto<?> updateProfileimg(HttpServletRequest request,
                                           MultipartFile multipartFile,
                                           String aStatic) throws IOException {

//        if (null == request.getHeader("Refresh-Token")) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "로그인이 필요합니다.");
//        }

        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "로그인이 필요합니다.");
        }

        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }

        String imageUrl = upload(multipartFile, aStatic);

        Images images = new Images(imageUrl, member.getId());

        imagesRepository.save(images);

        member.updateimg(imageUrl);

        memberRepository.save(member);

        return ResponseDto.success(
                MemberProfileResponseDto.builder()
                        .id(member.getId())
                        .nickname(member.getNickname())
                        .email(member.getEmail())
                        .profileimg(member.getProfileimg())
                        .createdAt(member.getCreatedAt())
                        .modifiedAt(member.getModifiedAt())
                        .build()
        );

    }


    @Transactional
    public ResponseDto<?> createMember(MemberRequestDto requestDto) {
        if (null != isPresentMember(requestDto.getNickname())) {
//            return ResponseDto.fail("DUPLICATED_NICKNAME",
//                    "중복된 닉네임입니다.");

            return ResponseDto.success(false);
        }

        if (null != isPresentMemberEmail(requestDto.getEmail())) {
//            return ResponseDto.fail("DUPLICATED_EMAIL",
//                    "중복된 이메일입니다.");

            return ResponseDto.success(false);
        }

        if (!requestDto.getPassword().equals(requestDto.getPasswordConfirm())) {
//            return ResponseDto.fail("PASSWORDS_NOT_MATCHED",
//                    "비밀번호와 비밀번호 확인이 일치하지 않습니다.");

            return ResponseDto.success(false);

        }

        Member member = Member.builder()
                .email(requestDto.getEmail())
                .nickname(requestDto.getNickname())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .build();
        memberRepository.save(member);

//        return ResponseDto.success(
//                MemberResponseDto.builder()
//                        .id(member.getId())
//                        .nickname(member.getNickname())
//                        .createdAt(member.getCreatedAt())
//                        .modifiedAt(member.getModifiedAt())
//                        .build()
//        );

        return ResponseDto.success(true);


    }

    @Transactional
    public ResponseDto<?> login(LoginRequestDto requestDto, HttpServletResponse response) {
        //Member member = isPresentMember(requestDto.getNickname());
        Member member = isPresentMemberEmail(requestDto.getEmail());
        if (null == member) {
//            return ResponseDto.fail("MEMBER_NOT_FOUND",
//                    "사용자를 찾을 수 없습니다.");

            return ResponseDto.success(false);
        }

        if (!member.validatePassword(passwordEncoder, requestDto.getPassword())) {
//            return ResponseDto.fail("INVALID_MEMBER", "사용자를 찾을 수 없습니다.");
            return ResponseDto.success(false);
        }


        TokenDto tokenDto = tokenProvider.generateTokenDto(member);
        tokenToHeaders(tokenDto, response);

        System.out.println(tokenDto.toString());


        return ResponseDto.success(member.getNickname());

    }

    // 로그아웃
    public ResponseDto<?> logout(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Member member = tokenProvider.getMemberFromAuthentication();
        if (null == member) {
            return ResponseDto.fail("MEMBER_NOT_FOUND",
                    "사용자를 찾을 수 없습니다.");
        }
        return tokenProvider.deleteRefreshToken(member);
    }


    @Transactional
    public Member validateMember(HttpServletRequest request) {

        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Member isPresentMemberEmail(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        return optionalMember.orElse(null);
    }

    //닉네임 중복 확인
    @Transactional(readOnly = true)
    public Member isPresentMember(String nickname) {
        Optional<Member> optionalMember = memberRepository.findByNickname(nickname);
        return optionalMember.orElse(null);
    }

    public void tokenToHeaders(TokenDto tokenDto, HttpServletResponse response) {
        response.addHeader("Authorization", "Bearer " + tokenDto.getAccessToken());
        response.addHeader("Refresh-Token", tokenDto.getRefreshToken());
        response.addHeader("Access-Token-Expire-Time", tokenDto.getAccessTokenExpiresIn().toString());
    }



    // ========================= 파일업로드 관련 메서드 ==========================

    // 로컬에 파일 업로드 하기
    private Optional<File> convert(MultipartFile file) throws IOException {

        String type = file.getContentType();
        long size = file.getSize();
        System.out.println("===================================="+type);
        System.out.println("===================================="+size);

        // 파일 타입 예외처리
        if (!type.startsWith("image")) {

            throw new FileTypeErrorException();

        }
        // 파일 크기 예외처리
//        if (size >=20480){
//
//            throw new FileSizeErrorException();
//        }

        File convertFile = new File(System.getProperty("user.dir") + "/" + file.getOriginalFilename());
        //File convertFile = new File(System.getProperty("image") + "/" + file.getOriginalFilename());

        if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 File이 생성됨 (경로가 잘못되었다면 생성 불가능)
            try (FileOutputStream fos = new FileOutputStream(convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }

    // 로컬에 저장된 파일을 S3로 파일업로드 세팅 및 S3로 업로드
    public String upload(File uploadFile, String dirName) {
        // S3에 저장될 파일 이름
        String fileName = dirName + "/" + UUID.randomUUID() + uploadFile.getName();
        // s3로 업로드 및  업로드 파일의 url을 String으로 받음.
        String uploadImageUrl = putS3(uploadFile, fileName);
        log.info(uploadImageUrl);
        removeNewFile(uploadFile);
        return uploadImageUrl;
    }
    // 이부분 질문하기.*************************
    // 이미지 변환 예외처리
    public String upload(MultipartFile multipartFile, String dirName) throws IOException {
        File uploadFile = convert(multipartFile)  // 파일 변환할 수 없으면 에러
                .orElseThrow(() -> new IllegalArgumentException("error: 파일 변환에 실패했습니다"));

        return upload(uploadFile, dirName);
    }

    // S3로 업로드 하는 메서드
    private String putS3(File uploadFile, String fileName) {
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    // s3에 파일 업로드 성공시 로컬에 저장된 이미지 지우기
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("File delete success");
            return;
        }
        log.info("File delete fail");
    }


}
