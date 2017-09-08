package com.xiaoql.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, String>, JpaSpecificationExecutor {

    List<ShopOrder> findByTimeAfter(Date date);

    Page<ShopOrder> findByRiderIdAndRiderState(String riderId, String riderState, Pageable pageable);

    @Query(value = "SELECT new ShopOrder(a.id,a.meituanViewId,a.shopId,a.shopName,a.shopAddress,a.shopPhone,a.time,a.stime,a.state,a.userName,a.userPhone,a.userAddress,a.remark,a.shopLng,a.shopLat,a.orderLng,a.orderLat,a.description,a.totalAfter) from ShopOrder a where  a.rider is null order by a.time desc")
    List<ShopOrder> findByRiderIdIsNullOrderByTimeDesc(Pageable pageable);

    List<ShopOrder> findByRiderStateOrderByTimeDesc(String riderState);



}