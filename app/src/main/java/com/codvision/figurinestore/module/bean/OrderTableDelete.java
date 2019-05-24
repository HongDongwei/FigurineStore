package com.codvision.figurinestore.module.bean;

public class OrderTableDelete {
    private int orderid;

    public OrderTableDelete(int orderid) {
        this.orderid = orderid;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }
}
