package com.example.gentle.repository;

import com.example.gentle.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByName(String name);

    Optional<Member> findByEmail(String email);

    Optional<Member> findByKakaoId(Long kakaoId);

}