package com.xiaoql.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


@Mapper
public interface ShopOrderExMapper {

    @Select("SELECT avg(rider_coast) FROM shop_order")
    Double avgCoast();

    @Select("select HOUR(time) hour,count(*) orders from shop_order where time>#{time} group by hour")
    List<Map<String, Object>> statDailyOrders(String time);

    @Select("select DATE_FORMAT(time,'%Y-%m-%d') as d,count(*) count from shop_order group by d ORDER BY d desc LIMIT 0,30")
    List<Map<String, Object>> statByDay();


    @Select("select DATE_FORMAT(time,'%Y-%m-%d') as d,count(*) count,sum(shipping_fee) rprice from shop_order where time>#{from} and time<#{to} and rider_id=#{riderId} and status=#{status}  group by d ORDER BY d desc LIMIT 0,30")
    List<Map<String, Object>> statRiderByDay(String from, String to, String riderId, int status);


    @Select("select rider_id,num,price,rprice,name,phone from (select rider_id,count(id) num,sum(total) price ,sum(shipping_fee) rprice from shop_order where time>#{from} and time<#{to} and status=#{status} group by rider_id) a INNER JOIN rider b on a.rider_id=b.id")
    List<Map<String, Object>> statRiders(String from, String to, int status);

    @Select("select count(id) num,sum(total_after) price from shop_order where time>#{from} and time<#{to} and rider_id=#{riderId} and status=#{status} order by time desc")
    List<Map<String, Object>> statRider(String from, String to, String riderId, int status);

    @Select("select * from shop_order where time>#{from} and time<#{to} and rider_id=#{riderId} and status=#{status} order by time desc")
    List<Map<String, Object>> statRiderDetail(String from, String to, String riderId, int status);

    @Select("select shop_name,count(shop_name) num,sum(total) price from shop_order where time>#{from} and time<{to} and status=#{status} group by shop_name")
    List<Map<String, Object>> statByShop(String from, String to, int status);


}