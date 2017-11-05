package com.xiaoql.entity;

import java.util.Date;

public class ShopOrder {
    private String id;

    private Date time;

    private String shopId;

    private String shopPhone;

    private String shopName;

    private String shopAddress;

    private String shopLng;

    private String shopLat;

    private String recipientName;

    private String recipientPhone;

    private String recipientAddress;

    private String recipientLng;

    private String recipientLat;

    private Double shippingFee;

    private Double total;

    private String riderId;

    private String riderName;

    private String riderPhone;

    private Integer status;

    private Date riderAssignTime;

    private Date riderReadTime;

    private Date riderGetGoodsTime;

    private String riderGetGoodsLng;

    private String riderGetGoodsLat;

    private Date riderCompleteTime;

    private String riderCompleteLng;

    private String riderCompleteLat;

    private Integer riderCoast;

    private String daySeq;

    private Integer type;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId == null ? null : shopId.trim();
    }

    public String getShopPhone() {
        return shopPhone;
    }

    public void setShopPhone(String shopPhone) {
        this.shopPhone = shopPhone == null ? null : shopPhone.trim();
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName == null ? null : shopName.trim();
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress == null ? null : shopAddress.trim();
    }

    public String getShopLng() {
        return shopLng;
    }

    public void setShopLng(String shopLng) {
        this.shopLng = shopLng == null ? null : shopLng.trim();
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLat) {
        this.shopLat = shopLat == null ? null : shopLat.trim();
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName == null ? null : recipientName.trim();
    }

    public String getRecipientPhone() {
        return recipientPhone;
    }

    public void setRecipientPhone(String recipientPhone) {
        this.recipientPhone = recipientPhone == null ? null : recipientPhone.trim();
    }

    public String getRecipientAddress() {
        return recipientAddress;
    }

    public void setRecipientAddress(String recipientAddress) {
        this.recipientAddress = recipientAddress == null ? null : recipientAddress.trim();
    }

    public String getRecipientLng() {
        return recipientLng;
    }

    public void setRecipientLng(String recipientLng) {
        this.recipientLng = recipientLng == null ? null : recipientLng.trim();
    }

    public String getRecipientLat() {
        return recipientLat;
    }

    public void setRecipientLat(String recipientLat) {
        this.recipientLat = recipientLat == null ? null : recipientLat.trim();
    }

    public Double getShippingFee() {
        return shippingFee;
    }

    public void setShippingFee(Double shippingFee) {
        this.shippingFee = shippingFee;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public String getRiderId() {
        return riderId;
    }

    public void setRiderId(String riderId) {
        this.riderId = riderId == null ? null : riderId.trim();
    }

    public String getRiderName() {
        return riderName;
    }

    public void setRiderName(String riderName) {
        this.riderName = riderName == null ? null : riderName.trim();
    }

    public String getRiderPhone() {
        return riderPhone;
    }

    public void setRiderPhone(String riderPhone) {
        this.riderPhone = riderPhone == null ? null : riderPhone.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getRiderAssignTime() {
        return riderAssignTime;
    }

    public void setRiderAssignTime(Date riderAssignTime) {
        this.riderAssignTime = riderAssignTime;
    }

    public Date getRiderReadTime() {
        return riderReadTime;
    }

    public void setRiderReadTime(Date riderReadTime) {
        this.riderReadTime = riderReadTime;
    }

    public Date getRiderGetGoodsTime() {
        return riderGetGoodsTime;
    }

    public void setRiderGetGoodsTime(Date riderGetGoodsTime) {
        this.riderGetGoodsTime = riderGetGoodsTime;
    }

    public String getRiderGetGoodsLng() {
        return riderGetGoodsLng;
    }

    public void setRiderGetGoodsLng(String riderGetGoodsLng) {
        this.riderGetGoodsLng = riderGetGoodsLng == null ? null : riderGetGoodsLng.trim();
    }

    public String getRiderGetGoodsLat() {
        return riderGetGoodsLat;
    }

    public void setRiderGetGoodsLat(String riderGetGoodsLat) {
        this.riderGetGoodsLat = riderGetGoodsLat == null ? null : riderGetGoodsLat.trim();
    }

    public Date getRiderCompleteTime() {
        return riderCompleteTime;
    }

    public void setRiderCompleteTime(Date riderCompleteTime) {
        this.riderCompleteTime = riderCompleteTime;
    }

    public String getRiderCompleteLng() {
        return riderCompleteLng;
    }

    public void setRiderCompleteLng(String riderCompleteLng) {
        this.riderCompleteLng = riderCompleteLng == null ? null : riderCompleteLng.trim();
    }

    public String getRiderCompleteLat() {
        return riderCompleteLat;
    }

    public void setRiderCompleteLat(String riderCompleteLat) {
        this.riderCompleteLat = riderCompleteLat == null ? null : riderCompleteLat.trim();
    }

    public Integer getRiderCoast() {
        return riderCoast;
    }

    public void setRiderCoast(Integer riderCoast) {
        this.riderCoast = riderCoast;
    }

    public String getDaySeq() {
        return daySeq;
    }

    public void setDaySeq(String daySeq) {
        this.daySeq = daySeq == null ? null : daySeq.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}