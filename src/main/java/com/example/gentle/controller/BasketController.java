package com.example.gentle.controller;

import com.example.gentle.dto.requestDto.BasketRequestDto;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/item")
public class BasketController {

    private final BasketService basketService;

    // 장바구니에 상품 추가
    @PostMapping("/basket/{id}")
    public ResponseEntity<?> addItemInMyBasket(@PathVariable Long id, HttpServletRequest request){
        return new ResponseEntity<>(Message.success(basketService.addItemInMyBasket(id,request)), HttpStatus.OK);
    }


    //상품제거
    @DeleteMapping("/basket/{id}")
    public ResponseEntity<?> deleteMyBasket(@PathVariable Long id) {
        return new ResponseEntity<>(Message.success(basketService.deleteMyBasket(id)),HttpStatus.OK);
    }

}
