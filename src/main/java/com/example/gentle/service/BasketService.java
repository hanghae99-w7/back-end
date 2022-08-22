package com.example.gentle.service;


import com.example.gentle.domain.Basket;
import com.example.gentle.domain.ItemInfo;
import com.example.gentle.domain.Member;
import com.example.gentle.dto.requestDto.BasketRequestDto;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.jwt.TokenProvider;
import com.example.gentle.repository.BasketRepository;
import com.example.gentle.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {

    private final ItemInfoRepository itemInfoRepository;
    private final BasketRepository basketRepository;
    private final TokenProvider tokenProvider;
    @Transactional
    public ResponseEntity<?> addItemInMyBasket(Long id, HttpServletRequest request) {


        if (null == request.getHeader("Refresh-Token")) {
        return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND","로그인이 필요합니다."),HttpStatus.UNAUTHORIZED);
        }

        if (null == request.getHeader("Authorization")) {
            return new ResponseEntity<>(Message.fail("MEMBER_NOT_FOUND","로그인이 필요합니다."),HttpStatus.UNAUTHORIZED);
        }

        Member member = validateMember(request);
        if (null == member) {
            return new ResponseEntity<>(Message.fail("INVALID_TOKEN", "Token이 유효하지 않습니다."), HttpStatus.UNAUTHORIZED);
        }

        ItemInfo itemInfo = getPresentInfo(id);
        if (null == itemInfo) {
            return new ResponseEntity<>(Message.fail("ITEM_NOT_FOUND", "상품이 존재하지 않습니다."), HttpStatus.NOT_FOUND);
        }


        Basket intoBasket = getPresentBasket(itemInfo);
        if (null == intoBasket) {
            basketRepository.save(Basket.builder()
                    .member(member)
                    .itemInfo(itemInfo)
                    .build());

            return new ResponseEntity<>(Message.success("장바구니에 담겼습니다."), HttpStatus.OK);
        } else {
            basketRepository.delete(intoBasket);
            return new ResponseEntity<>(Message.success("장바구니에서 삭제되었습니다."), HttpStatus.OK);
        }

    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public Basket getPresentBasket (ItemInfo itemInfo) {
        Optional<Basket> optionalBasket = basketRepository.findByItemInfo(itemInfo);
        return optionalBasket.orElse(null);
    }

    @Transactional(readOnly = true)
    public ItemInfo getPresentInfo (Long id) {
        Optional<ItemInfo> optionalItemInfo = itemInfoRepository.findById(id);
        return optionalItemInfo.orElse(null);
    }

}
