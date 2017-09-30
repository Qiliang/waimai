//package com.xiaoql.web;
//
//
//import org.openqa.selenium.*;
//import org.openqa.selenium.phantomjs.PhantomJSDriver;
//import org.openqa.selenium.phantomjs.PhantomJSDriverService;
//import org.openqa.selenium.remote.DesiredCapabilities;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.FluentWait;
//import org.openqa.selenium.support.ui.WebDriverWait;
//
//import java.util.concurrent.TimeUnit;
//
//public class SimpleDriver extends PhantomJSDriver {
//
//
//    public static SimpleDriver create(String phantomjsPath) {
//
//        System.setProperty("phantomjs.binary.path", phantomjsPath);
//        DesiredCapabilities desiredCapabilities = DesiredCapabilities.phantomjs();
//        desiredCapabilities.setCapability("applicationCacheEnabled", true);
//        desiredCapabilities.setCapability("takesScreenshot", true);
//        desiredCapabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, phantomjsPath);
//        desiredCapabilities.setCapability("phantomjs.page.settings.userAgent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//        desiredCapabilities.setCapability("phantomjs.page.customHeaders.User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36");
//        SimpleDriver driver = new SimpleDriver(desiredCapabilities);
//        driver.manage().window().setSize(new Dimension(1900, 937));
//        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
//        return driver;
//    }
//
//    public SimpleDriver(Capabilities desiredCapabilities) {
//        super(desiredCapabilities);
//    }
//
//    public WebElement find0(String selector) {
//        try {
//            FluentWait<WebDriver> wait0 = new WebDriverWait(this, 0).ignoring(NoSuchElementException.class);
//            return wait0.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public WebElement find1(String selector) {
//        try {
//            FluentWait<WebDriver> wait1 = new WebDriverWait(this, 1).ignoring(NoSuchElementException.class);
//            return wait1.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(selector)));
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//
//    public WebElement find0(WebElement parent, String selector) {
//        try {
//            FluentWait<WebDriver> wait0 = new WebDriverWait(this, 0).ignoring(NoSuchElementException.class);
//            return wait0.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, By.cssSelector(selector)));
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public WebElement find1(WebElement parent, String selector) {
//        try {
//            FluentWait<WebDriver> wait1 = new WebDriverWait(this, 1).ignoring(NoSuchElementException.class);
//            return wait1.until(ExpectedConditions.presenceOfNestedElementLocatedBy(parent, By.cssSelector(selector)));
//        } catch (Exception e) {
//            return null;
//        }
//    }
//}
