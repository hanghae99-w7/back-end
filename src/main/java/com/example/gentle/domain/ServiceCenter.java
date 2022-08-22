package com.example.gentle.domain;

import com.example.gentle.dto.requestDto.ServiceCenterRequestDto;
import com.example.gentle.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ServiceCenter extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "member_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean adminCheck;


    public void updateAdminCheck() {
        this.adminCheck = true;
    }

    public void updateContact(ServiceCenterRequestDto requestDto) {
        this.content = requestDto.getContent();
    }
}