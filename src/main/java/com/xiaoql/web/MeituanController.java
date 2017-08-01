package com.xiaoql.web;

import com.xiaoql.domain.OrderRepository;
import com.xiaoql.domain.Shop;
import com.xiaoql.domain.ShopOrder;
import com.xiaoql.domain.ShopRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/meituan")
public class MeituanController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopRepository shopRepository;


    @GetMapping({"/shops"})
    public List<Shop> shops() {
        return shopRepository.findAll();
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


    @GetMapping({"/orders"})
    public List<ShopOrder> orders() {
        return orderRepository.findAll();
    }

    @PutMapping({"/orders/{id}"})
    public void orders(@PathVariable String id, @RequestBody Map<String, Object> toOrder, HttpServletResponse response) {
        ShopOrder shopOrder = orderRepository.getOne(id);
        if (shopOrder == null) {
            response.setStatus(HttpStatus.NOT_FOUND.value());
            return;
        }
        shopOrder.update(toOrder);
        orderRepository.save(shopOrder);
    }



}
