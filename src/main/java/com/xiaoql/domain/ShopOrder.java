package com.xiaoql.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class ShopOrder {

    @Id
    private String id;

    private String ShopId;

    private Date time;

    private String stime;

    private String state;

    private String userName;

    private String userPhone;

    private String userAddress;

    private String remark;

    private String shopLng;//经度
    private String shopLat;//纬度
    private String orderLng;
    private String orderLat;
    private String riderId;
    private String description;

    public String getShopId() {
        return ShopId;
    }

    public void setShopId(String shopId) {
        ShopId = shopId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId;
    }

    public String getShopLng() {
        return shopLng;
    }

    public void setShopLng(String shopLng) {
        this.shopLng = shopLng;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat;
    }

    public String getOrderLng() {
        return orderLng;
    }

    public void setOrderLng(String orderLng) {
        this.orderLng = orderLng;
    }

    public String getOrderLat() {
        return orderLat;
    }

    public void setOrderLat(String orderLat) {
        this.orderLat = orderLat;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getStime() {
        return stime;
    }

    public void setStime(String stime) {
        this.stime = stime;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(String userAddress) {
        this.userAddress = userAddress;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
