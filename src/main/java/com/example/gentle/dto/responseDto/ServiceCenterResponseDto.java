package com.example.gentle.dto.responseDto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ServiceCenterResponseDto {
    private Long id;
    private String username;
    private String email;
    private String title;
    private String content;
    private Boolean adminChecked;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}