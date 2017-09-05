package com.xiaoql.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoql.domain.Shop;
import com.xiaoql.domain.ShopOrder;
import com.xiaoql.domain.ShopOrderRepository;
import com.xiaoql.domain.ShopRepository;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/meituan/spider")
@Transactional
public class MeitunSpider {

    @Value("${phantomjs.path}")
    private String phantomjsPath;

    @Value("${spider.enabled}")
    private boolean spiderEnabled;


    private final Map<String, SimpleDriver> loginMap = new HashMap<>();
    public static final Map<String, SimpleDriver> DRIVERMAP = new HashMap<>();

    private String access_token = null;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ShopOrderRepository shopOrderRepository;

    @Autowired
    private ShopRepository shopRepository;


    @PostMapping({"/onAll"})
    public Object startAll() {
        // String id = "d214ZnoxODIyOTM6bGlhbmcxMjM=";
        // executorService.execute(()->orderSpider(id));

        executorService.execute(() -> {
            for (Shop shop : shopRepository.findAll()) {
                String id = shop.getId();

                if (loginMap.containsKey(id)) {
                    WebDriver webDriver = loginMap.remove(id);
                    try {
                        webDriver.close();
                        webDriver.quit();
                    } catch (Exception e) {

                    }

                }
                SimpleDriver driver = SimpleDriver.create(phantomjsPath);
                loginMap.put(id, driver);
                driver.get("http://e.waimai.meituan.com/logon");
                sleep(1000);
                driver.switchTo().frame("J-logon-iframe");

                driver.findElementByCssSelector("input.login__login").sendKeys(shop.getLoginName());
                sleep(200);
                driver.findElementByCssSelector("input.login__password").sendKeys(shop.getLoginPassword());
                sleep(200);
                driver.findElementByCssSelector(".login__submit").click();
                loginTo(id);

            }
        });

        return new RestResponse();
    }

    @PostMapping({"/{id}"})
//    @Transactional
    public Object start(@PathVariable String id) {
        // String id = "d214ZnoxODIyOTM6bGlhbmcxMjM=";
        // executorService.execute(()->orderSpider(id));
        Shop shop = shopRepository.findById(id);

        if (loginMap.containsKey(id)) {
            WebDriver webDriver = loginMap.remove(id);
            try {
                webDriver.close();
                webDriver.quit();
            } catch (Exception e) {

            }

        }
        SimpleDriver driver = SimpleDriver.create(phantomjsPath);
        loginMap.put(id, driver);

        driver.get("http://e.waimai.meituan.com/logon");
        System.out.println(driver.getTitle());
        driver.switchTo().frame("J-logon-iframe");
        driver.findElementByCssSelector("input.login__login").sendKeys(shop.getLoginName());
        driver.findElementByCssSelector("input.login__password").sendKeys(shop.getLoginPassword());
        WebElement img = driver.find1("#login-form img");
        if (img != null) {
            System.out.println(img.getAttribute("src"));
            return img.getAttribute("src");
        } else {
            driver.findElementByCssSelector(".login__submit").click();
            loginTo(id);
            return "".intern();
        }

    }


    @GetMapping({"/{id}/screenshot"})
    public String screenshot(@PathVariable String id) {
        SimpleDriver driver = DRIVERMAP.get(id);
        if (driver == null) return "";
        return driver.getScreenshotAs(OutputType.BASE64);
    }


    @PostMapping({"/{id}/login"})
    @Transactional
    public Object login(@PathVariable String id, String code) {
        Shop shop = shopRepository.findById(id);
        SimpleDriver driver = loginMap.get(id);
        driver.switchTo().frame("J-logon-iframe");
        driver.executeScript("$('input.login__captcha').value='" + code + "'");
        //driver.findElementByCssSelector("input.login__captcha").sendKeys(code);
        driver.findElementByCssSelector(".login__submit").click();
        loginTo(id);
        return new RestResponse();
    }

    private void loginTo(String id) {
        if (DRIVERMAP.containsKey(id)) {
            WebDriver webDriver = DRIVERMAP.remove(id);
            try {
                webDriver.close();
                webDriver.quit();
            } catch (Exception e) {

            }
        }

        DRIVERMAP.put(id, loginMap.remove(id));
    }

    //@Scheduled(initialDelay = 10000, fixedDelay = 60 * 60 * 1000)
    @Transactional
    public void baiduToken() throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token?grant_type=client_credentials&client_id=bhl4mO2L4a58lCw1MPhDYeyz&client_secret=7wE4vYdwEgBPd8bZcPaYfqpAfnH56XW1")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        Map<String, Object> result = objectMapper.readValue(response.body().string(), Map.class);
        access_token = result.get("access_token").toString();
    }


    //@Scheduled(initialDelay = 20 * 1000, fixedDelay = 60 * 1000)
    public void logonSpider() {
        if (!spiderEnabled) return;
        for (Shop shop : shopRepository.findAll()) {
            String shopId = shop.getId();
            executorService.execute(() -> logonShop(shopId));
        }

    }

    private void logon(SimpleDriver driver, Shop shop) {
        driver.get("http://e.waimai.meituan.com/logon");
        sleep(1000);
        driver.switchTo().frame("J-logon-iframe");
        driver.findElementByCssSelector("input.login__login").sendKeys(shop.getLoginName());
        sleep(1000);
        driver.findElementByCssSelector("input.login__password").sendKeys(shop.getLoginPassword());
        sleep(1000);
        driver.findElementByCssSelector(".login__submit").click();
        System.out.println(driver.findElementByCssSelector("#login-form").getText());
        sleep(3000);
        long timestamp = new Date().getTime();
        driver.get("http://e.waimai.meituan.com/?time=" + timestamp + "#/v2/order/history");
        sleep(1000);
        driver.switchTo().frame("hashframe");
    }

    private void logonShop(String id) {
        SimpleDriver driver = DRIVERMAP.get(id);
        Shop shop = shopRepository.findById(id);
        if (driver == null) {
            driver = SimpleDriver.create(phantomjsPath);
        }
        try {
            URI uri = new URI(driver.getCurrentUrl());
            if (StringUtils.isNotBlank(uri.getPath()) && uri.getPath().contains("order/history")) {
                Document mainDoc = Jsoup.parse(driver.findElementByCssSelector("body").getAttribute("innerHTML"));
                if (mainDoc.select("#verify_img #verifyModal").size() > 0) {
                    logon(driver, shop);
                }
            } else if (StringUtils.isNotBlank(uri.getPath()) && uri.getPath().contains("logon")) {
                logon(driver, shop);
            } else if (uri.toString().equals("about:blank")) {
                logon(driver, shop);
            }
            DRIVERMAP.put(id, driver);
        } catch (Exception e) {
            //driver.quit();
        }

    }


   // @Scheduled(initialDelay = 10000, fixedDelay = 4 * 60 * 1000)
    public void orderSpider() {
        if (!spiderEnabled) return;
        for (Shop shop : shopRepository.findAll()) {
            String shopId = shop.getId();
            executorService.execute(() -> lookShopOrder(shopId));
            //lookShopOrder(shop.getId());
        }

    }

    private List<Map<String, Object>> orders(SimpleDriver driver) {

        String today = DateFormatUtils.format(new Date(), "yyyy-MM-dd");
        driver.executeScript("$.get('/v2/order/history/r/query?getNewVo=1&wmOrderPayType=-2&wmOrderStatus=-2&sortField=1&startDate=" + today + "&endDate=" + today + "&lastLabel=&nextLabel=',function(data){window.orders=data})");
        long total = 0;
        while (total < 200 * 30) {
            Map<String, Object> response = (Map<String, Object>) driver.executeScript("return window.orders");
            if (response != null) {
                if ((Long) response.get("code") != 0) {
                    return null;
                }
                if (response.get("wmOrderList") instanceof List) {
                    return (List<Map<String, Object>>) response.get("wmOrderList");
                }

                return new ArrayList<>();
            }
            sleep(200);
            total += 200;
        }
        return new ArrayList<>();
    }

    private Map<String, Object> ordersImages(SimpleDriver driver, List<String> ids, String meiTuanShopId) {
        try {
            String orderInfos = "[" + StringUtils.join(ids.stream().map(s -> "{\"wmOrderViewId\":" + s + ",\"wmPoiId\":" + meiTuanShopId + ",\"logisticsStatus\":0,\"logisticsCode\":\"0000\"}").collect(Collectors.toList()), ",") + "]";
            driver.executeScript("$.get('http://e.waimai.meituan.com/v2/order/history/r/getImageString?type=3&orderInfos=" + URLEncoder.encode(orderInfos, "utf-8") + "',function(data){window.ordersImages=data})");
            long total = 0;
            while (total < 200 * 30) {
                Map<String, Object> response = (Map<String, Object>) driver.executeScript("return window.ordersImages");
                if (response != null) return (Map<String, Object>) response.get("data");
                sleep(200);
                total += 200;
            }
            return new HashMap<>();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void lookShopOrder(String id) {
        SimpleDriver driver = DRIVERMAP.get(id);
        if (driver == null) return;
        if (driver.getCurrentUrl().contains("logon")) return;
        driver.switchTo().defaultContent();
        Shop shop = shopRepository.findOne(id);
        long begin = System.currentTimeMillis();
        System.out.println(shop.getName());
        try {
            long timestamp = new Date().getTime();
//{"wmOrderViewId":13781271578824180,"wmPoiId":1378127,"logisticsStatus":0,"logisticsCode":"0000"}
//            driver.executeScript("$.get('/v2/order/history/r/query?getNewVo=1&wmOrderPayType=-2&wmOrderStatus=-2&sortField=1&startDate=2017-08-17&endDate=2017-08-17&lastLabel=&nextLabel=',function(data){window.orders=data})");
//            sleep(2000);
//            Map<String, Object> response = (Map<String, Object>) driver.executeScript("return window.orders");
//            System.out.println(response);
//            List<Map<String, Object>> order = (List<Map<String, Object>>) response.get("wmOrderList");

//            driver.get("http://e.waimai.meituan.com/?time=" + timestamp + "#/v2/order/history");
//            sleep(1000);
//            driver.switchTo().frame("hashframe");
//            Actions actions = new Actions(driver);
//                WebElement orderContent = driver.findElementByCssSelector("ul.J-order-list");
//
//            Document ulDoc = Jsoup.parse(orderContent.getAttribute("innerHTML"));
//                if (ulDoc.select("#exception-pro").size() == 1) {
//                    System.out.println(ulDoc.select("#exception-pro").text());
//                    return;
//                }
            List<Map<String, Object>> orders = orders(driver);
            while (orders == null) {
                logon(driver, shop);
                orders = orders(driver);
            }
            Map<String, Object> ordersImages = Collections.emptyMap();
            while (!orders.isEmpty()) {
                List<String> ids = orders.stream().map(item -> (String) item.get("wm_order_id_view_str")).collect(Collectors.toList());
                ordersImages = ordersImages(driver, ids, orders.stream().map(item -> item.get("wm_poi_id")).findAny().get().toString());
                if (ordersImages.keySet().stream().allMatch(key -> ids.contains(key))) {
                    break;
                }
                sleep(200);
            }


            for (Map<String, Object> item : orders) {
                String orderId = (String) item.get("wm_order_id_view_str");
                // Integer meiTuanShopId = (Integer) item.get("wm_poi_id");
//                    actions.moveToElement(li).build().perform();
//                    String orderId = li.getAttribute("id");
//                    Document liDoc = Jsoup.parse(li.getAttribute("outerHTML"));
                    ShopOrder order = shopOrderRepository.findOne(orderId);
                    if (order == null) {
//                        driver.executeScript("$.post('/v2/order/history/r/print/info/v2?wmOrderViewId=" + orderId + "&wmPoiId=" + meiTuanShopId + "&getNewVo=1',function(data){window.abc=data})");
//                        sleep(2000);
//                        Object response = driver.executeScript("return window.abc");

                        Date orderTime = new Date();
                        orderTime.setTime(Long.valueOf((Long) item.get("order_time")) * 1000);
                        order = new ShopOrder();
                        order.setShopId(shop.getId());
                        order.setId(orderId);
                        order.setTime(orderTime);
                        order.setDescription((String) item.get("orderCopyContent"));
                        order.setShopAddress(shop.getAddress());
                        order.setShopName(shop.getName());
                        order.setShopPhone(shop.getPhone());
                        order.setRemark(item.get("remark").toString());
                        order.setUserName(item.get("recipient_name").toString());
                        order.setState(item.get("status").toString());
                        order.setUserPhone((String) item.get("recipient_bindedPhone"));
                        Map<String, Object> images = (Map<String, Object>) ordersImages.get(orderId);
                        order.setUserPhoneImg(images.get("recipient_phone").toString());
                        order.setUserAddress((String) item.get("recipient_address"));
                        order.setUserAddressImg(images.get("recipient_address").toString());
                        // WebElement mapInfo = li.findElement(By.className("j-show-map"));
                        order.setOrderLng(item.get("address_longitude").toString());
                        order.setOrderLat(item.get("address_latitude").toString());
                        order.setShopLng(item.get("poi_longitude").toString());
                        order.setShopLat(item.get("poi_latitude").toString());
                    } else {
                        //order.setState(li.findElement(By.cssSelector("div.order-state")).getText());
                    }
                if (StringUtils.isBlank(order.getUserAddress()) && StringUtils.isNotBlank(order.getUserAddressImg())) {
                    order.setUserAddress(ocr(order.getUserAddressImg().substring(22)));
                }
                if (StringUtils.isBlank(order.getUserPhone()) && StringUtils.isNotBlank(order.getUserPhoneImg())) {
                    order.setUserAddress(ocr(order.getUserAddressImg().substring(22)));
                }

                    shopOrderRepository.saveAndFlush(order);
                }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            long end = System.currentTimeMillis();
            System.out.println(shop.getName() + ':' + ((end - begin) / 1000));
            //driver.quit();
            //shop.setStealing(false);
            //shopRepository.save(shop);
        }

    }

    private String ocr(String img) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
            RequestBody body = RequestBody.create(mediaType, "image=" + URLEncoder.encode(img, "utf-8"));
            Request request = new Request.Builder()
                    .url("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + access_token)
                    .post(body)
                    .addHeader("content-type", "application/x-www-form-urlencoded")
                    .build();

            Response response = client.newCall(request).execute();
            Map<String, Object> result = objectMapper.readValue(response.body().string(), Map.class);
            List<Map<String, Object>> words_result = (List<Map<String, Object>>) result.get("words_result");
            return words_result.get(0).get("words").toString();
        } catch (Exception e) {
            return "".intern();
        }
    }

    private String remark(Document liDoc) {
        if (liDoc.getElementsByClass("remark-info").size() > 0) {
            return liDoc.getElementsByClass("remark-info").text();
        } else {
            return StringUtils.EMPTY;
        }
    }

    private String addressImg(Document liDoc) {
        if (liDoc.select(".J-search-address img").size() > 0) {
            return liDoc.select(".J-search-address img").attr("src");
        }
        return null;
    }

    private String address(Document liDoc) {
        if (liDoc.getElementsByClass("J-search-address").hasText()) {
            return liDoc.getElementsByClass("J-search-address").text();
        } else {
            String src = liDoc.select(".J-search-address img").attr("src");
            //String src = liDoc.getElementsByClass("J-search-address").get(0).getElementsByTag("img").get(0).attr("src");
            return ocr(src.substring(22));
        }
    }

    private String phoneImg(Document liDoc) {
        if (liDoc.select(".J-user-phone img").size() > 0) {
            return liDoc.select(".J-user-phone img").attr("src");
        } else {
            return null;
        }

    }

    private String phone(Document liDoc) {
        if (liDoc.getElementsByClass("J-user-phone").hasText()) {
            return liDoc.getElementsByClass("J-user-phone").text();
        } else {
            String src = liDoc.select(".J-user-phone img").attr("src");
            //String src = liDoc.getElementsByClass("J-user-phone").get(0).getElementsByTag("img").get(0).attr("src");
            return ocr(src.substring(22));
        }

//
        //data-original-title
//        Actions action = new Actions(driver);
//        WebElement we = li.findElement(By.className("J-user-phone"));
//        action.moveToElement(we).build().perform();
//        driver.executeScript("var element=arguments[0];$(element).trigger('mouseover')",we);
//        sleep(1000);
//        if (we.getAttribute("data-original-title") != null) {
//            return we.getAttribute("data-original-title");
//        } else {
//            return we.getText();
//        }
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
