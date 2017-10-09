package com.xiaoql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class WaimaiApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
		System.setProperty("user.timezone","Asia/Shanghai");
		System.setProperty("file.encoding","UTF-8");
        System.setProperty("sun.jnu.encoding","UTF-8");
        System.out.println("file.encoding:" + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding:" + System.getProperty("sun.jnu.encoding"));
		Calendar calendar = Calendar.getInstance();
		System.out.println("目前时间：" + calendar.getTime());
		System.out.println("Calendar时区：：" + calendar.getTimeZone().getID());
		System.out.println("user.timezone：" + System.getProperty("user.timezone"));
		System.out.println("user.country：" + System.getProperty("user.country"));
		System.out.println("默认时区：" + TimeZone.getDefault().getID());
		SpringApplication.run(WaimaiApplication.class, args);
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}

}
