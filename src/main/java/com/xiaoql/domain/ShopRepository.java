package com.xiaoql.domain;

import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, String> {

    List<Shop> findByLoginName(String loginName);

    @Transactional
    Shop findById(String Id);
}