package com.xiaoql.web;

import com.xiaoql.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
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
