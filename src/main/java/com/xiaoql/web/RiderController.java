//package com.xiaoql.web;
//
//import com.xiaoql.domain.Rider;
//import com.xiaoql.domain.RiderRepository;
//import com.xiaoql.domain.ShopOrder;
//import com.xiaoql.domain.ShopOrderRepository;
//import org.apache.commons.codec.digest.Md5Crypt;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.http.HttpStatus;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletResponse;
//import javax.transaction.Transactional;
//import java.nio.charset.StandardCharsets;
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/rider")
//public class RiderController {
//
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    private RiderRepository riderRepository;
//
//    @Autowired
//    private ShopOrderRepository shopOrderRepository;
//
//    private String token(String username) {
//        Md5Crypt.apr1Crypt(username);
//        return Base64.getEncoder().encodeToString(username.getBytes(StandardCharsets.UTF_8));
//    }
//
//    /**
//     * 骑手登录
//     */
//    @PostMapping("/token")
//    @Transactional
//    public Map<String, Object> login(String username, String password, HttpServletResponse response) {
//        Rider rider = riderRepository.findByLoginNameAndLoginPassword(username, password);
//        Map<String, Object> json = new HashMap<>();
//        if (rider == null) {
//            json.put("message", "用户名或密码错误");
//        } else {
//            json.put("token", rider.getId());
//            json.put("name", rider.getName());
//        }
//        return json;
//    }
//
//    /**
//     * 骑手开启、关闭接单
//     */
//    @PostMapping("/active")
//    @Transactional
//    public Object active(String token, boolean active, HttpServletResponse response) {
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//        Rider oRider = riderRepository.findOne(token);
//        oRider.setActive(active);
//        riderRepository.save(oRider);
//        return new RestResponse();
//    }
//
//
//    /**
//     * 骑手位置发送
//     */
//    @PostMapping("/position")
//    @Transactional
//    public Object position(String token, String lng, String lat, HttpServletResponse response) {
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//        Rider oRider = riderRepository.findOne(token);
//        oRider.setLat(lat);
//        oRider.setLng(lng);
//        riderRepository.save(oRider);
//        return new RestResponse();
//    }
//
//    /**
//     * 骑手的订单
//     */
//    @PostMapping("/orders")
//    @Transactional
//    public Object orders(String token, String riderState,
//                         @PageableParam(size = 50, page = 1, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable,
//                         HttpServletResponse response) {
//
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//
//        return shopOrderRepository.findByRiderIdAndRiderState(token, riderState, pageable);
//    }
//
//    /**
//     * 骑手的订单阅读时间
//     */
//    @PostMapping("/orders/read")
//    @Transactional
//    public Object ordersNews(@RequestParam String orderId,@RequestParam String token, HttpServletResponse response) {
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
//        shopOrder.setRiderReadTime(new Date());
//        shopOrderRepository.save(shopOrder);
//        return new RestResponse();
//    }
//
//    /**
//     * 骑手取单
//     */
//    @PostMapping("/orders/got")
//    @Transactional
//    public Object gotGoods(@RequestParam String orderId,@RequestParam String token,@RequestParam String lng,@RequestParam String lat, HttpServletResponse response) {
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//
//        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
//        shopOrder.setRiderState("psz");
//        shopOrder.setRiderGetGoodsTime(new Date());
//        shopOrder.setRiderGetGoodsLng(lng);
//        shopOrder.setRiderGetGoodsLat(lat);
//        shopOrderRepository.save(shopOrder);
//        return new RestResponse();
//    }
//
//    /**
//     * 骑手完成订单
//     */
//    @PostMapping("/orders/toUser")
//    @Transactional
//    public Object toUser(@RequestParam String orderId,@RequestParam String token,@RequestParam String lng,@RequestParam String lat, HttpServletResponse response) {
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//        Date now = new Date();
//        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
//        shopOrder.setRiderState("wc");
//        shopOrder.setRiderToUserTime(now);
//        shopOrder.setRiderToUserLng(lng);
//        shopOrder.setRiderToUserLat(lat);
//        shopOrder.setRiderCoast(now.getTime() - shopOrder.getRiderAssignTime().getTime());
//        shopOrderRepository.save(shopOrder);
//        return new RestResponse();
//    }
//
//    /**
//     * 骑手新订单提醒
//     */
//    @PostMapping("/orders/alert")
//    public Object alert(@RequestParam String token, @RequestParam String time, HttpServletResponse response) {
//        if (!riderRepository.exists(token)) {
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            return new RestResponse(true, "骑手未登录");
//        }
//
//        List list = jdbcTemplate.queryForList("select id from shop_order where rider_id='" + token + "' and rider_state='dqc' and rider_assign_time>'" + time + "' limit 0,1");
//        Map<String, Object> map = new HashMap<>();
//        map.put("alert", list.size() > 0);
//        return map;
//    }
//
//
//}
