package com.xiaoql.API;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoql.entity.Rider;
import com.xiaoql.entity.RiderExample;
import com.xiaoql.entity.ShopOrder;
import com.xiaoql.entity.ShopOrderExample;
import com.xiaoql.mapper.RiderMapper;
import com.xiaoql.mapper.ShopOrderExMapper;
import com.xiaoql.mapper.ShopOrderMapper;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/rider")
public class RiderAPI {

    @Autowired
    private RiderMapper riderMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;
    @Autowired
    private ShopOrderExMapper  shopOrderExMapper;

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
     * 骑手自身状态
     */
    @PostMapping("/me")
    @Transactional
    public Object me(String token, HttpServletResponse response) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        Map<String, String> me = BeanUtilsBean.getInstance().describe(rider);
        me.remove("loginName");
        me.remove("loginPassword");
        me.remove("orderCount");
        me.remove("class");
        return me;
    }

    /**
     * 骑手开启、关闭接单
     */
    @PostMapping("/active")
    @Transactional
    public Object active(String token, int status, HttpServletResponse response) {

        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        riderMapper.updateByPrimaryKeySelective(new Rider() {{
            setId(token);
            setStatus(status);
        }});

        return new RestResponse();
    }


    /**
     * 骑手位置发送
     */
    @PostMapping("/position")
    @Transactional
    public Object position(String token, String lng, String lat, String clientId, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }

        riderMapper.updateByPrimaryKeySelective(new Rider(){{
            setId(token);
            setLat(lat);
            setLng(lng);
            setClientId(clientId);
            setLastModifyTime(new Date());
        }});
        return new RestResponse();
    }

    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 60 * 1000)
    public void shop() throws IOException {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, -2);
        riderMapper.updateByExampleSelective(new Rider() {{
            setStatus(0);
        }}, new RiderExample() {{
            createCriteria().andLastModifyTimeLessThan(calendar.getTime()).andStatusEqualTo(1);
        }});
    }

    /**
     * 骑手的订单
     */
    @PostMapping("/orders")
    @Transactional
    public Object orders(String token, String status,
                         @RequestParam(required = false, defaultValue = "1") Integer page,
                         @RequestParam(required = false, defaultValue = "25") Integer size, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(token);
        if (rider == null) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            return new RestResponse(true, "骑手未登录");
        }
        ShopOrderExample example = new ShopOrderExample();
        Arrays.stream(status.split(",")).map(s -> Integer.parseInt(s)).collect(Collectors.toList()).forEach(st -> {
            example.or().andRiderIdEqualTo(token).andStatusEqualTo(st);
        });

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

        shopOrderExMapper.batchReadUpdate(new Date(),orderId);
//        ShopOrder shopOrder = shopOrderMapper.selectByPrimaryKey(orderId);
//        shopOrder.setRiderReadTime(new Date());
//        shopOrderMapper.updateByPrimaryKey(shopOrder);
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
        if (shopOrder.getRiderAssignTime() == null) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "订单未指派");
        }

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
                .andRiderAssignTimeGreaterThan(Utils.toDate(time)).andRiderReadTimeIsNotNull();
        PageHelper.startPage(1, 1, false);
        List list = shopOrderMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("alert", list.size() > 0);
        return map;
    }

}
