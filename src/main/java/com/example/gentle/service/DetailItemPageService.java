package com.example.gentle.service;

import com.example.gentle.domain.ItemInfo;
import com.example.gentle.dto.responseDto.Message;
import com.example.gentle.repository.ItemInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class DetailItemPageService {

   private final ItemInfoRepository itemInfoRepository;

   public ResponseEntity<?> getDatailItemPage(Long itemId){
       ItemInfo itemInfo1 =itemInfoRepository.findById(itemId).orElseThrow(
               ()-> new IllegalArgumentException("없는 아이템 정보입니다.")
       );
       return new ResponseEntity<>(Message.success(itemInfo1), HttpStatus.OK);
   }
}
