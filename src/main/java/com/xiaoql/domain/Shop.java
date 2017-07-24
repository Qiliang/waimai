package com.xiaoql.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Shop {

    @Id
    private String id;

    private String meituanId;

    private String name;

    private String address;

    private String phone;

    private String loginName;
    private String loginPassword;


    public String getMeituanId() {
        return meituanId;
    }

    public void setMeituanId(String meituanId) {
        this.meituanId = meituanId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
