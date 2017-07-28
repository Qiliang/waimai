package com.xiaoql.domain;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.lang.reflect.Field;
import java.util.Map;

@Entity
public class User {

    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_SCHEDULE = "SCHEDULE";

    @Id
    private String id;
    private String name;
    private String loginName;
    private String loginPassword;
    private String role;
    private boolean alive;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginPassword() {
        return loginPassword;
    }

    public void setLoginPassword(String loginPassword) {
        this.loginPassword = loginPassword;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }


    public void update(Map<String, Object> from) {
        from.keySet().forEach(key -> {
            Field field = FieldUtils.getField(User.class, key, true);
            try {
                FieldUtils.writeField(field, this, from.get(key), true);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
    }
}
