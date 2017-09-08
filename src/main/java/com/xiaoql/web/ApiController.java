package com.xiaoql.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.xiaoql.domain.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.cfg.NamingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private EntityManager em;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private RiderRepository riderRepository;

    private ObjectMapper objectMapper=new ObjectMapper();

    public ApiController() {
        objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
    }

    @GetMapping("/stat")
    public Object stat() {
        Map<String, Object> result = new HashMap<>();
        result.put("orderCount", shopOrderRepository.count());
        result.put("riderCount", riderRepository.count());
        result.put("shopCount", shopRepository.count());

        long orderDqcCount = shopOrderRepository.count((root, query, builder) -> builder.equal(root.get("riderState"), "dqc"));
        long orderPszCount = shopOrderRepository.count((root, query, builder) -> builder.equal(root.get("riderState"), "psz"));
        long orderWcCount = shopOrderRepository.count((root, query, builder) -> builder.equal(root.get("riderState"), "wc"));
        long orderYcCount = shopOrderRepository.count((root, query, builder) -> builder.equal(root.get("riderState"), "yc"));
        result.put("orderDqcCount", orderDqcCount);
        result.put("orderPszCount", orderPszCount);
        result.put("orderWcCount", orderWcCount);
        result.put("orderYcCount", orderYcCount);
        Double orderCoast = (Double) em.createQuery("SELECT avg(riderCoast)FROM ShopOrder").getSingleResult();
        result.put("orderCoast", (int) (orderCoast / 1000 / 60));
        return result;
    }

    @PostMapping("/stat/days")
    public Object statDay() throws ParseException {
        return  jdbcTemplate.queryForList("select DATE_FORMAT(time,'%Y-%m-%d') as d,count(*) count from shop_order group by d ORDER BY d desc LIMIT 0,30");
    }

    @PostMapping("/stat/riders")
    public Object statRiders(@RequestParam String from, @RequestParam String to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));

        List<Object[]> result = em.createNativeQuery("select rider_id,num,price,name,phone " +
                "from (select rider_id,count(id) num,sum(total_after) price from shop_order where time>'" + from + "' and time<'" + to + "' group by rider_id) a INNER JOIN rider b on a.rider_id=b.id").getResultList();

        return result;
    }

    @PostMapping("/stat/rider")
    public Object statRider(@RequestParam String from, @RequestParam String to, @RequestParam String token, @RequestParam String state) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));

        Map<String, Object> result =jdbcTemplate.queryForMap("select count(id) num,sum(total_after) price from shop_order where time>'" + from + "' and time<'" + to + "' and rider_id='" + token + "' and rider_state='" + state + "'");

        return result;
    }

    @PostMapping("/stat/rider/details")
    public Object statRiderDetails(@RequestParam String from, @RequestParam String to, @RequestParam String token, @RequestParam String state) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        List<Map<String, Object>> result = jdbcTemplate.queryForList("select id,rider_id riderId,shop_name shopName,shop_address shopAddress,user_name userName,user_address userAddress,time,rider_assign_time riderAssignTime,rider_get_goods_time riderGetGoodsTime,rider_to_user_time riderToUserTime from shop_order where time>'" + from + "' and time<'" + to + "' and rider_id='" + token + "' and rider_state='" + state + "'");
        return result;
    }

    @PostMapping("/stat/shops")
    public Object statShop(@RequestParam String from, @RequestParam String to) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        List<Object[]> result = em.createNativeQuery("select shop_name,count(shop_name) num,sum(total_after) price from shop_order where time>'" + from + "' and time<'" + to + "' group by shop_name").getResultList();

        return result;
    }

    @GetMapping({"/dailyOrders/stat"})
    public Object dailyOrders() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        Date d = new Date();

        String day = today.get(Calendar.YEAR) + "-" + (today.get(Calendar.MONTH) + 1) + "-" + today.get(Calendar.DAY_OF_MONTH);
        List<Object[]> result = em.createNativeQuery("select HOUR(time) hour,count(*) orders from shop_order where time>'" + day + "' group by hour").getResultList();

        List<Map<String, Object>> out = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            final int huor = i;
            Map<String, Object> huorOrders = new HashMap<>();
            huorOrders.put("hour", i);
            out.add(huorOrders);
            Optional<Object[]> line = result.stream().filter(row -> ((int) row[0]) == huor).findFirst();
            if (line.isPresent()) {
                huorOrders.put("orders", line.get()[1]);
            } else {
                huorOrders.put("orders", 0);
            }
        }
        return out;

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
