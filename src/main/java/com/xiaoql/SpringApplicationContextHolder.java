package com.xiaoql;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.Validate.notEmpty;
import static org.apache.commons.lang3.Validate.notNull;

@Component
public class SpringApplicationContextHolder implements ApplicationContextAware {

    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringApplicationContextHolder.context = context;
    }


    public static <T> T bean(Class<T> aClass) {
        notNull(aClass);
        return context == null ? null : context.getBean(aClass);
    }

    public static Object bean(String beanName) {
        notEmpty(beanName, "bean name is required");
        return context == null ? null : context.getBean(beanName);
    }

}