package com.example.gentle.controller;


import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketRepository basketRepository;

    // 장바구니에 상품 추가
    @RequestMapping(value = "/{memberId}", method = RequestMethod.POST)
    public ResponseEntity<?> addItemInMyBasket(){

        return
    }
}
