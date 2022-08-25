package com.example.gentle.repository;


import com.example.gentle.domain.Basket;
import com.example.gentle.domain.ItemInfo;
import com.example.gentle.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket,Long> {
    List<Basket>findByMember(Member member);
    Basket findByItemInfo(ItemInfo itemInfo);

}
