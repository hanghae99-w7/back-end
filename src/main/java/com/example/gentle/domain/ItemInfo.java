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
public class ItemInfo extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imgUrl;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private String detailPageUrl;

    @Column(nullable = false)
    private String category;
    
}
