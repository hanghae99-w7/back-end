package com.example.gentle.domain;


import com.example.gentle.util.Timestamped;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Basket extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "glasses", nullable = true)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Glasses> glasses;

    @JoinColumn(name = "sunglass", nullable = true)
    @OneToMany(fetch = FetchType.LAZY)
    private List<Sunglass> sunglass;

    @JoinColumn(name = "member", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Member member;
}
