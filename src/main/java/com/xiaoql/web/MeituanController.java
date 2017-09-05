package com.xiaoql.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoql.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meituan")
public class MeituanController {

    public static final String DQC = "dqc";
    //public static final String PSZ = "psz";
    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ObjectMapper objectMapper;



    @GetMapping({"/shops"})
    public List<Shop> shops() {
        return shopRepository.findAll().stream().map(shop -> {
            shop.setStealing(MeitunSpider.DRIVERMAP.containsKey(shop.getId()));
            return shop;
        }).collect(Collectors.toList());
    }

    @PutMapping("/shops/{id}")
    public void shops(@PathVariable String id, @RequestBody Map<String, Object> toShop, HttpServletResponse response) {
        if (toShop.get("loginName") != null
                && StringUtils.isNotBlank(toShop.get("loginName").toString())
                && shopRepository.findByLoginName(toShop.get("loginName").toString()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        Shop shop = shopRepository.getOne(id);
        shop.update(toShop);
        shopRepository.save(shop);
    }

    @PostMapping("/shops")
    public Object shops(@RequestBody Shop toShop, HttpServletResponse response) {
        if (shopRepository.findByLoginName(toShop.getLoginName()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "登录名重复");
        }
        shopRepository.save(toShop);
        return new RestResponse();
    }

    @DeleteMapping("/shops/{id}")
    public Object shopsDel(@PathVariable String id, HttpServletResponse response) {
        shopRepository.delete(id);
        return new RestResponse();
    }


    @GetMapping({"/dailyOrders"})
    public List<ShopOrder> dailyOrders() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return shopOrderRepository.findByTimeAfter(today.getTime());
    }

    @PostMapping({"/orders"})
    @Transactional
    public void orders(@RequestBody Map<String, Object> requestBody) throws JsonProcessingException {

        Map<String, Object> body = (Map<String, Object>) ((List) requestBody.get("data")).get(0);

        Date orderTime = new Date();
        orderTime.setTime(Long.valueOf((Integer) body.get("order_time"))*1000);
        ShopOrder order = new ShopOrder();
        order.setShopId(body.get("wm_poi_id").toString());
//        order.setId(body.get("id").toString());
        order.setId(body.get("wm_order_id_view_str").toString());
        order.setMeituanViewId(body.get("wm_order_id_view_str").toString());
        order.setTime(orderTime);
        order.setDescription(body.get("orderCopyContent").toString());
        //order.setShopAddress(body.get("poi_name").toString());
        order.setShopName(body.get("poi_name").toString());
        //order.setShopPhone(shop.getPhone());
        //order.setRemark(item.get("remark").toString());
        order.setUserName(body.get("recipient_name").toString());
        order.setState(body.get("pay_status").toString());
        order.setUserPhone(body.get("recipient_phone").toString());
        //Map<String, Object> images = (Map<String, Object>) ordersImages.get(orderId);
        // order.setUserPhoneImg(images.get("recipient_phone").toString());
        order.setUserAddress(body.get("recipient_address").toString());
        //order.setUserAddressImg(images.get("recipient_address").toString());
        // WebElement mapInfo = li.findElement(By.className("j-show-map"));
        order.setOrderLng(body.get("address_longitude").toString());
        order.setOrderLat(body.get("address_latitude").toString());
        order.setShopLng(body.get("poi_longitude").toString());
        order.setShopLat(body.get("poi_latitude").toString());
        order.setRaw(objectMapper.writeValueAsString(body));
        shopOrderRepository.saveAndFlush(order);

    }

    @GetMapping({"/orders"})
    public Page<ShopOrder> orders(@PageableParam(size = 50, page = 1, sort = {"time"}, direction = Sort.Direction.DESC) Pageable pageable) {
        return shopOrderRepository.findAll(pageable);
    }

    @GetMapping(value = {"/orders"}, params = {"state"})
    public List<ShopOrder> orders(@RequestParam String state) {
        if ("dzp".equalsIgnoreCase(state)) {
            return shopOrderRepository.findByRiderIdIsNullOrderByTimeDesc();
        } else {
            return shopOrderRepository.findByRiderStateOrderByTimeDesc(state);
        }
    }

    @PutMapping({"/orders/{id}"})
    public void orders(@PathVariable String id, @RequestBody Map<String, Object> toOrder, HttpServletResponse response) {
        ShopOrder shopOrder = shopOrderRepository.getOne(id);
        if (shopOrder == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        shopOrder.update(toOrder);
        shopOrderRepository.save(shopOrder);
    }

    @PostMapping({"/orders/{id}/asign/{riderId}"})
    public void orderAsignRider(@PathVariable String id, @PathVariable String riderId, HttpServletResponse response) {
        ShopOrder shopOrder = shopOrderRepository.getOne(id);
        if (shopOrder == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        Rider rider = riderRepository.getOne(riderId);
        shopOrder.setRiderState(DQC);
        shopOrder.setRiderAssignTime(new Date());
        shopOrder.setRider(rider);
        shopOrderRepository.save(shopOrder);
    }

    @PostMapping({"/orders/{id}/reasign/{riderDisplayName}"})
    public void orderReAsignRider(@PathVariable String id, @PathVariable String riderDisplayName, HttpServletResponse response) {
        String[] parts = riderDisplayName.split(":");
        ShopOrder shopOrder = shopOrderRepository.getOne(id);
        if (shopOrder == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        Rider rider = riderRepository.findByPhone(parts[0]);
        shopOrder.setRiderState(DQC);
        shopOrder.setRiderAssignTime(new Date());
        shopOrder.setRider(rider);
        shopOrderRepository.save(shopOrder);
    }





}
