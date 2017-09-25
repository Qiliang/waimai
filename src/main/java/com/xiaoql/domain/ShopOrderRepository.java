package com.xiaoql.domain;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface ShopOrderRepository extends JpaRepository<ShopOrder, String>, JpaSpecificationExecutor {


    @Query(value = "SELECT new ShopOrder(a.id,a.meituanViewId,a.shopId,a.shopName,a.shopAddress,a.shopPhone,a.time,a.state,a.userName,a.userPhone,a.userAddress,a.remark,a.shopLng,a.shopLat,a.orderLng,a.orderLat,a.totalAfter,a.riderState,a.riderAssignTime,a.riderReadTime,a.riderGetGoodsTime,a.riderGetGoodsLng,a.riderGetGoodsLat,a.riderToUserTime,a.riderToUserLng,a.riderToUserLat,a.riderCoast,a.description) from ShopOrder a where  a.rider.id=?1 and a.riderState=?2 order by a.time desc")
    Page<ShopOrder> findByRiderIdAndRiderState(String riderId, String riderState, Pageable pageable);

    @Query(value = "SELECT new ShopOrder(a.id,a.meituanViewId,a.shopId,a.shopName,a.shopAddress,a.shopPhone,a.time,a.state,a.userName,a.userPhone,a.userAddress,a.remark,a.shopLng,a.shopLat,a.orderLng,a.orderLat,a.totalAfter,a.riderState,a.riderAssignTime,a.riderReadTime,a.riderGetGoodsTime,a.riderGetGoodsLng,a.riderGetGoodsLat,a.riderToUserTime,a.riderToUserLng,a.riderToUserLat,a.riderCoast,a.description) from ShopOrder a where  a.rider is null order by a.time desc")
    List<ShopOrder> findByRiderIdIsNullOrderByTimeDesc(Pageable pageable);

    List<ShopOrder> findByRiderStateOrderByTimeDesc(String riderState);

    @Query(value = "SELECT new ShopOrder(a.id) from ShopOrder a where  a.rider.id=?1")
    List<ShopOrder> findByRider(String riderId);


}