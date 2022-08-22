package com.example.gentle.controller;

import com.example.gentle.repository.ItemInfoRepository;
import com.example.gentle.service.DetailItemPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/item")
public class ItemInfoController {
    private final DetailItemPageService detailItemPageService;

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<?> getDetailItemInfo(@PathVariable Long itemId, HttpServletRequest request){
        return detailItemPageService.getDatailItemPage(itemId);
    }
}
