package com.xiaoql.web;

import com.xiaoql.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private RiderRepository riderRepository;

    @GetMapping("/stat")
    public Object stat() {
        return shopOrderRepository;
    }


    @GetMapping("/users")
    public List<User> users() {
        return userRepository.findAll();
    }

    @PutMapping("/users/{id}")
    public void users(@PathVariable String id, @RequestBody Map<String, Object> toUser, HttpServletResponse response) {
        if (toUser.get("loginName") != null
                && StringUtils.isNotBlank(toUser.get("loginName").toString())
                && userRepository.findByLoginName(toUser.get("loginName").toString()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        User user = userRepository.getOne(id);
        user.update(toUser);
        userRepository.save(user);
    }

    @PostMapping("/users")
    public Object users(@RequestBody User toUser, HttpServletResponse response) {
        if (userRepository.findByLoginName(toUser.getLoginName()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "登录名重复");
        }
        userRepository.save(toUser);
        return new RestResponse();
    }

    @DeleteMapping("/users/{id}")
    public Object usersDel(@PathVariable String id, HttpServletResponse response) {
        userRepository.delete(id);
        return new RestResponse();
    }


    @GetMapping("/riders")
    public List<Rider> riders(@RequestParam(required = false) Boolean active) {
        if (active == null)
        return riderRepository.findAll();
        else {
            return riderRepository.findByActive(active);
        }
    }

    @PutMapping("/riders/{id}")
    public void riders(@PathVariable String id, @RequestBody Map<String, Object> modifier, HttpServletResponse response) {
        if (modifier.get("loginName") != null
                && StringUtils.isNotBlank(modifier.get("loginName").toString())
                && riderRepository.findByLoginName(modifier.get("loginName").toString()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }
        Rider rider = riderRepository.getOne(id);
        rider.update(modifier);
        riderRepository.save(rider);
    }

    @GetMapping("/riders/{id}/orders/count")
    public long riders(@PathVariable String id, HttpServletResponse response) {
        long count = shopOrderRepository.count((root, query, builder) -> builder.equal(root.get("riderId"), id));
        return count;
    }

    @PostMapping("/riders")
    public Object riders(@RequestBody Rider toCreator, HttpServletResponse response) {
        if (riderRepository.findByLoginName(toCreator.getLoginName()).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "登录名重复");
        }
        riderRepository.save(toCreator);
        return new RestResponse();
    }

    @DeleteMapping("/riders/{id}")
    public Object ridersDel(@PathVariable String id, HttpServletResponse response) {
        riderRepository.delete(id);
        return new RestResponse();
    }

}
