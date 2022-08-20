package com.example.gentle.domain;

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

    @JoinColumn(name = "member")
    @ManyToOne
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

}
