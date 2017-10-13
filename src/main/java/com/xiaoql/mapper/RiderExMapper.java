package com.xiaoql.mapper;

import com.xiaoql.entity.Rider;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;


@Mapper
public interface RiderExMapper {

    @Select("select rider.*,shop.`name` shop_name from rider left join shop on rider.shop_id=shop.id order by rider.id")
    @Results({
            @Result(id=true, column="id", property="id"),
            @Result(column="name", property="name"),
            @Result(column="login_name", property="loginName"),
            @Result(column="login_password", property="loginPassword"),
            @Result(column="phone", property="phone"),
            @Result(column="lng", property="lng"),
            @Result(column="lat", property="lat"),
            @Result(column="status", property="status"),
            @Result(column="shop_id", property="shopId"),
            @Result(column="client_id", property="clientId"),
            @Result(column="order_count", property="orderCount"),
            @Result(column="last_modify_time", property="lastModifyTime"),
            @Result(column="shop_name", property="shopName")
    })
    List<Map> getAllWithShop();


}