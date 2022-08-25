package com.example.gentle.service;


import com.example.gentle.domain.Basket;
import com.example.gentle.domain.ItemInfo;
import com.example.gentle.domain.Member;
import com.example.gentle.dto.responseDto.BasketResponseDto;
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
import java.util.ArrayList;
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


        Basket basket = getDuplicationCheck(itemInfo);
        if (null == basket) {
            Basket basket1 = Basket.builder()
                    .member(member)
                    .itemInfo(itemInfo)
                    .build();

            basketRepository.save(basket1);

            return new ResponseEntity<>(Message.success(
                    BasketResponseDto.builder()
                            .itemId(basket1.getItemInfo().getId())
                            .detailPageUrl(basket1.getItemInfo().getDetailPageUrl())
                            .imgUrl(basket1.getItemInfo().getImgUrl())
                            .productName(basket1.getItemInfo().getProductName())
                            .price(basket1.getItemInfo().getPrice())
                            .createdAt(basket1.getCreatedAt())
                            .modifiedAt(basket1.getModifiedAt())
                            .build()
            ),
                    HttpStatus.OK
            );
        }

        return new ResponseEntity<>(Message.success("이미 장바구니에 담겨 있습니다."), HttpStatus.OK);
        
        

    }

    @Transactional
    public ResponseEntity<?> deleteMyBasket(Long id, HttpServletRequest request) {

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

        Basket basket = getDeleteBasket(id);

        if (null == basket) {
            return new ResponseEntity<>(Message.fail("ITEM_NOT_FOUND", "해당 아이템이 없습니다."), HttpStatus.NOT_FOUND);
        }

        basketRepository.delete(basket);

        return new ResponseEntity<>(Message.success(
                BasketResponseDto.builder()
                        .itemId(basket.getItemInfo().getId())
                        .detailPageUrl(basket.getItemInfo().getDetailPageUrl())
                        .imgUrl(basket.getItemInfo().getImgUrl())
                        .productName(basket.getItemInfo().getProductName())
                        .price(basket.getItemInfo().getPrice())
                        .createdAt(basket.getCreatedAt())
                        .modifiedAt(basket.getModifiedAt())
                        .build()
        ),HttpStatus.OK);
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

    @Transactional(readOnly = true)
    public ResponseEntity<?> getBasketByMember (HttpServletRequest request){

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
        List<Basket> basketList = basketRepository.findByMember(member);
        List<BasketResponseDto> basketResponseDtoList = new ArrayList<>();
        for (Basket basket : basketList) {
            basketResponseDtoList.add(
                    BasketResponseDto.builder()
                    .itemId(basket.getItemInfo().getId())
                    .detailPageUrl(basket.getItemInfo().getDetailPageUrl())
                    .imgUrl(basket.getItemInfo().getImgUrl())
                    .productName(basket.getItemInfo().getProductName())
                    .price(basket.getItemInfo().getPrice())
                    .createdAt(basket.getCreatedAt())
                    .modifiedAt(basket.getModifiedAt())
                    .build()
            );
        }

        return new ResponseEntity<>(Message.success(basketResponseDtoList),HttpStatus.OK);
        }
        

    public Basket getDuplicationCheck(ItemInfo itemInfo) {
        Optional<Basket> optionalBasket = basketRepository.findByItemInfo(itemInfo);
        return optionalBasket.orElse(null);

    }

    public Basket getDeleteBasket(Long id) {
        Optional<Basket> optionalBasket = basketRepository.findById(id);
        return optionalBasket.orElse(null);
    }

}
