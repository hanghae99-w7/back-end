package com.example.gentle.service;

import com.example.gentle.domain.ItemInfo;
import com.example.gentle.dto.responseDto.ItemInfoResponseDto;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.repository.ItemInfoRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemInfoService {

    private final ItemInfoRepository itemInfoRepository;

    public ItemInfoService(ItemInfoRepository itemInfoRepository) {

        this.itemInfoRepository = itemInfoRepository;

    }

    @Transactional(readOnly = true)
    public ResponseEntity<?> getItemInfo(String category,
                                         String orderBy,
                                         Pageable pageable) {

        if (orderBy.equals("id")) {
            List<ItemInfo> itemInfoList = itemInfoRepository.findByCategory(category, pageable);
            return getResponseEntity(itemInfoList);

        } else if (orderBy.equals("priceup")) {
            List<ItemInfo> itemInfoList = itemInfoRepository.findByCategoryOrderByPriceDesc(category, pageable);
            return getResponseEntity(itemInfoList);
        }

        List<ItemInfo> itemInfoList = itemInfoRepository.findByCategoryOrderByPrice(category, pageable);
        return getResponseEntity(itemInfoList);

    }

    private ResponseEntity<?> getResponseEntity(List<ItemInfo> itemInfoList) {
        List<ItemInfoResponseDto> itemInfoResponseDtoList = new ArrayList<>();

        for (ItemInfo itemInfo : itemInfoList) {
            itemInfoResponseDtoList.add(
                    ItemInfoResponseDto.builder()
                            .itemId(itemInfo.getId())
                            .detailPageUrl(itemInfo.getDetailPageUrl())
                            .imgUrl(itemInfo.getImgUrl())
                            .productName(itemInfo.getProductName())
                            .price(itemInfo.getPrice())
                            .createdAt(itemInfo.getCreatedAt())
                            .modifiedAt(itemInfo.getModifiedAt())
                            .build()
            );
        }

        return new ResponseEntity<>(Message.success(itemInfoResponseDtoList), HttpStatus.OK);
    }
}