package com.xiaoql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
public class WaimaiApplication extends WebMvcConfigurerAdapter {

	public static void main(String[] args) {
        System.setProperty("file.encoding","UTF-8");
        System.setProperty("sun.jnu.encoding","UTF-8");
        System.out.println("file.encoding:" + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding:" + System.getProperty("sun.jnu.encoding"));
		SpringApplication.run(WaimaiApplication.class, args);
	}

	@Bean
	public ExecutorService executorService() {
		return Executors.newCachedThreadPool();
	}

}
