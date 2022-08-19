package com.example.gentle.domain;


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
public class Basket extends Timestamped{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "glasses", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Glasses glasses;

    @JoinColumn(name = "sunglass", nullable = true)
    @ManyToOne(fetch = FetchType.LAZY)
    private Sunglass sunglass;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
