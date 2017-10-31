package com.xiaoql.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoql.entity.Shop;
import com.xiaoql.entity.ShopExample;
import com.xiaoql.entity.ShopOrder;
import com.xiaoql.mapper.ShopMapper;
import com.xiaoql.mapper.ShopOrderMapper;
import okhttp3.*;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/mt")
public class MeituanAPI {

    @Value("${env.production}")
    private boolean production;

    @Value("${env.developerId}")
    private String developerId;

    @Value("${env.SignKey}")
    private String signKey;

    private final String OK = "{\"data\": \"OK\"}";
    private final String Failed = "{\"data\": \"failed\"}";

    @Autowired
    private ShopMapper shopMapper;
    @Autowired
    private ShopOrderMapper shopOrderMapper;
    @Autowired
    private ObjectMapper objectMapper;

    //云端心跳回调URL
    @RequestMapping("/live")
    public String live(HttpServletRequest request) throws IOException {
        System.out.println("live");
        
        return OK;
    }

    //推送订单URL
    @RequestMapping("/order")
    public String order(String ePoiId, String order, HttpServletRequest request) throws IOException {
        System.out.println("order");
        ValueMap valueMap = ValueMap.wrap(objectMapper.readValue(order, Map.class));

        Shop shop = shopMapper.selectByPrimaryKey(ePoiId);
        if (StringUtils.isBlank(shop.getLng())) return Failed;
        ShopOrder shopOrder = new ShopOrder();
        shopOrder.setId(valueMap.valString("orderId"));
        Date orderTime = new Date();
        System.out.println(valueMap.get("ctime"));
        orderTime.setTime((Integer) valueMap.get("ctime") * 1000L);
        shopOrder.setShopId(valueMap.valString("ePoiId"));
        shopOrder.setTime(orderTime);
        shopOrder.setShopName(valueMap.valString("poiName"));
        shopOrder.setRecipientName(valueMap.valString("recipientName"));
        shopOrder.setStatus((Integer) valueMap.get("status"));
        shopOrder.setRecipientPhone(valueMap.valString("recipientPhone"));
        shopOrder.setRecipientAddress(valueMap.valString("recipientAddress"));
        shopOrder.setRecipientLng(StringUtils.rightPad(valueMap.get("longitude").toString().replace(".", ""), 9, '0'));
        shopOrder.setRecipientLat(StringUtils.rightPad(valueMap.get("latitude").toString().replace(".", ""), 8, '0'));

        shopOrder.setShopLng(shop.getLng());
        shopOrder.setShopLat(shop.getLat());
        shopOrder.setTotal(Double.parseDouble(valueMap.get("total").toString()));
        shopOrder.setShopPhone(valueMap.valString("poiPhone"));
        shopOrder.setShopAddress(valueMap.valString("poiAddress"));
        shopOrder.setShippingFee(shop.getShippingFee());
        shopOrder.setDaySeq(valueMap.valString("daySeq"));
        shopOrderMapper.insertSelective(shopOrder);
        return OK;
        //String s = "{\"avgSendTime\":2400.0,\"caution\":\"\",\"cityId\":420500,\"ctime\":1506586628,\"daySeq\":\"21\",\"deliveryTime\":0,\"detail\":\"[{\\\"app_food_code\\\":\\\"?????+?????\\\",\\\"box_num\\\":1,\\\"box_price\\\":0,\\\"cart_id\\\":0,\\\"food_discount\\\":1,\\\"food_name\\\":\\\"?????+?????\\\",\\\"food_property\\\":\\\"??,?\\\",\\\"price\\\":20,\\\"quantity\\\":1,\\\"sku_id\\\":\\\"\\\",\\\"spec\\\":\\\"\\\",\\\"unit\\\":\\\"?\\\"}]\",\"dinnersNumber\":0,\"ePoiId\":\"bc52f284-ea25-4556-bd36-43d2a5b1219b\",\"extras\":\"[{}]\",\"hasInvoiced\":0,\"invoiceTitle\":\"\",\"isFavorites\":false,\"isPoiFirstOrder\":false,\"isThirdShipping\":0,\"latitude\":30.701898,\"logisticsCode\":\"2002\",\"longitude\":111.279216,\"orderId\":6292920071,\"orderIdView\":24396521203884554,\"originalPrice\":22.0,\"payType\":2,\"poiAddress\":\"??????26?\",\"poiFirstOrder\":false,\"poiId\":2439652,\"poiName\":\"?????????????\",\"poiPhone\":\"17371724087\",\"poiReceiveDetail\":\"{\\\"actOrderChargeByMt\\\":[],\\\"actOrderChargeByMtIterator\\\":{},\\\"actOrderChargeByMtSize\\\":0,\\\"actOrderChargeByPoi\\\":[],\\\"actOrderChargeByPoiIterator\\\":{\\\"$ref\\\":\\\"$.actOrderChargeByMtIterator\\\"},\\\"actOrderChargeByPoiSize\\\":0,\\\"foodShareFeeChargeByPoi\\\":300,\\\"logisticsFee\\\":200,\\\"onlinePayment\\\":2200,\\\"setActOrderChargeByMt\\\":true,\\\"setActOrderChargeByPoi\\\":true,\\\"setFoodShareFeeChargeByPoi\\\":true,\\\"setLogisticsFee\\\":true,\\\"setOnlinePayment\\\":true,\\\"setWmPoiReceiveCent\\\":true,\\\"wmPoiReceiveCent\\\":1700}\",\"recipientAddress\":\"?????? (B?1203?)@#?????????????????8???????\",\"recipientName\":\"??(??)\",\"recipientPhone\":\"15549319591\",\"shipperPhone\":\"\",\"shippingFee\":2.0,\"status\":2,\"taxpayerId\":\"\",\"total\":22.0,\"utime\":1506586628}";
//        if (orderConfirm(shop, shopOrder.getId())) {
//            return OK;
//        }
//        return Failed;
    }

    //商家确认接单
    private boolean orderConfirm(Shop shop, String orderId) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("appAuthToken", shop.getMeituanToken());
        params.put("charset", "UTF-8");
        params.put("orderId", Long.valueOf(orderId));
        return post("http://api.open.cater.meituan.com/waimai/order/confirm", params);
    }

    /**
     * developerId:100120
     * ePoiId:9384019
     * sign:52b379754c40c7865a48b84a24fb99c1ebb49f11
     * orderCancel:{
     * "orderId":12341234,
     * "reason":"超时取消",
     * "reasonCode":"1002"
     * }
     */
    //美团用户或客服取消URL
    @RequestMapping("/order_cancel")
    public String order_cancel(String ePoiId, String orderCancel, HttpServletRequest request) throws IOException {
        System.out.println("order_cancel");
        
        Map<String, Object> orderCancelMap = objectMapper.readValue(orderCancel, Map.class);
        String orderId = orderCancelMap.get("orderId").toString();
        int res = shopOrderMapper.updateByPrimaryKeySelective(new ShopOrder() {{
            setId(orderId);
            setStatus(Status.OrderCanceled);
        }});
        if (res > 0) return OK;
        return Failed;
    }

    //美团用户或客服退款流程URL
    @RequestMapping("/order_tui")
    public String order_tui(HttpServletRequest request) throws IOException {
        System.out.println("order_tui");
        
        return OK;
    }

    //已完成订单推送回调URL
    @RequestMapping("/order_callback")
    public String order_callback(String ePoiId, String order,HttpServletRequest request) throws IOException {
        System.out.println("order_callback");
        Map<String, Object> valueMap = objectMapper.readValue(order, Map.class);
        shopOrderMapper.updateByPrimaryKeySelective(new ShopOrder() {{
            setId(valueMap.get("orderId").toString());
            setStatus((Integer) valueMap.get("status"));
        }});
        return OK;
    }

    //订单已确认的回调URL
    @RequestMapping("/order_ok")
    public String order_ok(String ePoiId, String order, HttpServletRequest request) throws IOException {
        System.out.println("order_ok");
        Map<String, Object> valueMap = objectMapper.readValue(order, Map.class);
        shopOrderMapper.updateByPrimaryKeySelective(new ShopOrder() {{
            setId(valueMap.get("orderId").toString());
            setStatus((Integer) valueMap.get("status"));
        }});
        return OK;
    }

    //订单配送状态的回调URL
    @RequestMapping("/order_state")
    public String order_state(String ePoiId, String shippingStatus, HttpServletRequest request) throws IOException {
        //Map<String, Object> valueMap = objectMapper.readValue(shippingStatus, Map.class);
        System.out.println("order_state");
        
        return OK;
    }

    //门店更改的回调URL
    @RequestMapping("/shop_change")
    public String shop_change(HttpServletRequest request) throws IOException {
        System.out.println("shop_change");
        
        return OK;
    }

    //外卖订单对账信息回调URL
    @RequestMapping("/order_view")
    public String order_view(HttpServletRequest request) throws IOException {
        System.out.println("order_view");
        
        return OK;
    }

    //买单推送URL
    @RequestMapping("/order_mai")
    public String order_mai(HttpServletRequest request) throws IOException {
        System.out.println("order_mai");
        
        return OK;
    }

    //订单校验和下单URL
    @RequestMapping("/order_jiao")
    public String order_jiao(HttpServletRequest request) throws IOException {
        System.out.println("order_jiao");
        
        return OK;
    }

    //点餐推送消息接口URL
    @RequestMapping("/order_dian")
    public String order_dian(HttpServletRequest request) throws IOException {
        System.out.println("order_dian");
        
        return OK;
    }


    //门店映射回调地址
    @RequestMapping("/shop_binding")
    public String shop_binding(String appAuthToken, String businessId, String ePoiId, String timestamp, HttpServletRequest request) {
        System.out.println("shop_binding");
        int res = shopMapper.updateByPrimaryKeySelective(new Shop() {{
            setId(ePoiId);
            setMeituanToken(appAuthToken);
        }});
        if (res > 0) return OK;
        return Failed;
    }

    //门店映射解绑回调地址
    @RequestMapping("/shop_unbinding")
    public String shop_unbinding(String developerId, String epoiId, String businessId, String ePoiId, String timestamp, HttpServletRequest request) {
        System.out.println("shop_unbinding");
        int res = shopMapper.updateByPrimaryKeySelective(new Shop() {{
            setId(ePoiId);
            setMeituanToken("");
        }});
        if (res > 0) return OK;
        return Failed;
    }


    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 60 * 1000)
    public void shop() throws IOException {

        shopMapper.selectByExample(new ShopExample()).forEach(shop -> {
            if (StringUtils.isBlank(shop.getMeituanToken())) return;
            try {
                Map<String, Object> params = new LinkedHashMap<>();
                params.put("appAuthToken", shop.getMeituanToken());
                params.put("charset", "UTF-8");
                params.put("ePoiIds", shop.getId());
                params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
                String s = String.join("", params.keySet().stream().map(key -> key + params.get(key)).collect(Collectors.toList()));
                String sign = Hex.encodeHexString(DigestUtils.getSha1Digest().digest((signKey + s).getBytes("utf-8"))).toLowerCase();
                params.put("sign", sign);
                String values = String.join("&", params.keySet().stream().map(key -> key + "=" + params.get(key)).collect(Collectors.toList()));
                OkHttpClient client = new OkHttpClient();
                //RequestBody body = RequestBody.create(form, values);
                Request request = new Request.Builder()
                        .url("http://api.open.cater.meituan.com/waimai/poi/queryPoiInfo?" + values)
                        .get()
                        .build();
                Response response = client.newCall(request).execute();
                //System.out.println(response.body().string());
                ValueMap valueMap = ValueMap.wrap((Map) ((List) objectMapper.readValue(response.body().string(), Map.class).get("data")).get(0));
                shop.setName(valueMap.valString("name"));
                shop.setPhone(valueMap.valString("phone"));
                shop.setLat(valueMap.valString("latitude"));
                shop.setLng(valueMap.valString("longitude"));
                shop.setAddress(valueMap.valString("address"));
                shopMapper.updateByPrimaryKey(shop);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType form = MediaType.parse("application/x-www-form-urlencoded");

    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 20 * 1000)
    public void live() throws IOException {
        if (!production) return;

        Map<String, Object> data = new LinkedHashMap<>();
        data.put("developerId", developerId);
        data.put("time", new Date().getTime());
        data.put("list", shopMapper.selectByExample(new ShopExample()).stream().map(shop ->
                new HashMap<String, Object>() {{
                    put("ePoiId", shop.getId());
                    put("posId", "1");
                }}
        ).collect(Collectors.toList()));
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        String json = objectMapper.writeValueAsString(data);
        params.put("data", json);
        post("http://heartbeat.meituan.com/pos/heartbeat", params, false);
    }


    //自配送－配送状态
    private boolean orderDelivering(Shop shop, String orderId, String courierName, String courierPhone) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("appAuthToken", shop.getMeituanToken());
        params.put("charset", "UTF-8");
        params.put("orderId", Long.valueOf(orderId));
        params.put("courierName", courierName);
        params.put("courierPhone", courierPhone);
        return post("http://api.open.cater.meituan.com/waimai/order/delivering", params);
    }

    //自配送场景－订单已送达
    private boolean orderDelivered(Shop shop, String orderId) {
        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
        params.put("appAuthToken", shop.getMeituanToken());
        params.put("charset", "UTF-8");
        params.put("orderId", Long.valueOf(orderId));
        return post("http://api.open.cater.meituan.com/waimai/order/delivered", params);
    }


    public boolean post(String url, final LinkedHashMap<String, Object> params) {
        try {
            String responseText = post(url, params, true);
            if (StringUtils.isBlank(responseText)) return false;
            Map<String, Object> res = objectMapper.readValue(responseText, Map.class);
            return "OK".equals(res.get("data"));
        } catch (Exception e) {
            return false;
        }
    }

    public String post(String url, final LinkedHashMap<String, Object> params, boolean time) {
        try {
            if (time) params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            String s = String.join("", params.keySet().stream().map(key -> key + params.get(key)).collect(Collectors.toList()));
            String sign = Hex.encodeHexString(DigestUtils.getSha1Digest().digest((signKey + s).getBytes("utf-8"))).toLowerCase();
            params.put("sign", sign);
            String values = String.join("&", params.keySet().stream().map(key -> key + "=" + params.get(key)).collect(Collectors.toList()));
            OkHttpClient client = new OkHttpClient();
            RequestBody body = RequestBody.create(form, values);
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean get(String url, final LinkedHashMap<String, Object> params) {
        try {
            params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
            String s = String.join("", params.keySet().stream().map(key -> key + params.get(key)).collect(Collectors.toList()));
            String sign = Hex.encodeHexString(DigestUtils.getSha1Digest().digest((signKey + s).getBytes("utf-8"))).toLowerCase();
            params.put("sign", sign);
            String values = String.join("&", params.keySet().stream().map(key -> key + "=" + params.get(key)).collect(Collectors.toList()));
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(url + "?" + values)
                    .get()
                    .build();
            Response response = client.newCall(request).execute();
            Map<String, Object> res = objectMapper.readValue(response.body().string(), Map.class);
            return "OK".equals(res.get("data"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws IOException {
//        String[] ids = TimeZone.getAvailableIDs();
//        for (String id : ids)
//            System.out.println(id);
        int d = 1507528467;
        //TimeZone tz = TimeZone.getTimeZone("Etc/GMT+0");
        TimeZone tz = TimeZone.getDefault();
        System.out.println(tz.getRawOffset());
        System.out.println((d * 1000L));
        System.out.println(tz.getOffset(1507528467));
//Etc/GMT+0
        Date orderTime = new Date();
        orderTime.setTime(d * 1000);

        System.out.println(orderTime);
        orderTime.setTime(d * 1000L);
        System.out.println(orderTime);
    }
}
