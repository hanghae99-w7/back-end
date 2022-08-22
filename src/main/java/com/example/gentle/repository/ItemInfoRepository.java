package com.example.gentle.repository;

import com.example.gentle.domain.ItemInfo;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemInfoRepository extends JpaRepository<ItemInfo, Long> {

    List<ItemInfo> findByCategory(String category, Pageable pageable);
    List<ItemInfo> findByCategoryOrderByPriceDesc(String category, Pageable pageable);
    List<ItemInfo> findByCategoryOrderByPrice(String category, Pageable pageable);

}