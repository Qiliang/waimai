package com.xiaoql;

import com.xiaoql.web.PageableExtHandlerMethodArgumentResolver;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.auditing.CurrentDateTimeProvider;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "springSecurityAuditorAware")
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

	@Bean
	DateTimeProvider dateTimeProvider() {
		return CurrentDateTimeProvider.INSTANCE;
	}


	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageableExtHandlerMethodArgumentResolver());
	}
}
