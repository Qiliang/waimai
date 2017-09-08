package com.xiaoql.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ShopOrder extends JPAEntity {


    public ShopOrder() {
    }

    public ShopOrder(String id, String meituanViewId, String shopId, String shopName, String shopAddress, String shopPhone, Date time, String stime, String state, String userName, String userPhone, String userAddress, String remark, String shopLng, String shopLat, String orderLng, String orderLat, String description,double totalAfter) {
        this.id = id;
        this.meituanViewId = meituanViewId;
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopPhone = shopPhone;
        this.time = time;
        this.stime = stime;
        this.state = state;
        this.userName = userName;
        this.userPhone = userPhone;
        this.userAddress = userAddress;
        this.remark = remark;
        this.shopLng = shopLng;
        this.shopLat = shopLat;
        this.orderLng = orderLng;
        this.orderLat = orderLat;
        this.description = description;
        this.totalAfter=totalAfter;
    }

    @Id
    private String id;

    private String meituanViewId;

    private String shopId;

    private String shopName;

    private String shopAddress;
    private String shopPhone;

    private Date time;

    private String stime;

    private String state;

    private String userName;

    private String userPhone;
    @Lob
    private String userPhoneImg;
    private String userAddress;
    @Lob
    private String userAddressImg;
    private String remark;

    private String shopLng;//经度
    private String shopLat;//纬度
    private String orderLng;
    private String orderLat;
    @Lob
    private String raw;

    private double totalAfter;

    @OneToOne()
    @JoinColumn()
    private Rider rider;
    private String riderState;
    private Date riderAssignTime;//订单分配给骑手的时间
    private Date riderReadTime;//骑手读取订单的时间
    private Date riderGetGoodsTime;//骑手拿到货品的时间
    private String riderGetGoodsLng;
    private String riderGetGoodsLat;
    private Date riderToUserTime;//骑手送达给客户的时间
    private String riderToUserLng;
    private String riderToUserLat;
    private long riderCoast;//骑手订单耗时
    @Lob
    private String description;

    @CreatedDate
    private Date createdDate;

    @LastModifiedDate
    private Date lastModifiedDate;


    public double getTotalAfter() {
        return totalAfter;
    }

    public void setTotalAfter(double totalAfter) {
        this.totalAfter = totalAfter;
    }

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public String getMeituanViewId() {
        return meituanViewId;
    }

    public void setMeituanViewId(String meituanViewId) {
        this.meituanViewId = meituanViewId;
    }

    public Date getRiderReadTime() {
        return riderReadTime;
    }

    public void setRiderReadTime(Date riderReadTime) {
        this.riderReadTime = riderReadTime;
    }

    public String getRiderGetGoodsLng() {
        return riderGetGoodsLng;
    }

    public void setRiderGetGoodsLng(String riderGetGoodsLng) {
        this.riderGetGoodsLng = riderGetGoodsLng;
    }

    public long getRiderCoast() {
        return riderCoast;
    }

    public void setRiderCoast(long riderCoast) {
        this.riderCoast = riderCoast;
    }

    public String getUserPhoneImg() {
        return userPhoneImg;
    }

    public void setUserPhoneImg(String userPhoneImg) {
        this.userPhoneImg = userPhoneImg;
    }

    public String getUserAddressImg() {
        return userAddressImg;
    }

    public void setUserAddressImg(String userAddressImg) {
        this.userAddressImg = userAddressImg;
    }

    public String getRiderGetGoodsLat() {
        return riderGetGoodsLat;
    }

    public void setRiderGetGoodsLat(String riderGetGoodsLat) {
        this.riderGetGoodsLat = riderGetGoodsLat;
    }

    public String getRiderToUserLng() {
        return riderToUserLng;
    }

    public void setRiderToUserLng(String riderToUserLng) {
        this.riderToUserLng = riderToUserLng;
    }

    public String getRiderToUserLat() {
        return riderToUserLat;
    }

    public void setRiderToUserLat(String riderToUserLat) {
        this.riderToUserLat = riderToUserLat;
    }

    public Rider getRider() {
        return rider;
    }

    public void setRider(Rider rider) {
        this.rider = rider;
    }

    public String getRiderState() {
        return riderState;
    }

    public void setRiderState(String riderState) {
        this.riderState = riderState;
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Date getRiderAssignTime() {
        return riderAssignTime;
    }

    public void setRiderAssignTime(Date riderAssignTime) {
        this.riderAssignTime = riderAssignTime;
    }

    public Date getRiderGetGoodsTime() {
        return riderGetGoodsTime;
    }

    public void setRiderGetGoodsTime(Date riderGetGoodsTime) {
        this.riderGetGoodsTime = riderGetGoodsTime;
    }

    public Date getRiderToUserTime() {
        return riderToUserTime;
    }

    public void setRiderToUserTime(Date riderToUserTime) {
        this.riderToUserTime = riderToUserTime;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
