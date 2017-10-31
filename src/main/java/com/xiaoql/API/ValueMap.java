package com.xiaoql.API;


import java.util.HashMap;
import java.util.Map;

public class ValueMap extends HashMap<String, Object> {


    public static ValueMap wrap(Map<? extends String, ?> m) {
        return new ValueMap(m);
    }

    public ValueMap(Map<? extends String, ?> m) {
        super(m);
    }

    public String valString(String key) {
        if (this.get(key) instanceof Double) {
            return String.valueOf(((Double) this.get(key)).longValue());
        }

        return String.valueOf(this.get(key));
    }

    public String valLong(String key) {
        return (String) this.get(key);
    }

    public int valInt(String key) {
        Object value = this.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof Long) {
            return ((Long) value).intValue();
        } else if (value instanceof Double) {
            return ((Double) value).intValue();
        } else if (value instanceof Float) {
            return ((Float) value).intValue();
        }
        return (Integer) this.get(key);
    }

}
