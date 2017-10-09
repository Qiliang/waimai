package com.xiaoql.API;


public class Status {

    //美团字段含义：1-用户已提交订单；2-可推送到App方平台也可推送到商家；4-商家已确认；6-已配送；8-已完成；9-已取消
    //送送送字段涵义 11-已分配骑手；12-已取餐；

    public final static Integer OrderConfirm = 4;
    public final static Integer OrderCanceled = 9;
    public final static Integer OrderRiderAssgin = 11;
    public final static Integer OrderRiderGotGoods = 12;
    public final static Integer OrderCompleted = 6;


    //骑手状态 0关闭接单   1开启接单
    public final static Integer RiderClosed = 0;
    public final static Integer RiderActived = 1;
}
