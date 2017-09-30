package com.xiaoql.API;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoql.entity.Rider;
import com.xiaoql.entity.RiderExample;
import com.xiaoql.entity.ShopOrder;
import com.xiaoql.entity.ShopOrderExample;
import com.xiaoql.mapper.RiderMapper;
import com.xiaoql.mapper.ShopOrderMapper;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;

@RestController
@RequestMapping("/api/rider")
public class RiderAPI {

    @Autowired
    private RiderMapper riderMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    private String token(String username) {
        Md5Crypt.apr1Crypt(username);
        return Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 骑手登录
     */
    @PostMapping("/token")
    @Transactional
    public Map<String, Object> login(String username, String password) {
        RiderExample riderExample = new RiderExample();
        riderExample.createCriteria().andLoginNameEqualTo(username).andLoginPasswordEqualTo(password);
        List<Rider> riders = riderMapper.selectByExample(riderExample);
        Map<String, Object> json = new HashMap<>();
        if (riders.size() > 0) {
            json.put("token", riders.get(0).getId());
            json.put("name", riders.get(0).getName());
        } else {
            json.put("message", "用户名或密码错误");
        }
        return json;
    }

    /**
     * 骑手开启、关闭接单
     */
    @PostMapping("/active")
    @Transactional
    public Object active(String token, boolean active, HttpServletResponse response) {

        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        riderMapper.updateByPrimaryKey(rider);
        return new RestResponse();
    }


    /**
     * 骑手位置发送
     */
    @PostMapping("/position")
    @Transactional
    public Object position(String token, String lng, String lat, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        rider.setLat(lat);
        rider.setLng(lng);
        riderMapper.updateByPrimaryKey(rider);
        return new RestResponse();
    }

    /**
     * 骑手的订单
     */
    @PostMapping("/orders")
    @Transactional
    public Object orders(String token, int status,
                         @RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "25") Integer size, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        ShopOrderExample example = new ShopOrderExample();
        example.createCriteria().andRiderIdEqualTo(token).andStatusEqualTo(status);
        example.setOrderByClause("time desc");
        PageHelper.startPage(page, size);
        return new PageInfo<ShopOrder>(shopOrderMapper.selectByExample(example));
    }

    /**
     * 骑手的订单阅读时间
     */
    @PostMapping("/orders/read")
    @Transactional
    public Object ordersNews(@RequestParam String orderId, @RequestParam String token, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        ShopOrder shopOrder = shopOrderMapper.selectByPrimaryKey(orderId);
        shopOrder.setRiderReadTime(new Date());
        shopOrderMapper.updateByPrimaryKey(shopOrder);
        return new RestResponse();
    }

    /**
     * 骑手取单
     */
    @PostMapping("/orders/got")
    @Transactional
    public Object gotGoods(@RequestParam String orderId, @RequestParam String token, @RequestParam String lng, @RequestParam String lat, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        ShopOrder shopOrder = shopOrderMapper.selectByPrimaryKey(orderId);
        shopOrder.setStatus(Status.OrderRiderGotGoods);
        shopOrder.setRiderGetGoodsTime(new Date());
        shopOrder.setRiderGetGoodsLng(lng);
        shopOrder.setRiderGetGoodsLat(lat);
        shopOrderMapper.updateByPrimaryKey(shopOrder);
        return new RestResponse();
    }

    /**
     * 骑手完成订单
     */
    @PostMapping("/orders/toUser")
    @Transactional
    public Object toUser(@RequestParam String orderId, @RequestParam String token, @RequestParam String lng, @RequestParam String lat, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        Date now = new Date();
        ShopOrder shopOrder = shopOrderMapper.selectByPrimaryKey(orderId);
        shopOrder.setStatus(Status.OrderCompleted);
        shopOrder.setRiderCompleteTime(now);
        shopOrder.setRiderCompleteLng(lng);
        shopOrder.setRiderCompleteLat(lat);
        shopOrder.setRiderCoast((int) ((now.getTime() - shopOrder.getRiderAssignTime().getTime()) / 1000));
        shopOrderMapper.updateByPrimaryKey(shopOrder);
        return new RestResponse();
    }

    /**
     * 骑手新订单提醒
     */
    @PostMapping("/orders/alert")
    public Object alert(@RequestParam String token, @RequestParam String time, HttpServletResponse response) throws ParseException {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        ShopOrderExample example = new ShopOrderExample();
        example.createCriteria().andRiderIdEqualTo(token).andStatusEqualTo(Status.OrderRiderAssgin)
                .andRiderAssignTimeGreaterThan(Utils.toDate(time));
        PageHelper.startPage(1, 1, false);
        List list = shopOrderMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("alert", list.size() > 0);
        return map;
    }

}