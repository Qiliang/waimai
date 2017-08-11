package com.xiaoql.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoql.SpringApplicationContextHolder;
import com.xiaoql.domain.Shop;
import com.xiaoql.domain.ShopOrder;
import com.xiaoql.domain.ShopOrderRepository;
import com.xiaoql.domain.ShopRepository;
import okhttp3.*;
import okhttp3.RequestBody;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("/meituan/spider")
@Transactional
public class MeitunSpider {

    @Value("${phantomjs.path}")
    private String phantomjsPath;

    private final Map<String, SimpleDriver> loginMap = new HashMap<>();
    public static final Map<String, SimpleDriver> DRIVERMAP = new HashMap<>();

    private String access_token = null;

    @Autowired
    private SpringApplicationContextHolder springApplicationContextHolder;

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
        shopRepository.findAll().stream().parallel().forEach(shop -> {
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
            System.out.println(driver.getTitle());
            driver.switchTo().frame("J-logon-iframe");
            driver.findElementByCssSelector("input.login__login").sendKeys(shop.getLoginName());
            driver.findElementByCssSelector("input.login__password").sendKeys(shop.getLoginPassword());
            WebElement img = driver.find1("#login-form img");
            if (img != null) {
                System.out.println(img.getAttribute("src"));
            } else {
                driver.findElementByCssSelector(".login__submit").click();
                loginTo(id);
            }
        });

        return new RestResponse();
    }

    @PostMapping({"/{id}"})
//    @Transactional
    public Object get(@PathVariable String id) {
        // String id = "d214ZnoxODIyOTM6bGlhbmcxMjM=";
        // executorService.execute(()->orderSpider(id));
        Shop shop = shopRepository.findById(id);

        if (loginMap.containsKey(id)) {
            WebDriver webDriver = loginMap.remove(id);
          try{
              webDriver.close();
              webDriver.quit();
          }catch (Exception e){

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
        driver.executeScript("$('input.login__captcha').value='"+code+"'");
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

    @Scheduled(initialDelay = 10000, fixedDelay = 10 * 10 * 1000)
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


    @Scheduled(initialDelay = 10000, fixedDelay = 3 * 60 * 1000)
    public void orderSpider() {

        for (Shop shop : shopRepository.findAll()) {
            String shopId = shop.getId();
            executorService.execute(() -> lookShopOrder(shopId));
            //lookShopOrder(shop.getId());
        }

    }

    private void lookShopOrder(String id) {
        SimpleDriver driver = DRIVERMAP.get(id);
        if (driver == null) return;
        if (driver.getCurrentUrl().contains("logon")) return;
        driver.switchTo().defaultContent();
        Shop shop = shopRepository.findOne(id);
        //shopRepository.save(shop);
        long begin = System.currentTimeMillis();
        System.out.println(shop.getName());
        try {

//            if (driver.find1("#J-login-area") != null) {
//                return;
//            }
//
//            driver.get("http://e.waimai.meituan.com/#/v2/shop/manage/shopInfo");
//            driver.switchTo().frame("hashframe");
//            shop.setMeituanId(driver.findElementByCssSelector("a.shop-link").getAttribute("href"));
//            shop.setName(driver.findElementByCssSelector("h4.poi-name").getText());
//            shop.setAddress(driver.findElementByCssSelector("p.poi-address").getText().split("：")[1]);
//            shop.setPhone(driver.findElementByCssSelector("span.telephone-show").getText());
//            shopRepository.saveAndFlush(shop);
            //body > div.content-wrapper > div.panel.panel-default > form > div > div:nth-child(1) > div > div > label:nth-child(1)
            if (!driver.getCurrentUrl().contains("/v2/order/history")) {
                driver.switchTo().defaultContent();
                driver.get("http://e.waimai.meituan.com/#/v2/order/history");
                driver.switchTo().frame("hashframe");
            } else {
                driver.switchTo().frame("hashframe");
                //点击全部订单
                WebElement allOrders = driver.findElementByCssSelector("body > div.content-wrapper > div.panel.panel-default > form > div > div:nth-child(1) > div > div > label:nth-child(1)");
                allOrders.click();
            }


            Actions actions = new Actions(driver);
//            WebElement btnNext;
//            do {
                WebElement orderContent = driver.findElementByCssSelector("ul.J-order-list");
                sleep(1000);
                Document ulDoc = Jsoup.parse(orderContent.getAttribute("innerHTML"));
                if (ulDoc.select("#exception-pro").size() == 1) {
                    System.out.println(ulDoc.select("#exception-pro").text());
                    return;
                }

                for (WebElement li : orderContent.findElements(By.cssSelector("li.order-list-item"))) {

                    actions.moveToElement(li).build().perform();
                    String orderId = li.getAttribute("id");
                    Document liDoc = Jsoup.parse(li.getAttribute("outerHTML"));
                    ShopOrder order = shopOrderRepository.findOne(orderId);
                    if (order == null) {
                        Date orderTime = new Date();
                        orderTime.setTime(Long.valueOf(li.getAttribute("data-order-time")) * 1000);
                        order = new ShopOrder();
                        order.setShopId(shop.getId());
                        order.setId(li.getAttribute("id"));
                        order.setTime(orderTime);
                        order.setDescription(li.findElement(By.cssSelector(".product-list")).getText());
                        order.setShopAddress(shop.getAddress());
                        order.setShopName(shop.getName());
                        order.setRemark(remark(liDoc));
                        order.setUserName(li.findElement(By.cssSelector("div.user-name")).getText());
                        order.setState(li.findElement(By.cssSelector("div.order-state")).getText());
                        order.setUserPhone(phone(liDoc));
                        order.setUserAddress(address(liDoc));
                        WebElement mapInfo = li.findElement(By.className("j-show-map"));
                        order.setOrderLng(mapInfo.getAttribute("data-order-lng"));
                        order.setOrderLat(mapInfo.getAttribute("data-order-lat"));
                        order.setShopLng(mapInfo.getAttribute("data-poi-lng"));
                        order.setShopLat(mapInfo.getAttribute("data-poi-lat"));
                    } else {
                        order.setState(li.findElement(By.cssSelector("div.order-state")).getText());
                    }

                    shopOrderRepository.saveAndFlush(order);
                }

//                btnNext = driver.findElementByCssSelector("button.J-next-page");
//                actions.moveToElement(btnNext).build().perform();
//                if (!btnNext.isEnabled()) continue;
//                btnNext.click();
//            }
//            while (btnNext.isEnabled());

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

    private String address(Document liDoc) {
        if (liDoc.getElementsByClass("J-search-address").hasText()) {
            return liDoc.getElementsByClass("J-search-address").text();
        } else {
            String src = liDoc.select(".J-search-address img").attr("src");
            //String src = liDoc.getElementsByClass("J-search-address").get(0).getElementsByTag("img").get(0).attr("src");
            return ocr(src.substring(22));
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
