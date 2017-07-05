package com.xiaoql.web;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Controller
public class Index {

    @Value("${phantomjs.path}")
    private String phantomjsPath;

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

            driver.get("http://e.waimai.meituan.com/#/v2/order/history");
            driver.switchTo().frame("hashframe");

            WebElement orderContent = driver.findElementByCssSelector(".order-content");
            orderContent.findElements(By.cssSelector("li")).forEach(li -> {
                System.out.println(li.getText());
            });

            WebElement btnNext = driver.findElementByCssSelector("button.J-next-page");
            List<String> orders = new ArrayList<>();
            while (btnNext.isEnabled()) {
                btnNext.click();
                sleep(1000);
                btnNext = driver.findElementByCssSelector("button.J-next-page");
                orderContent = driver.findElementByCssSelector(".order-content");
                orderContent.findElements(By.cssSelector("li")).forEach(li -> {
                    //System.out.println(li.getText());
                    orders.add(li.getText());
                });

            }
            model.addAttribute("orders", orders);
            return "index";
        } catch (Exception e) {
            throw e;
        } finally {
            driver.quit();
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
