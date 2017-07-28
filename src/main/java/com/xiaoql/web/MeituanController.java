package com.xiaoql.web;

import com.xiaoql.domain.OrderRepository;
import com.xiaoql.domain.Shop;
import com.xiaoql.domain.ShopOrder;
import com.xiaoql.domain.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @GetMapping({"/orders"})
    public List<ShopOrder> orders() {
        return orderRepository.findAll();
    }

}
