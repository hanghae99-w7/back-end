package com.example.gentle.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class BasketResponseDto {
    private Long itemId;
    private String detailPageUrl;
    private String imgUrl ;
    private String productName;
    private Integer price;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}