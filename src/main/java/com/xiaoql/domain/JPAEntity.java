package com.xiaoql.domain;


import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.Map;

public abstract class JPAEntity {

    public void update(Map<String, Object> from) {
        from.keySet().forEach(key -> {
            Field field = FieldUtils.getField(this.getClass(), key, true);
            try {
                FieldUtils.writeField(field, this, from.get(key), true);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
