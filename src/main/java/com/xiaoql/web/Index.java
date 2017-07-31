package com.xiaoql.web;

import com.xiaoql.domain.OrderRepository;
import com.xiaoql.domain.Shop;
import com.xiaoql.domain.ShopOrder;
import com.xiaoql.domain.ShopRepository;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class Index {

    @Value("${phantomjs.path}")
    private String phantomjsPath;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ShopRepository shopRepository;

    private PhantomJSDriver getPhantomJs() {

        System.setProperty("phantomjs.binary.path", phantomjsPath);
        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
        desiredCapabilities.setCapability("applicationCacheEnabled", true);
        desiredCapabilities.setCapability("takesScreenshot", true);
        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjsPath);
        desiredCapabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        desiredCapabilities.setCapability("phantomjs.page.customHeaders.User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
        PhantomJSDriver driver = new PhantomJSDriver(desiredCapabilities);
        driver.manage().window().setSize(new Dimension(1900, 937));
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        return driver;
    }

    @PostMapping({"/", ""})
    public String get(@RequestParam String username, @RequestParam String password, Model model) {
        PhantomJSDriver driver = getPhantomJs();
        try {
            driver.get("http://e.waimai.meituan.com/logon");
            System.out.println(driver.getTitle());
            driver.switchTo().frame("J-logon-iframe");
            driver.findElementByCssSelector("input.login__login").sendKeys("wmxfz182293");
            driver.findElementByCssSelector("input.login__password").sendKeys("liang123");
            driver.findElementByCssSelector(".login__submit").click();
            sleep(3000);

            driver.get("http://e.waimai.meituan.com/#/v2/shop/manage/shopInfo");
            driver.switchTo().frame("hashframe");
            Shop shop = new Shop();
            shop.setId(Base64.getEncoder().encodeToString(String.format("%s:%s", username, password).getBytes()));
            shop.setMeituanId(driver.findElementByCssSelector("a.shop-link").getAttribute("href"));
            shop.setName(driver.findElementByCssSelector("h4.poi-name").getText());
            shop.setAddress(driver.findElementByCssSelector("p.poi-address").getText().split("：")[1]);
            shop.setPhone(driver.findElementByCssSelector("span.telephone-show").getText());
            shop.setLoginName(username);
            shop.setLoginPassword(password);
            shopRepository.save(shop);

            driver.switchTo().defaultContent();
            driver.get("http://e.waimai.meituan.com/#/v2/order/history");
            driver.switchTo().frame("hashframe");

            WebElement btnNext = null;
            List<String> orders = new ArrayList<>();
            do {
//<i class="j-show-map fa fa-map-marker" data-addr="机电小区 (三江桥北门外正街23*****" data-poi-lat="30713350" data-poi-lng="111285591" data-order-lat="30714533" data-order-lng="111284602"> &lt;1km</i>
                btnNext = driver.findElementByCssSelector("button.J-next-page");
                WebElement orderContent = driver.findElementByCssSelector("ul.J-order-list");
                orderContent.findElements(By.cssSelector("li.order-list-item")).forEach(li -> {
                    //System.out.println(li.getText());
                    Date orderTime = new Date();
                    orderTime.setTime(Long.valueOf(li.getAttribute("data-order-time")));
                    ShopOrder order = new ShopOrder();
                    order.setShopId(shop.getId());
                    order.setId(li.getAttribute("id"));
                    order.setTime(orderTime);
                    order.setShopAddress(shop.getAddress());
                    order.setShopName(shop.getName());
//                    order.setRemark(remark(driver, li));
                    order.setUserName(li.findElement(By.cssSelector("div.user-name")).getText());
                    order.setState(li.findElement(By.cssSelector("div.order-state")).getText());
                    order.setUserPhone(phone(driver, li));
                    order.setUserAddress(li.findElement(By.className("J-search-address")).getText());
                    WebElement mapInfo = li.findElement(By.className("j-show-map"));
                    order.setOrderLng(mapInfo.getAttribute("data-order-lng"));
                    order.setOrderLat(mapInfo.getAttribute("data-order-lat"));
                    order.setShopLng(mapInfo.getAttribute("data-poi-lng"));
                    order.setShopLat(mapInfo.getAttribute("data-poi-lat"));
                    order.setDescription(li.findElement(By.className("drop-con")).getText());
                    //drop-con
                    //j-show-map fa fa-map-marker

                    orderRepository.save(order);
                    orders.add(li.getText());
                });
                btnNext.click();
            }
            while (btnNext.isEnabled());

            model.addAttribute("orders", orders);
            return "index";
        } catch (Exception e) {
            throw e;
        } finally {
            driver.quit();
        }
    }


    private String remark(WebDriver driver, WebElement li) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            WebElement ws = wait.until(d -> li.findElement(By.cssSelector("div.remark-info")));
            return ws.getText();
        } catch (Exception e) {
            return "".intern();
        }
    }

    private String phone(PhantomJSDriver driver, WebElement li) {
        Actions action = new Actions(driver);
        WebElement we = li.findElement(By.className("J-user-phone"));
        action.moveToElement(we).build().perform();
        driver.executeScript("var element=arguments[0];$(element).trigger('mouseover')",we);
        sleep(1000);
        if (we.getAttribute("data-original-title") != null) {
            return we.getAttribute("data-original-title");
        } else {
            return we.getText();
        }
    }

    @GetMapping({"", "/"})
    public String index(HttpServletResponse response) {
        return "index";
    }

    private void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
