package com.xiaoql.web;

import com.xiaoql.domain.Rider;
import com.xiaoql.domain.RiderRepository;
import com.xiaoql.domain.ShopOrder;
import com.xiaoql.domain.ShopOrderRepository;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rider")
public class RiderController {


    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    private String token(String username) {
        Md5Crypt.apr1Crypt(username);
        return Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 骑手登录
     */
    @PostMapping("/token")
    public Map<String, Object> login(String username, String password) {
        Rider rider = riderRepository.findByLoginNameAndLoginPassword(username, password);
        Map<String, Object> json = new HashMap<>();
        json.put("token", rider.getId());
        json.put("name", rider.getName());
        return json;
    }

    /**
     * 骑手开启、关闭接单
     */
    @PostMapping("/active")
    public Object active(String token, boolean active, HttpServletResponse response) {
        if (!riderRepository.exists(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        Rider oRider = riderRepository.findOne(token);
        oRider.setActive(active);
        riderRepository.save(oRider);
        return new RestResponse();
    }


    /**
     * 骑手位置发送
     */
    @PostMapping("/position")
    public Object position(String token, String lng, String lat, HttpServletResponse response) {
        if (!riderRepository.exists(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        Rider oRider = riderRepository.findOne(token);
        oRider.setLat(lat);
        oRider.setLng(lng);
        riderRepository.save(oRider);
        return new RestResponse();
    }

    /**
     * 骑手的订单
     */
    @PostMapping("/orders")
    public Object orders(String token, String riderState,
                         @PageableParam(size = 50, page = 1, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable,
                         HttpServletResponse response) {

        if (!riderRepository.exists(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        return shopOrderRepository.findByRiderIdAndRiderState(token, riderState, pageable);
    }

    /**
     * 骑手的订单阅读时间
     */
    @PostMapping("/orders/read")
    public Object ordersNews(@RequestParam String orderId,@RequestParam String token, HttpServletResponse response) {
        if (!riderRepository.exists(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
        shopOrder.setRiderReadTime(new Date());
        shopOrderRepository.save(shopOrder);
        return new RestResponse();
    }

    /**
     * 骑手取单
     */
    @PostMapping("/orders/got")
    public Object gotGoods(@RequestParam String orderId,@RequestParam String token,@RequestParam String lng,@RequestParam String lat, HttpServletResponse response) {
        if (!riderRepository.exists(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
        shopOrder.setRiderState("psz");
        shopOrder.setRiderGetGoodsTime(new Date());
        shopOrder.setRiderGetGoodsLng(lng);
        shopOrder.setRiderGetGoodsLat(lat);
        shopOrderRepository.save(shopOrder);
        return new RestResponse();
    }

    /**
     * 骑手完成订单
     */
    @PostMapping("/orders/toUser")
    public Object toUser(@RequestParam String orderId,@RequestParam String token,@RequestParam String lng,@RequestParam String lat, HttpServletResponse response) {
        if (!riderRepository.exists(token)) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        Date now = new Date();
        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
        shopOrder.setRiderState("wc");
        shopOrder.setRiderToUserTime(now);
        shopOrder.setRiderToUserLng(lng);
        shopOrder.setRiderToUserLat(lat);
        shopOrder.setRiderCoast(now.getTime() - shopOrder.getRiderAssignTime().getTime());
        shopOrderRepository.save(shopOrder);
        return new RestResponse();
    }


}
