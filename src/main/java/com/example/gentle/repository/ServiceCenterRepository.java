package com.example.gentle.repository;

import com.example.gentle.domain.Member;
import com.example.gentle.domain.ServiceCenter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServiceCenterRepository extends JpaRepository<ServiceCenter, Long> {
    List<ServiceCenter> findByMember(Member member);
}
