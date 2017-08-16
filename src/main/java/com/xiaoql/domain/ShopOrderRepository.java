package com.xiaoql.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Date;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, String>, JpaSpecificationExecutor {

    List<ShopOrder> findByTimeAfter(Date date);

    Page<ShopOrder> findByRiderIdAndRiderState(String riderId, String riderState, Pageable pageable);

    List<ShopOrder> findByRiderIdIsNullOrderByTimeDesc();

    List<ShopOrder> findByRiderStateOrderByTimeDesc(String riderState);

}