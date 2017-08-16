package com.xiaoql.web;


import org.springframework.data.domain.Sort;

import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface PageableParam {

    int value() default 10;

    int size() default 10;

    int page() default 0;

    String[] sort() default {};

    Sort.Direction direction() default Sort.Direction.ASC;
}
