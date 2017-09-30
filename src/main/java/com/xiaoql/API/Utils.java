package com.xiaoql.API;


import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Utils {


    public static void updateBean(Object bean, Map<String, Object> map) {
        updateBean(bean, map, "id");
    }

    public static void updateBean(Object bean, Map<String, Object> map, String keyName) {
        map.keySet().stream().filter(k -> !keyName.equals(k)).forEach(k -> {
            try {
                BeanUtils.setProperty(bean, k, map.get(k));
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
        });

    }

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public static Date toDate(String s) throws ParseException {
        return sdf.parse(s);
    }

}
