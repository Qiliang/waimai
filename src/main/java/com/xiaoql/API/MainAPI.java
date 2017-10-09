package com.xiaoql.API;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoql.entity.*;
import com.xiaoql.mapper.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class MainAPI {

    @Autowired
    AppVersionMapper appVersionMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    RiderMapper riderMapper;

    @Autowired
    private ShopOrderMapper shopOrderMapper;

    @Autowired
    private ShopOrderExMapper shopOrderExMapper;

    @Autowired
    private ShopMapper shopMapper;

    @PostMapping("/login")
    public Object login(@RequestParam String username, @RequestParam String password, HttpServletResponse response) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andLoginNameEqualTo(username).andLoginPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() > 0) {
            return users.get(0).getId();
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "用户名或密码不正确");
        }
    }


    //////////////////////////////////////统计相关/////////////////////////////////////////////////////////////////////


    @GetMapping("/stat")
    public Object stat() {
        Map<String, Object> result = new HashMap<>();
        result.put("orderCount", shopOrderMapper.countByExample(new ShopOrderExample()));
        result.put("riderCount", riderMapper.countByExample(new RiderExample()));
        result.put("shopCount", shopMapper.countByExample(new ShopExample()));

        long orderDqcCount = shopOrderMapper.countByExample(new ShopOrderExample() {{
            createCriteria().andStatusEqualTo(Status.OrderRiderAssgin);
        }});
        long orderPszCount = shopOrderMapper.countByExample(new ShopOrderExample() {{
            createCriteria().andStatusEqualTo(Status.OrderRiderGotGoods);
        }});
        long orderWcCount = shopOrderMapper.countByExample(new ShopOrderExample() {{
            createCriteria().andStatusEqualTo(Status.OrderCompleted);
        }});
        long orderYcCount = shopOrderMapper.countByExample(new ShopOrderExample() {{
            createCriteria().andStatusEqualTo(Status.OrderCanceled);
        }});

        result.put("orderDqcCount", orderDqcCount);
        result.put("orderPszCount", orderPszCount);
        result.put("orderWcCount", orderWcCount);
        result.put("orderYcCount", orderYcCount);

        Double orderCoast = shopOrderExMapper.avgCoast();
        result.put("orderCoast", orderCoast == null ? 0 : orderCoast / 60);
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
        List<Map<String, Object>> result = shopOrderExMapper.statDailyOrders(day);
        //List<Object[]> result = em.createNativeQuery("select HOUR(time) hour,count(*) orders from shop_order where time>'" + day + "' group by hour").getResultList();

        List<Map<String, Object>> out = new ArrayList<>();
        for (int i = 0; i < 24; i++) {
            final int huor = i;
            Map<String, Object> huorOrders = new HashMap<>();
            huorOrders.put("hour", i);
            out.add(huorOrders);
            Optional<Map<String, Object>> line = result.stream().filter(row -> ((int) row.get("hour") == huor)).findFirst();
            if (line.isPresent()) {
                huorOrders.put("orders", line.get().get("orders"));
            } else {
                huorOrders.put("orders", 0);
            }
        }
        return out;

    }

    @PostMapping("/stat/days")
    public Object statDay() throws ParseException {
        List list = shopOrderExMapper.statByDay();
        Collections.reverse(list);
        return list;
    }

    @PostMapping("/stat/days/riders/{riderId}")
    public Object statRiderDay(@PathVariable String riderId, @RequestParam String from, @RequestParam String to, @RequestParam int status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        return shopOrderExMapper.statRiderByDay(from, to, riderId, status);
    }

    @PostMapping("/stat/riders")
    public Object statRiders(@RequestParam String from, @RequestParam String to, @RequestParam int status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        return shopOrderExMapper.statRiders(from, to, status);
    }

    @PostMapping("/rider/stat")
    public Object statRider(@RequestParam String from, @RequestParam String to, @RequestParam String token, @RequestParam int status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        return shopOrderExMapper.statRider(from, to, token, status).get(0);
    }

    @PostMapping("/rider/stat/details")
    public Object statRiderDetails(@RequestParam String from, @RequestParam String to, @RequestParam String token, @RequestParam int status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        return shopOrderExMapper.statRiderDetail(from, to, token, status);
    }

    @PostMapping("/stat/shops")
    public Object statShop(@RequestParam String from, @RequestParam String to, @RequestParam int status) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        to = sdf.format(DateUtils.addDays(sdf.parse(to), 1));
        return shopOrderExMapper.statByShop(from, to, status);
    }


    //////////////////////////////////////用户相关/////////////////////////////////////////////////////////////////////


    @GetMapping("/users")
    public List<User> users() {
        UserExample example = new UserExample();
        return userMapper.selectByExample(example);
    }

    @PutMapping("/users/{id}")
    public void users(@PathVariable String id, @RequestBody Map<String, Object> toUser, HttpServletResponse response) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(toUser.get("loginName").toString());
        userMapper.selectByExample(example);
        if (toUser.get("loginName") != null
                && StringUtils.isNotBlank(toUser.get("loginName").toString())
                && userMapper.selectByExample(example).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return;
        }

        User user = userMapper.selectByPrimaryKey(id);
        Utils.updateBean(user, toUser);
        userMapper.updateByPrimaryKey(user);
    }

    @PostMapping("/users")
    public Object users(@RequestBody User toUser, HttpServletResponse response) {
        UserExample example = new UserExample();
        example.createCriteria().andLoginNameEqualTo(toUser.getLoginName());
        if (userMapper.selectByExample(example).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "登录名重复");
        }
        userMapper.insertSelective(toUser);
        return new RestResponse();
    }

    @DeleteMapping("/users/{id}")
    public Object usersDel(@PathVariable String id, HttpServletResponse response) {
        userMapper.deleteByPrimaryKey(id);
        return new RestResponse();
    }


    //////////////////////////////////////店铺相关/////////////////////////////////////////////////////////////////////


    @GetMapping({"/shops"})
    public List<Shop> shops() {
        return shopMapper.selectByExample(new ShopExample());
    }

    @PutMapping("/shops/{id}")
    public void shops(@PathVariable String id, @RequestBody Map<String, Object> toShop, HttpServletResponse response) {
        Shop shop = shopMapper.selectByPrimaryKey(id);
        Utils.updateBean(shop, toShop);
        shopMapper.updateByPrimaryKeySelective(shop);
    }

    @PostMapping("/shops")
    public Object shops(@RequestBody Shop toShop, HttpServletResponse response) {
        if (shopMapper.selectByExample(new ShopExample() {{
            createCriteria().andNameEqualTo(toShop.getName());
        }}).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "店铺名重复");
        }
        shopMapper.insertSelective(toShop);
        return new RestResponse();
    }

    @DeleteMapping("/shops/{id}")
    public Object shopsDel(@PathVariable String id, HttpServletResponse response) {
        shopMapper.deleteByPrimaryKey(id);
        return new RestResponse();
    }

    //////////////////////////////////////骑手相关/////////////////////////////////////////////////////////////////////


    @GetMapping("/riders")
    public List<Rider> riders(Integer status) {
        RiderExample example = new RiderExample();
        if (status == null)
            return riderMapper.selectByExample(example);
        else {
            example.createCriteria().andStatusEqualTo(status);
            List<Rider> riders = riderMapper.selectByExample(example);
            riders.forEach(rider -> {
                ShopOrderExample shopOrderExample = new ShopOrderExample();
                shopOrderExample.createCriteria().andRiderIdEqualTo(rider.getId()).andStatusBetween(11, 12);
                rider.setOrderCount((int) shopOrderMapper.countByExample(shopOrderExample));
            });
            return riders;
        }
    }

    @PutMapping("/riders/{id}")
    public void riders(@PathVariable String id, @RequestBody Map<String, Object> modifier, HttpServletResponse response) {
        Rider rider = riderMapper.selectByPrimaryKey(id);
        Utils.updateBean(rider, modifier);
        riderMapper.updateByPrimaryKey(rider);
    }


    @PostMapping("/riders")
    public Object riders(@RequestBody Rider toCreator, HttpServletResponse response) {
        RiderExample example = new RiderExample();
        example.createCriteria().andLoginNameEqualTo(toCreator.getLoginName());
        if (riderMapper.selectByExample(example).size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            return new RestResponse(true, "登录名重复");
        }
        riderMapper.insertSelective(toCreator);
        return new RestResponse();
    }

    @DeleteMapping("/riders/{id}")
    public Object ridersDel(@PathVariable String id, HttpServletResponse response) {
        riderMapper.deleteByPrimaryKey(id);
        return new RestResponse();
    }

    //app升级
    @GetMapping({"/checkupdate"})
    @ResponseBody
    public Object update() {
        AppVersionExample example = new AppVersionExample();
        example.createCriteria().andStatusEqualTo(1);
        List<AppVersion> appVersions = appVersionMapper.selectByExample(example);
        if (appVersions.size() > 0) return appVersions.get(0);
        return new RestResponse(true, "没有可用的更新");
    }

    /**
     * 新订单提醒
     */
    @PostMapping("/orders/alert")
    public Object alert(@RequestParam String time) throws ParseException {

        ShopOrderExample example = new ShopOrderExample();
        example.createCriteria().andTimeGreaterThan(Utils.toDate(time)).andStatusEqualTo(Status.OrderConfirm);
        PageHelper.startPage(1, 1, false);
        List list = shopOrderMapper.selectByExample(example);
        Map<String, Object> map = new HashMap<>();
        map.put("alert", list.size() > 0);
        return map;
    }


//////////////////////////////////////订单相关/////////////////////////////////////////////////////////////////////

    private final Map<String, List<String>> riderMap = new ConcurrentHashMap<>();

//    @GetMapping({"/orders"})
//    public PageInfo<ShopOrder> orders(Integer status, Integer page, Integer size, String sort) {
//        PageHelper.startPage(page, size);
//        PageHelper.orderBy(sort.replace(",", " "));
//        return new PageInfo(shopOrderMapper.selectByExample(new ShopOrderExample() {{
//            createCriteria().andStatusEqualTo(status);
//        }}));
//    }

    @Scheduled(initialDelay = 3000, fixedDelay = 1000 * 10)
    public void getRider() {
        List<Rider> riders = riderMapper.selectByExample(new RiderExample());
        Map<String, List<String>> riderMap = riders.stream().filter(r -> StringUtils.isNotBlank(r.getShopId()))
                .collect(Collectors.groupingBy(Rider::getShopId, Collectors.mapping(Rider::getId, Collectors.toList())));
        riderMap.put("others", riders.stream().filter(r -> StringUtils.isBlank(r.getShopId())).map(Rider::getId).collect(Collectors.toList()));
        this.riderMap.putAll(riderMap);
    }


    @GetMapping(value = {"/orders"})
    public PageInfo<ShopOrder> orders(@RequestParam(required = false) Integer status) {
        if (Status.OrderConfirm == status) {
            PageHelper.startPage(1, 25);
            List<ShopOrder> orders = shopOrderMapper.selectByExample(new ShopOrderExample() {{
                setOrderByClause("time desc");
                createCriteria().andStatusEqualTo(status);
            }});

            orders.forEach(order -> {
                if (this.riderMap.containsKey(order.getShopName())) {
                    order.setRiders(this.riderMap.get(order.getShopId()));
                } else {
                    order.setRiders(this.riderMap.get("others"));
                }
            });

            return new PageInfo(orders);

        } else if (status == null) {
            PageHelper.startPage(1, 25);
            return new PageInfo(shopOrderMapper.selectByExample(new ShopOrderExample() {{
                setOrderByClause("time desc");
            }}));
        } else {
            PageHelper.startPage(1, 25);
            return new PageInfo(shopOrderMapper.selectByExample(new ShopOrderExample() {{
                setOrderByClause("time desc");
                createCriteria().andStatusEqualTo(status);
            }}));

        }
    }

//    @PutMapping({"/orders/{id}"})
//    public void orders(@PathVariable String id, @RequestBody Map<String, Object> toOrder, HttpServletResponse response) {
//        ShopOrder shopOrder = shopOrderMapper.selectByPrimaryKey(id);
//        if (shopOrder == null) {
//            response.setStatus(HttpStatus.NOT_FOUND.value());
//            return;
//        }
//
//        shopOrder.update(toOrder);
//        shopOrderRepository.save(shopOrder);
//    }

    @PostMapping({"/orders/{ids}/asign/{riderId}"})
    public void orderAsignRider(@PathVariable String ids, @PathVariable String riderId, HttpServletResponse response) {
        for (String id : ids.split(",")) {
            Rider rider = riderMapper.selectByPrimaryKey(riderId);
            shopOrderMapper.updateByPrimaryKeySelective(new ShopOrder() {{
                setId(id);
                setStatus(Status.OrderRiderAssgin);
                setRiderAssignTime(new Date());
                setRiderId(riderId);
                setRiderName(rider.getName());
                setRiderPhone(rider.getPhone());
            }});
        }
    }

    @PostMapping({"/orders/{id}/reasign/{riderDisplayName}"})
    public void orderReAsignRider(@PathVariable String id, @PathVariable String riderDisplayName, HttpServletResponse response) {
        String[] parts = riderDisplayName.split(":");

        List<Rider> riders = riderMapper.selectByExample(new RiderExample() {{
            createCriteria().andPhoneEqualTo(parts[0]);
        }});
        if (riders.size() == 0) return;
        Rider rider = riders.get(0);
        shopOrderMapper.updateByPrimaryKeySelective(new ShopOrder() {{
            setId(id);
            setStatus(Status.OrderRiderAssgin);
            setRiderAssignTime(new Date());
            setRiderId(rider.getId());
            setRiderName(rider.getName());
            setRiderPhone(rider.getPhone());
        }});
    }

}
