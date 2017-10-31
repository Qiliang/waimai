package com.xiaoql.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShopOrderExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ShopOrderExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andTimeIsNull() {
            addCriterion("time is null");
            return (Criteria) this;
        }

        public Criteria andTimeIsNotNull() {
            addCriterion("time is not null");
            return (Criteria) this;
        }

        public Criteria andTimeEqualTo(Date value) {
            addCriterion("time =", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotEqualTo(Date value) {
            addCriterion("time <>", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThan(Date value) {
            addCriterion("time >", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("time >=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThan(Date value) {
            addCriterion("time <", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeLessThanOrEqualTo(Date value) {
            addCriterion("time <=", value, "time");
            return (Criteria) this;
        }

        public Criteria andTimeIn(List<Date> values) {
            addCriterion("time in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotIn(List<Date> values) {
            addCriterion("time not in", values, "time");
            return (Criteria) this;
        }

        public Criteria andTimeBetween(Date value1, Date value2) {
            addCriterion("time between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andTimeNotBetween(Date value1, Date value2) {
            addCriterion("time not between", value1, value2, "time");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNull() {
            addCriterion("shop_id is null");
            return (Criteria) this;
        }

        public Criteria andShopIdIsNotNull() {
            addCriterion("shop_id is not null");
            return (Criteria) this;
        }

        public Criteria andShopIdEqualTo(String value) {
            addCriterion("shop_id =", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotEqualTo(String value) {
            addCriterion("shop_id <>", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThan(String value) {
            addCriterion("shop_id >", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdGreaterThanOrEqualTo(String value) {
            addCriterion("shop_id >=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThan(String value) {
            addCriterion("shop_id <", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLessThanOrEqualTo(String value) {
            addCriterion("shop_id <=", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdLike(String value) {
            addCriterion("shop_id like", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotLike(String value) {
            addCriterion("shop_id not like", value, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdIn(List<String> values) {
            addCriterion("shop_id in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotIn(List<String> values) {
            addCriterion("shop_id not in", values, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdBetween(String value1, String value2) {
            addCriterion("shop_id between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopIdNotBetween(String value1, String value2) {
            addCriterion("shop_id not between", value1, value2, "shopId");
            return (Criteria) this;
        }

        public Criteria andShopPhoneIsNull() {
            addCriterion("shop_phone is null");
            return (Criteria) this;
        }

        public Criteria andShopPhoneIsNotNull() {
            addCriterion("shop_phone is not null");
            return (Criteria) this;
        }

        public Criteria andShopPhoneEqualTo(String value) {
            addCriterion("shop_phone =", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotEqualTo(String value) {
            addCriterion("shop_phone <>", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneGreaterThan(String value) {
            addCriterion("shop_phone >", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("shop_phone >=", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneLessThan(String value) {
            addCriterion("shop_phone <", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneLessThanOrEqualTo(String value) {
            addCriterion("shop_phone <=", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneLike(String value) {
            addCriterion("shop_phone like", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotLike(String value) {
            addCriterion("shop_phone not like", value, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneIn(List<String> values) {
            addCriterion("shop_phone in", values, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotIn(List<String> values) {
            addCriterion("shop_phone not in", values, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneBetween(String value1, String value2) {
            addCriterion("shop_phone between", value1, value2, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopPhoneNotBetween(String value1, String value2) {
            addCriterion("shop_phone not between", value1, value2, "shopPhone");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNull() {
            addCriterion("shop_name is null");
            return (Criteria) this;
        }

        public Criteria andShopNameIsNotNull() {
            addCriterion("shop_name is not null");
            return (Criteria) this;
        }

        public Criteria andShopNameEqualTo(String value) {
            addCriterion("shop_name =", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotEqualTo(String value) {
            addCriterion("shop_name <>", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThan(String value) {
            addCriterion("shop_name >", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameGreaterThanOrEqualTo(String value) {
            addCriterion("shop_name >=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThan(String value) {
            addCriterion("shop_name <", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLessThanOrEqualTo(String value) {
            addCriterion("shop_name <=", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameLike(String value) {
            addCriterion("shop_name like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotLike(String value) {
            addCriterion("shop_name not like", value, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameIn(List<String> values) {
            addCriterion("shop_name in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotIn(List<String> values) {
            addCriterion("shop_name not in", values, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameBetween(String value1, String value2) {
            addCriterion("shop_name between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopNameNotBetween(String value1, String value2) {
            addCriterion("shop_name not between", value1, value2, "shopName");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNull() {
            addCriterion("shop_address is null");
            return (Criteria) this;
        }

        public Criteria andShopAddressIsNotNull() {
            addCriterion("shop_address is not null");
            return (Criteria) this;
        }

        public Criteria andShopAddressEqualTo(String value) {
            addCriterion("shop_address =", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotEqualTo(String value) {
            addCriterion("shop_address <>", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThan(String value) {
            addCriterion("shop_address >", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressGreaterThanOrEqualTo(String value) {
            addCriterion("shop_address >=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThan(String value) {
            addCriterion("shop_address <", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLessThanOrEqualTo(String value) {
            addCriterion("shop_address <=", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressLike(String value) {
            addCriterion("shop_address like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotLike(String value) {
            addCriterion("shop_address not like", value, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressIn(List<String> values) {
            addCriterion("shop_address in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotIn(List<String> values) {
            addCriterion("shop_address not in", values, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressBetween(String value1, String value2) {
            addCriterion("shop_address between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopAddressNotBetween(String value1, String value2) {
            addCriterion("shop_address not between", value1, value2, "shopAddress");
            return (Criteria) this;
        }

        public Criteria andShopLngIsNull() {
            addCriterion("shop_lng is null");
            return (Criteria) this;
        }

        public Criteria andShopLngIsNotNull() {
            addCriterion("shop_lng is not null");
            return (Criteria) this;
        }

        public Criteria andShopLngEqualTo(String value) {
            addCriterion("shop_lng =", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngNotEqualTo(String value) {
            addCriterion("shop_lng <>", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngGreaterThan(String value) {
            addCriterion("shop_lng >", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngGreaterThanOrEqualTo(String value) {
            addCriterion("shop_lng >=", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngLessThan(String value) {
            addCriterion("shop_lng <", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngLessThanOrEqualTo(String value) {
            addCriterion("shop_lng <=", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngLike(String value) {
            addCriterion("shop_lng like", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngNotLike(String value) {
            addCriterion("shop_lng not like", value, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngIn(List<String> values) {
            addCriterion("shop_lng in", values, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngNotIn(List<String> values) {
            addCriterion("shop_lng not in", values, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngBetween(String value1, String value2) {
            addCriterion("shop_lng between", value1, value2, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLngNotBetween(String value1, String value2) {
            addCriterion("shop_lng not between", value1, value2, "shopLng");
            return (Criteria) this;
        }

        public Criteria andShopLatIsNull() {
            addCriterion("shop_lat is null");
            return (Criteria) this;
        }

        public Criteria andShopLatIsNotNull() {
            addCriterion("shop_lat is not null");
            return (Criteria) this;
        }

        public Criteria andShopLatEqualTo(String value) {
            addCriterion("shop_lat =", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatNotEqualTo(String value) {
            addCriterion("shop_lat <>", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatGreaterThan(String value) {
            addCriterion("shop_lat >", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatGreaterThanOrEqualTo(String value) {
            addCriterion("shop_lat >=", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatLessThan(String value) {
            addCriterion("shop_lat <", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatLessThanOrEqualTo(String value) {
            addCriterion("shop_lat <=", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatLike(String value) {
            addCriterion("shop_lat like", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatNotLike(String value) {
            addCriterion("shop_lat not like", value, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatIn(List<String> values) {
            addCriterion("shop_lat in", values, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatNotIn(List<String> values) {
            addCriterion("shop_lat not in", values, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatBetween(String value1, String value2) {
            addCriterion("shop_lat between", value1, value2, "shopLat");
            return (Criteria) this;
        }

        public Criteria andShopLatNotBetween(String value1, String value2) {
            addCriterion("shop_lat not between", value1, value2, "shopLat");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNull() {
            addCriterion("recipient_name is null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIsNotNull() {
            addCriterion("recipient_name is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientNameEqualTo(String value) {
            addCriterion("recipient_name =", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotEqualTo(String value) {
            addCriterion("recipient_name <>", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThan(String value) {
            addCriterion("recipient_name >", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_name >=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThan(String value) {
            addCriterion("recipient_name <", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLessThanOrEqualTo(String value) {
            addCriterion("recipient_name <=", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameLike(String value) {
            addCriterion("recipient_name like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotLike(String value) {
            addCriterion("recipient_name not like", value, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameIn(List<String> values) {
            addCriterion("recipient_name in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotIn(List<String> values) {
            addCriterion("recipient_name not in", values, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameBetween(String value1, String value2) {
            addCriterion("recipient_name between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientNameNotBetween(String value1, String value2) {
            addCriterion("recipient_name not between", value1, value2, "recipientName");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIsNull() {
            addCriterion("recipient_phone is null");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIsNotNull() {
            addCriterion("recipient_phone is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneEqualTo(String value) {
            addCriterion("recipient_phone =", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotEqualTo(String value) {
            addCriterion("recipient_phone <>", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneGreaterThan(String value) {
            addCriterion("recipient_phone >", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_phone >=", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLessThan(String value) {
            addCriterion("recipient_phone <", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLessThanOrEqualTo(String value) {
            addCriterion("recipient_phone <=", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneLike(String value) {
            addCriterion("recipient_phone like", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotLike(String value) {
            addCriterion("recipient_phone not like", value, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneIn(List<String> values) {
            addCriterion("recipient_phone in", values, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotIn(List<String> values) {
            addCriterion("recipient_phone not in", values, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneBetween(String value1, String value2) {
            addCriterion("recipient_phone between", value1, value2, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientPhoneNotBetween(String value1, String value2) {
            addCriterion("recipient_phone not between", value1, value2, "recipientPhone");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressIsNull() {
            addCriterion("recipient_address is null");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressIsNotNull() {
            addCriterion("recipient_address is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressEqualTo(String value) {
            addCriterion("recipient_address =", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotEqualTo(String value) {
            addCriterion("recipient_address <>", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressGreaterThan(String value) {
            addCriterion("recipient_address >", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_address >=", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressLessThan(String value) {
            addCriterion("recipient_address <", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressLessThanOrEqualTo(String value) {
            addCriterion("recipient_address <=", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressLike(String value) {
            addCriterion("recipient_address like", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotLike(String value) {
            addCriterion("recipient_address not like", value, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressIn(List<String> values) {
            addCriterion("recipient_address in", values, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotIn(List<String> values) {
            addCriterion("recipient_address not in", values, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressBetween(String value1, String value2) {
            addCriterion("recipient_address between", value1, value2, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientAddressNotBetween(String value1, String value2) {
            addCriterion("recipient_address not between", value1, value2, "recipientAddress");
            return (Criteria) this;
        }

        public Criteria andRecipientLngIsNull() {
            addCriterion("recipient_lng is null");
            return (Criteria) this;
        }

        public Criteria andRecipientLngIsNotNull() {
            addCriterion("recipient_lng is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientLngEqualTo(String value) {
            addCriterion("recipient_lng =", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngNotEqualTo(String value) {
            addCriterion("recipient_lng <>", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngGreaterThan(String value) {
            addCriterion("recipient_lng >", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_lng >=", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngLessThan(String value) {
            addCriterion("recipient_lng <", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngLessThanOrEqualTo(String value) {
            addCriterion("recipient_lng <=", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngLike(String value) {
            addCriterion("recipient_lng like", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngNotLike(String value) {
            addCriterion("recipient_lng not like", value, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngIn(List<String> values) {
            addCriterion("recipient_lng in", values, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngNotIn(List<String> values) {
            addCriterion("recipient_lng not in", values, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngBetween(String value1, String value2) {
            addCriterion("recipient_lng between", value1, value2, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLngNotBetween(String value1, String value2) {
            addCriterion("recipient_lng not between", value1, value2, "recipientLng");
            return (Criteria) this;
        }

        public Criteria andRecipientLatIsNull() {
            addCriterion("recipient_lat is null");
            return (Criteria) this;
        }

        public Criteria andRecipientLatIsNotNull() {
            addCriterion("recipient_lat is not null");
            return (Criteria) this;
        }

        public Criteria andRecipientLatEqualTo(String value) {
            addCriterion("recipient_lat =", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatNotEqualTo(String value) {
            addCriterion("recipient_lat <>", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatGreaterThan(String value) {
            addCriterion("recipient_lat >", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatGreaterThanOrEqualTo(String value) {
            addCriterion("recipient_lat >=", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatLessThan(String value) {
            addCriterion("recipient_lat <", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatLessThanOrEqualTo(String value) {
            addCriterion("recipient_lat <=", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatLike(String value) {
            addCriterion("recipient_lat like", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatNotLike(String value) {
            addCriterion("recipient_lat not like", value, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatIn(List<String> values) {
            addCriterion("recipient_lat in", values, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatNotIn(List<String> values) {
            addCriterion("recipient_lat not in", values, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatBetween(String value1, String value2) {
            addCriterion("recipient_lat between", value1, value2, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andRecipientLatNotBetween(String value1, String value2) {
            addCriterion("recipient_lat not between", value1, value2, "recipientLat");
            return (Criteria) this;
        }

        public Criteria andShippingFeeIsNull() {
            addCriterion("shipping_fee is null");
            return (Criteria) this;
        }

        public Criteria andShippingFeeIsNotNull() {
            addCriterion("shipping_fee is not null");
            return (Criteria) this;
        }

        public Criteria andShippingFeeEqualTo(Double value) {
            addCriterion("shipping_fee =", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeNotEqualTo(Double value) {
            addCriterion("shipping_fee <>", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeGreaterThan(Double value) {
            addCriterion("shipping_fee >", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeGreaterThanOrEqualTo(Double value) {
            addCriterion("shipping_fee >=", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeLessThan(Double value) {
            addCriterion("shipping_fee <", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeLessThanOrEqualTo(Double value) {
            addCriterion("shipping_fee <=", value, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeIn(List<Double> values) {
            addCriterion("shipping_fee in", values, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeNotIn(List<Double> values) {
            addCriterion("shipping_fee not in", values, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeBetween(Double value1, Double value2) {
            addCriterion("shipping_fee between", value1, value2, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andShippingFeeNotBetween(Double value1, Double value2) {
            addCriterion("shipping_fee not between", value1, value2, "shippingFee");
            return (Criteria) this;
        }

        public Criteria andTotalIsNull() {
            addCriterion("total is null");
            return (Criteria) this;
        }

        public Criteria andTotalIsNotNull() {
            addCriterion("total is not null");
            return (Criteria) this;
        }

        public Criteria andTotalEqualTo(Double value) {
            addCriterion("total =", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotEqualTo(Double value) {
            addCriterion("total <>", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThan(Double value) {
            addCriterion("total >", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalGreaterThanOrEqualTo(Double value) {
            addCriterion("total >=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThan(Double value) {
            addCriterion("total <", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalLessThanOrEqualTo(Double value) {
            addCriterion("total <=", value, "total");
            return (Criteria) this;
        }

        public Criteria andTotalIn(List<Double> values) {
            addCriterion("total in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotIn(List<Double> values) {
            addCriterion("total not in", values, "total");
            return (Criteria) this;
        }

        public Criteria andTotalBetween(Double value1, Double value2) {
            addCriterion("total between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andTotalNotBetween(Double value1, Double value2) {
            addCriterion("total not between", value1, value2, "total");
            return (Criteria) this;
        }

        public Criteria andRiderIdIsNull() {
            addCriterion("rider_id is null");
            return (Criteria) this;
        }

        public Criteria andRiderIdIsNotNull() {
            addCriterion("rider_id is not null");
            return (Criteria) this;
        }

        public Criteria andRiderIdEqualTo(String value) {
            addCriterion("rider_id =", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdNotEqualTo(String value) {
            addCriterion("rider_id <>", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdGreaterThan(String value) {
            addCriterion("rider_id >", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdGreaterThanOrEqualTo(String value) {
            addCriterion("rider_id >=", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdLessThan(String value) {
            addCriterion("rider_id <", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdLessThanOrEqualTo(String value) {
            addCriterion("rider_id <=", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdLike(String value) {
            addCriterion("rider_id like", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdNotLike(String value) {
            addCriterion("rider_id not like", value, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdIn(List<String> values) {
            addCriterion("rider_id in", values, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdNotIn(List<String> values) {
            addCriterion("rider_id not in", values, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdBetween(String value1, String value2) {
            addCriterion("rider_id between", value1, value2, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderIdNotBetween(String value1, String value2) {
            addCriterion("rider_id not between", value1, value2, "riderId");
            return (Criteria) this;
        }

        public Criteria andRiderNameIsNull() {
            addCriterion("rider_name is null");
            return (Criteria) this;
        }

        public Criteria andRiderNameIsNotNull() {
            addCriterion("rider_name is not null");
            return (Criteria) this;
        }

        public Criteria andRiderNameEqualTo(String value) {
            addCriterion("rider_name =", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameNotEqualTo(String value) {
            addCriterion("rider_name <>", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameGreaterThan(String value) {
            addCriterion("rider_name >", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameGreaterThanOrEqualTo(String value) {
            addCriterion("rider_name >=", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameLessThan(String value) {
            addCriterion("rider_name <", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameLessThanOrEqualTo(String value) {
            addCriterion("rider_name <=", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameLike(String value) {
            addCriterion("rider_name like", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameNotLike(String value) {
            addCriterion("rider_name not like", value, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameIn(List<String> values) {
            addCriterion("rider_name in", values, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameNotIn(List<String> values) {
            addCriterion("rider_name not in", values, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameBetween(String value1, String value2) {
            addCriterion("rider_name between", value1, value2, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderNameNotBetween(String value1, String value2) {
            addCriterion("rider_name not between", value1, value2, "riderName");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneIsNull() {
            addCriterion("rider_phone is null");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneIsNotNull() {
            addCriterion("rider_phone is not null");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneEqualTo(String value) {
            addCriterion("rider_phone =", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneNotEqualTo(String value) {
            addCriterion("rider_phone <>", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneGreaterThan(String value) {
            addCriterion("rider_phone >", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneGreaterThanOrEqualTo(String value) {
            addCriterion("rider_phone >=", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneLessThan(String value) {
            addCriterion("rider_phone <", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneLessThanOrEqualTo(String value) {
            addCriterion("rider_phone <=", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneLike(String value) {
            addCriterion("rider_phone like", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneNotLike(String value) {
            addCriterion("rider_phone not like", value, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneIn(List<String> values) {
            addCriterion("rider_phone in", values, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneNotIn(List<String> values) {
            addCriterion("rider_phone not in", values, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneBetween(String value1, String value2) {
            addCriterion("rider_phone between", value1, value2, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andRiderPhoneNotBetween(String value1, String value2) {
            addCriterion("rider_phone not between", value1, value2, "riderPhone");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeIsNull() {
            addCriterion("rider_assign_time is null");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeIsNotNull() {
            addCriterion("rider_assign_time is not null");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeEqualTo(Date value) {
            addCriterion("rider_assign_time =", value, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeNotEqualTo(Date value) {
            addCriterion("rider_assign_time <>", value, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeGreaterThan(Date value) {
            addCriterion("rider_assign_time >", value, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rider_assign_time >=", value, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeLessThan(Date value) {
            addCriterion("rider_assign_time <", value, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeLessThanOrEqualTo(Date value) {
            addCriterion("rider_assign_time <=", value, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeIn(List<Date> values) {
            addCriterion("rider_assign_time in", values, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeNotIn(List<Date> values) {
            addCriterion("rider_assign_time not in", values, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeBetween(Date value1, Date value2) {
            addCriterion("rider_assign_time between", value1, value2, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderAssignTimeNotBetween(Date value1, Date value2) {
            addCriterion("rider_assign_time not between", value1, value2, "riderAssignTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeIsNull() {
            addCriterion("rider_read_time is null");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeIsNotNull() {
            addCriterion("rider_read_time is not null");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeEqualTo(Date value) {
            addCriterion("rider_read_time =", value, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeNotEqualTo(Date value) {
            addCriterion("rider_read_time <>", value, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeGreaterThan(Date value) {
            addCriterion("rider_read_time >", value, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rider_read_time >=", value, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeLessThan(Date value) {
            addCriterion("rider_read_time <", value, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeLessThanOrEqualTo(Date value) {
            addCriterion("rider_read_time <=", value, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeIn(List<Date> values) {
            addCriterion("rider_read_time in", values, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeNotIn(List<Date> values) {
            addCriterion("rider_read_time not in", values, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeBetween(Date value1, Date value2) {
            addCriterion("rider_read_time between", value1, value2, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderReadTimeNotBetween(Date value1, Date value2) {
            addCriterion("rider_read_time not between", value1, value2, "riderReadTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeIsNull() {
            addCriterion("rider_get_goods_time is null");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeIsNotNull() {
            addCriterion("rider_get_goods_time is not null");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeEqualTo(Date value) {
            addCriterion("rider_get_goods_time =", value, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeNotEqualTo(Date value) {
            addCriterion("rider_get_goods_time <>", value, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeGreaterThan(Date value) {
            addCriterion("rider_get_goods_time >", value, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rider_get_goods_time >=", value, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeLessThan(Date value) {
            addCriterion("rider_get_goods_time <", value, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeLessThanOrEqualTo(Date value) {
            addCriterion("rider_get_goods_time <=", value, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeIn(List<Date> values) {
            addCriterion("rider_get_goods_time in", values, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeNotIn(List<Date> values) {
            addCriterion("rider_get_goods_time not in", values, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeBetween(Date value1, Date value2) {
            addCriterion("rider_get_goods_time between", value1, value2, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsTimeNotBetween(Date value1, Date value2) {
            addCriterion("rider_get_goods_time not between", value1, value2, "riderGetGoodsTime");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngIsNull() {
            addCriterion("rider_get_goods_lng is null");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngIsNotNull() {
            addCriterion("rider_get_goods_lng is not null");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngEqualTo(String value) {
            addCriterion("rider_get_goods_lng =", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngNotEqualTo(String value) {
            addCriterion("rider_get_goods_lng <>", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngGreaterThan(String value) {
            addCriterion("rider_get_goods_lng >", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngGreaterThanOrEqualTo(String value) {
            addCriterion("rider_get_goods_lng >=", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngLessThan(String value) {
            addCriterion("rider_get_goods_lng <", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngLessThanOrEqualTo(String value) {
            addCriterion("rider_get_goods_lng <=", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngLike(String value) {
            addCriterion("rider_get_goods_lng like", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngNotLike(String value) {
            addCriterion("rider_get_goods_lng not like", value, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngIn(List<String> values) {
            addCriterion("rider_get_goods_lng in", values, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngNotIn(List<String> values) {
            addCriterion("rider_get_goods_lng not in", values, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngBetween(String value1, String value2) {
            addCriterion("rider_get_goods_lng between", value1, value2, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLngNotBetween(String value1, String value2) {
            addCriterion("rider_get_goods_lng not between", value1, value2, "riderGetGoodsLng");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatIsNull() {
            addCriterion("rider_get_goods_lat is null");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatIsNotNull() {
            addCriterion("rider_get_goods_lat is not null");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatEqualTo(String value) {
            addCriterion("rider_get_goods_lat =", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatNotEqualTo(String value) {
            addCriterion("rider_get_goods_lat <>", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatGreaterThan(String value) {
            addCriterion("rider_get_goods_lat >", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatGreaterThanOrEqualTo(String value) {
            addCriterion("rider_get_goods_lat >=", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatLessThan(String value) {
            addCriterion("rider_get_goods_lat <", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatLessThanOrEqualTo(String value) {
            addCriterion("rider_get_goods_lat <=", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatLike(String value) {
            addCriterion("rider_get_goods_lat like", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatNotLike(String value) {
            addCriterion("rider_get_goods_lat not like", value, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatIn(List<String> values) {
            addCriterion("rider_get_goods_lat in", values, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatNotIn(List<String> values) {
            addCriterion("rider_get_goods_lat not in", values, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatBetween(String value1, String value2) {
            addCriterion("rider_get_goods_lat between", value1, value2, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderGetGoodsLatNotBetween(String value1, String value2) {
            addCriterion("rider_get_goods_lat not between", value1, value2, "riderGetGoodsLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeIsNull() {
            addCriterion("rider_complete_time is null");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeIsNotNull() {
            addCriterion("rider_complete_time is not null");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeEqualTo(Date value) {
            addCriterion("rider_complete_time =", value, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeNotEqualTo(Date value) {
            addCriterion("rider_complete_time <>", value, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeGreaterThan(Date value) {
            addCriterion("rider_complete_time >", value, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("rider_complete_time >=", value, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeLessThan(Date value) {
            addCriterion("rider_complete_time <", value, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeLessThanOrEqualTo(Date value) {
            addCriterion("rider_complete_time <=", value, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeIn(List<Date> values) {
            addCriterion("rider_complete_time in", values, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeNotIn(List<Date> values) {
            addCriterion("rider_complete_time not in", values, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeBetween(Date value1, Date value2) {
            addCriterion("rider_complete_time between", value1, value2, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteTimeNotBetween(Date value1, Date value2) {
            addCriterion("rider_complete_time not between", value1, value2, "riderCompleteTime");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngIsNull() {
            addCriterion("rider_complete_lng is null");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngIsNotNull() {
            addCriterion("rider_complete_lng is not null");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngEqualTo(String value) {
            addCriterion("rider_complete_lng =", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngNotEqualTo(String value) {
            addCriterion("rider_complete_lng <>", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngGreaterThan(String value) {
            addCriterion("rider_complete_lng >", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngGreaterThanOrEqualTo(String value) {
            addCriterion("rider_complete_lng >=", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngLessThan(String value) {
            addCriterion("rider_complete_lng <", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngLessThanOrEqualTo(String value) {
            addCriterion("rider_complete_lng <=", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngLike(String value) {
            addCriterion("rider_complete_lng like", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngNotLike(String value) {
            addCriterion("rider_complete_lng not like", value, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngIn(List<String> values) {
            addCriterion("rider_complete_lng in", values, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngNotIn(List<String> values) {
            addCriterion("rider_complete_lng not in", values, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngBetween(String value1, String value2) {
            addCriterion("rider_complete_lng between", value1, value2, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLngNotBetween(String value1, String value2) {
            addCriterion("rider_complete_lng not between", value1, value2, "riderCompleteLng");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatIsNull() {
            addCriterion("rider_complete_lat is null");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatIsNotNull() {
            addCriterion("rider_complete_lat is not null");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatEqualTo(String value) {
            addCriterion("rider_complete_lat =", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatNotEqualTo(String value) {
            addCriterion("rider_complete_lat <>", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatGreaterThan(String value) {
            addCriterion("rider_complete_lat >", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatGreaterThanOrEqualTo(String value) {
            addCriterion("rider_complete_lat >=", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatLessThan(String value) {
            addCriterion("rider_complete_lat <", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatLessThanOrEqualTo(String value) {
            addCriterion("rider_complete_lat <=", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatLike(String value) {
            addCriterion("rider_complete_lat like", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatNotLike(String value) {
            addCriterion("rider_complete_lat not like", value, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatIn(List<String> values) {
            addCriterion("rider_complete_lat in", values, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatNotIn(List<String> values) {
            addCriterion("rider_complete_lat not in", values, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatBetween(String value1, String value2) {
            addCriterion("rider_complete_lat between", value1, value2, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCompleteLatNotBetween(String value1, String value2) {
            addCriterion("rider_complete_lat not between", value1, value2, "riderCompleteLat");
            return (Criteria) this;
        }

        public Criteria andRiderCoastIsNull() {
            addCriterion("rider_coast is null");
            return (Criteria) this;
        }

        public Criteria andRiderCoastIsNotNull() {
            addCriterion("rider_coast is not null");
            return (Criteria) this;
        }

        public Criteria andRiderCoastEqualTo(Integer value) {
            addCriterion("rider_coast =", value, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastNotEqualTo(Integer value) {
            addCriterion("rider_coast <>", value, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastGreaterThan(Integer value) {
            addCriterion("rider_coast >", value, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastGreaterThanOrEqualTo(Integer value) {
            addCriterion("rider_coast >=", value, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastLessThan(Integer value) {
            addCriterion("rider_coast <", value, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastLessThanOrEqualTo(Integer value) {
            addCriterion("rider_coast <=", value, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastIn(List<Integer> values) {
            addCriterion("rider_coast in", values, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastNotIn(List<Integer> values) {
            addCriterion("rider_coast not in", values, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastBetween(Integer value1, Integer value2) {
            addCriterion("rider_coast between", value1, value2, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andRiderCoastNotBetween(Integer value1, Integer value2) {
            addCriterion("rider_coast not between", value1, value2, "riderCoast");
            return (Criteria) this;
        }

        public Criteria andDaySeqIsNull() {
            addCriterion("day_seq is null");
            return (Criteria) this;
        }

        public Criteria andDaySeqIsNotNull() {
            addCriterion("day_seq is not null");
            return (Criteria) this;
        }

        public Criteria andDaySeqEqualTo(String value) {
            addCriterion("day_seq =", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqNotEqualTo(String value) {
            addCriterion("day_seq <>", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqGreaterThan(String value) {
            addCriterion("day_seq >", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqGreaterThanOrEqualTo(String value) {
            addCriterion("day_seq >=", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqLessThan(String value) {
            addCriterion("day_seq <", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqLessThanOrEqualTo(String value) {
            addCriterion("day_seq <=", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqLike(String value) {
            addCriterion("day_seq like", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqNotLike(String value) {
            addCriterion("day_seq not like", value, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqIn(List<String> values) {
            addCriterion("day_seq in", values, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqNotIn(List<String> values) {
            addCriterion("day_seq not in", values, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqBetween(String value1, String value2) {
            addCriterion("day_seq between", value1, value2, "daySeq");
            return (Criteria) this;
        }

        public Criteria andDaySeqNotBetween(String value1, String value2) {
            addCriterion("day_seq not between", value1, value2, "daySeq");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}