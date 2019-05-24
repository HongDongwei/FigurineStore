package com.codvision.figurinestore.module.bean;

/**
 * Created by sxy on 2019/5/24 0:50
 * todo
 */
public class OrderTableSubmit {
    private int orderid;

    private  String state;

    private  int amount;

    public OrderTableSubmit(int orderid, String state, int amount) {
        this.orderid = orderid;
        this.state = state;
        this.amount = amount;
    }

    public int getOrderid() {
        return orderid;
    }

    public void setOrderid(int orderid) {
        this.orderid = orderid;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
