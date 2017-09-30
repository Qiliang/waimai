//package com.xiaoql.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.xiaoql.domain.Shop;
//import com.xiaoql.domain.ShopOrder;
//import com.xiaoql.domain.ShopOrderRepository;
//import com.xiaoql.domain.ShopRepository;
//import okhttp3.*;
//import org.apache.commons.codec.binary.Hex;
//import org.apache.commons.codec.digest.DigestUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestController
//@RequestMapping("/mt")
//public class MeituanAPI {
//
//    @Value("${env.production}")
//    private boolean production;
//
//    @Value("${env.developerId}")
//    private String developerId;
//
//    @Value("${env.SignKey}")
//    private String signKey;
//
//    @Autowired
//    private ShopRepository shopRepository;
//    @Autowired
//    private ShopOrderRepository shopOrderRepository;
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    //云端心跳回调URL
//    @RequestMapping("/live")
//    public String live(HttpServletRequest request) throws IOException {
//        System.out.println("live");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return " {\"data\":\"success\"} ";
//    }
//
//    //推送订单URL
//    @RequestMapping("/order")
//    public String order(String ePoiId, String order, HttpServletRequest request) throws IOException {
//        System.out.println("order");
//        ValueMap valueMap = ValueMap.wrap(objectMapper.readValue(order, Map.class));
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        Shop shop = shopRepository.getOne(ePoiId);
//
//        ShopOrder shopOrder = new ShopOrder();
//        shopOrder.setId(valueMap.valString("orderId"));
//        shopOrder.setMeituanViewId(valueMap.valString("orderIdView"));
//        Date orderTime = new Date();
//        orderTime.setTime(Long.valueOf((Integer) valueMap.get("ctime")) * 1000);
//        shopOrder.setShopId(valueMap.valString("ePoiId"));
//        shopOrder.setTime(orderTime);
//        //shopOrder.setDescription(body.get("orderCopyContent").toString());
//        shopOrder.setShopName(valueMap.valString("poiName"));
//        shopOrder.setUserName(valueMap.valString("recipientName"));
//        shopOrder.setState(valueMap.get("status").toString());
//        shopOrder.setUserPhone(valueMap.valString("recipientPhone"));
//        shopOrder.setUserAddress(valueMap.valString("recipientAddress"));
//        shopOrder.setOrderLng(valueMap.get("longitude").toString().replace(".", ""));
//        shopOrder.setOrderLat(valueMap.get("latitude").toString().replace(".", ""));
//        shopOrder.setShopLng(shop.getLongitude());
//        shopOrder.setShopLat(shop.getLatitude());
//        shopOrder.setTotalAfter(Double.parseDouble(valueMap.get("total").toString()));
//        shopOrder.setRaw(order);
//        shopOrder.setShopPhone(valueMap.valString("poiPhone"));
//        shopOrder.setShopAddress(valueMap.valString("poiAddress"));
//        shopOrder.setMpt(shop.getMpt());
//
//        shopOrderRepository.save(shopOrder);
//        String s = "{\"avgSendTime\":2400.0,\"caution\":\"\",\"cityId\":420500,\"ctime\":1506586628,\"daySeq\":\"21\",\"deliveryTime\":0,\"detail\":\"[{\\\"app_food_code\\\":\\\"?????+?????\\\",\\\"box_num\\\":1,\\\"box_price\\\":0,\\\"cart_id\\\":0,\\\"food_discount\\\":1,\\\"food_name\\\":\\\"?????+?????\\\",\\\"food_property\\\":\\\"??,?\\\",\\\"price\\\":20,\\\"quantity\\\":1,\\\"sku_id\\\":\\\"\\\",\\\"spec\\\":\\\"\\\",\\\"unit\\\":\\\"?\\\"}]\",\"dinnersNumber\":0,\"ePoiId\":\"bc52f284-ea25-4556-bd36-43d2a5b1219b\",\"extras\":\"[{}]\",\"hasInvoiced\":0,\"invoiceTitle\":\"\",\"isFavorites\":false,\"isPoiFirstOrder\":false,\"isThirdShipping\":0,\"latitude\":30.701898,\"logisticsCode\":\"2002\",\"longitude\":111.279216,\"orderId\":6292920071,\"orderIdView\":24396521203884554,\"originalPrice\":22.0,\"payType\":2,\"poiAddress\":\"??????26?\",\"poiFirstOrder\":false,\"poiId\":2439652,\"poiName\":\"?????????????\",\"poiPhone\":\"17371724087\",\"poiReceiveDetail\":\"{\\\"actOrderChargeByMt\\\":[],\\\"actOrderChargeByMtIterator\\\":{},\\\"actOrderChargeByMtSize\\\":0,\\\"actOrderChargeByPoi\\\":[],\\\"actOrderChargeByPoiIterator\\\":{\\\"$ref\\\":\\\"$.actOrderChargeByMtIterator\\\"},\\\"actOrderChargeByPoiSize\\\":0,\\\"foodShareFeeChargeByPoi\\\":300,\\\"logisticsFee\\\":200,\\\"onlinePayment\\\":2200,\\\"setActOrderChargeByMt\\\":true,\\\"setActOrderChargeByPoi\\\":true,\\\"setFoodShareFeeChargeByPoi\\\":true,\\\"setLogisticsFee\\\":true,\\\"setOnlinePayment\\\":true,\\\"setWmPoiReceiveCent\\\":true,\\\"wmPoiReceiveCent\\\":1700}\",\"recipientAddress\":\"?????? (B?1203?)@#?????????????????8???????\",\"recipientName\":\"??(??)\",\"recipientPhone\":\"15549319591\",\"shipperPhone\":\"\",\"shippingFee\":2.0,\"status\":2,\"taxpayerId\":\"\",\"total\":22.0,\"utime\":1506586628}";
//        if (orderConfirm(shop, shopOrder.getId())) {
//            return "{\"data\": \"OK\"}";
//        }
//        return "{\"data\": \"failed\"}";
//    }
//
//    //商家确认接单
//    private boolean orderConfirm(Shop shop, String orderId) {
//        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("appAuthToken", shop.getAppAuthToken());
//        params.put("charset", "UTF-8");
//        params.put("orderId", Long.valueOf(orderId));
//        return post("http://api.open.cater.meituan.com/waimai/order/confirm", params);
//    }
//
//    /**
//     * developerId:100120
//     * ePoiId:9384019
//     * sign:52b379754c40c7865a48b84a24fb99c1ebb49f11
//     * orderCancel:{
//     * "orderId":12341234,
//     * "reason":"超时取消",
//     * "reasonCode":"1002"
//     * }
//     */
//    //美团用户或客服取消URL
//    @RequestMapping("/order_cancel")
//    public String order_cancel(String ePoiId, String orderCancel, HttpServletRequest request) throws IOException {
//        System.out.println("order_cancel");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        Map<String, Object> orderCancelMap = objectMapper.readValue(orderCancel, Map.class);
//        String orderId = orderCancelMap.get("orderId").toString();
////        Shop shop = shopRepository.getOne(ePoiId);
////        if (shop == null) return "{\"data\": \"failed\"}";
//        ShopOrder shopOrder = shopOrderRepository.getOne(orderId);
//        if (shopOrder == null) return "{\"data\": \"failed\"}";
//        //字段含义：1-用户已提交订单；2-可推送到App方平台也可推送到商家；4-商家已确认；6-已配送；8-已完成；9-已取消
//        shopOrder.setState("9");
//        shopOrderRepository.save(shopOrder);
//        return "{\"data\": \"OK\"}";
//    }
//
//    //美团用户或客服退款流程URL
//    @RequestMapping("/order_tui")
//    public String order_tui(HttpServletRequest request) throws IOException {
//        System.out.println("order_tui");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //已完成订单推送回调URL
//    @RequestMapping("/order_callback")
//    public String order_callback(HttpServletRequest request) throws IOException {
//        System.out.println("order_callback");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //订单已确认的回调URL
//    @RequestMapping("/order_ok")
//    public String order_ok(HttpServletRequest request) throws IOException {
//        System.out.println("order_ok");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //订单配送状态的回调URL
//    @RequestMapping("/order_state")
//    public String order_state(HttpServletRequest request) throws IOException {
//        System.out.println("order_state");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //门店更改的回调URL
//    @RequestMapping("/shop_change")
//    public String shop_change(HttpServletRequest request) throws IOException {
//        System.out.println("shop_change");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //外卖订单对账信息回调URL
//    @RequestMapping("/order_view")
//    public String order_view(HttpServletRequest request) throws IOException {
//        System.out.println("order_view");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //买单推送URL
//    @RequestMapping("/order_mai")
//    public String order_mai(HttpServletRequest request) throws IOException {
//        System.out.println("order_mai");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //订单校验和下单URL
//    @RequestMapping("/order_jiao")
//    public String order_jiao(HttpServletRequest request) throws IOException {
//        System.out.println("order_jiao");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//    //点餐推送消息接口URL
//    @RequestMapping("/order_dian")
//    public String order_dian(HttpServletRequest request) throws IOException {
//        System.out.println("order_dian");
//        System.out.println(objectMapper.writeValueAsString(request.getParameterMap()));
//        return "{\"data\": \"OK\"}";
//    }
//
//
//    //门店映射回调地址
//    @RequestMapping("/shop_binding")
//    public String shop_binding(String appAuthToken, String businessId, String ePoiId, String timestamp, HttpServletRequest request) {
//        System.out.println("shop_binding");
//        Shop shop = shopRepository.findById(ePoiId);
//        if (shop == null) return "{\"data\":\"failed\"} ";
//        shop.setAppAuthToken(appAuthToken);
//        shopRepository.save(shop);
//        return "{\"data\": \"OK\"}";
//    }
//
//    //门店映射解绑回调地址
//    @RequestMapping("/shop_unbinding")
//    public String shop_unbinding(String developerId, String epoiId, String businessId, String ePoiId, String timestamp, HttpServletRequest request) {
//        System.out.println("shop_unbinding");
//        Shop shop = shopRepository.findById(ePoiId);
//        if (shop == null) return "{\"data\":\"failed\"} ";
//        shop.setAppAuthToken("");
//        shopRepository.save(shop);
//        return "{\"data\": \"OK\"}";
//    }
//
//
//    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 60 * 1000)
//    public void shop() throws IOException {
//        shopRepository.findAll().forEach(shop -> {
//            if (StringUtils.isBlank(shop.getAppAuthToken())) return;
//            try {
//                Map<String, Object> params = new LinkedHashMap<>();
//                params.put("appAuthToken", shop.getAppAuthToken());
//                params.put("charset", "UTF-8");
//                params.put("ePoiIds", shop.getId());
//                params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//                String s = String.join("", params.keySet().stream().map(key -> key + params.get(key)).collect(Collectors.toList()));
//                String sign = Hex.encodeHexString(DigestUtils.getSha1Digest().digest((signKey + s).getBytes("utf-8"))).toLowerCase();
//                params.put("sign", sign);
//                String values = String.join("&", params.keySet().stream().map(key -> key + "=" + params.get(key)).collect(Collectors.toList()));
//                OkHttpClient client = new OkHttpClient();
//                //RequestBody body = RequestBody.create(form, values);
//                Request request = new Request.Builder()
//                        .url("http://api.open.cater.meituan.com/waimai/poi/queryPoiInfo?" + values)
//                        .get()
//                        .build();
//                Response response = client.newCall(request).execute();
//                //System.out.println(response.body().string());
//                ValueMap valueMap = ValueMap.wrap((Map) ((List) objectMapper.readValue(response.body().string(), Map.class).get("data")).get(0));
//
//                shop.setName(valueMap.valString("name"));
//                shop.setPhone(valueMap.valString("phone"));
//                shop.setLatitude(valueMap.valString("latitude"));
//                shop.setLongitude(valueMap.valString("longitude"));
//                shop.setAddress(valueMap.valString("address"));
//                shopRepository.save(shop);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//
//    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//    public static final MediaType form = MediaType.parse("application/x-www-form-urlencoded");
//
//    @Scheduled(initialDelay = 5 * 1000, fixedDelay = 20 * 1000)
//    public void live() throws IOException {
//         if (!production) return;
//
//        Map<String, Object> data = new LinkedHashMap<>();
//        data.put("developerId", developerId);
//        data.put("time", new Date().getTime());
//        data.put("list", shopRepository.findAll().stream().map(shop ->
//                new HashMap<String, Object>() {{
//                    put("ePoiId", shop.getId());
//                    put("posId", "1");
//                }}
//        ).collect(Collectors.toList()));
//        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        String json = objectMapper.writeValueAsString(data);
//        params.put("data", json);
//        post("http://heartbeat.meituan.com/pos/heartbeat", params, false);
//    }
//
//
//    //自配送－配送状态
//    private boolean orderDelivering(Shop shop, String orderId, String courierName, String courierPhone) {
//        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("appAuthToken", shop.getAppAuthToken());
//        params.put("charset", "UTF-8");
//        params.put("orderId", Long.valueOf(orderId));
//        params.put("courierName", courierName);
//        params.put("courierPhone", courierPhone);
//        return post("http://api.open.cater.meituan.com/waimai/order/delivering", params);
//    }
//
//    //自配送场景－订单已送达
//    private boolean orderDelivered(Shop shop, String orderId) {
//        LinkedHashMap<String, Object> params = new LinkedHashMap<>();
//        params.put("appAuthToken", shop.getAppAuthToken());
//        params.put("charset", "UTF-8");
//        params.put("orderId", Long.valueOf(orderId));
//        return post("http://api.open.cater.meituan.com/waimai/order/delivered", params);
//    }
//
//
//    public boolean post(String url, final LinkedHashMap<String, Object> params) {
//        try {
//            String responseText = post(url, params, true);
//            if (StringUtils.isBlank(responseText)) return false;
//            Map<String, Object> res = objectMapper.readValue(responseText, Map.class);
//            return "OK".equals(res.get("data"));
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    public String post(String url, final LinkedHashMap<String, Object> params, boolean time) {
//        try {
//            if (time) params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//            String s = String.join("", params.keySet().stream().map(key -> key + params.get(key)).collect(Collectors.toList()));
//            String sign = Hex.encodeHexString(DigestUtils.getSha1Digest().digest((signKey + s).getBytes("utf-8"))).toLowerCase();
//            params.put("sign", sign);
//            String values = String.join("&", params.keySet().stream().map(key -> key + "=" + params.get(key)).collect(Collectors.toList()));
//            OkHttpClient client = new OkHttpClient();
//            RequestBody body = RequestBody.create(form, values);
//            Request request = new Request.Builder()
//                    .url(url)
//                    .post(body)
//                    .build();
//            Response response = client.newCall(request).execute();
//            return response.body().string();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public boolean get(String url, final LinkedHashMap<String, Object> params) {
//        try {
//            params.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
//            String s = String.join("", params.keySet().stream().map(key -> key + params.get(key)).collect(Collectors.toList()));
//            String sign = Hex.encodeHexString(DigestUtils.getSha1Digest().digest((signKey + s).getBytes("utf-8"))).toLowerCase();
//            params.put("sign", sign);
//            String values = String.join("&", params.keySet().stream().map(key -> key + "=" + params.get(key)).collect(Collectors.toList()));
//            OkHttpClient client = new OkHttpClient();
//            Request request = new Request.Builder()
//                    .url(url + "?" + values)
//                    .get()
//                    .build();
//            Response response = client.newCall(request).execute();
//            Map<String, Object> res = objectMapper.readValue(response.body().string(), Map.class);
//            return "OK".equals(res.get("data"));
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//        Double d = 111.279216;
//        System.out.println(d.toString().replace(".", ""));
//    }
//}
