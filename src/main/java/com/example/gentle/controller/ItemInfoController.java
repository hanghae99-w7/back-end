package com.example.gentle.controller;

import com.example.gentle.service.DetailItemPageService;
import com.example.gentle.service.ItemInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/iteminfo")
public class ItemInfoController {

    private final ItemInfoService itemInfoService;
    private final DetailItemPageService detailItemPageService;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getItemInfo(@RequestParam String category,
                                         @RequestParam String orderBy,
                                         Pageable pageable) {
        return itemInfoService.getItemInfo(category, orderBy, pageable);
    }

    @RequestMapping(value = "/{itemId}",method = RequestMethod.GET)
    public ResponseEntity<?> getDetailItemInfo(@PathVariable Long itemId, HttpServletRequest request){
        return detailItemPageService.getDatailItemPage(itemId);
    }
}
