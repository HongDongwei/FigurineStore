package com.codvision.figurinestore.module.bean;

/**
 * Created by sxy on 2019/5/24 0:50
 * todo
 */
public class OrderTableInsert {

    private int userid;

    private int comid;

    private  String ordertime;

    private  String state;

    private  int amount;

    public OrderTableInsert(int userid, int comid, String ordertime, String state, int amount) {
        this.userid = userid;
        this.comid = comid;
        this.ordertime = ordertime;
        this.state = state;
        this.amount = amount;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getComid() {
        return comid;
    }

    public void setComid(int comid) {
        this.comid = comid;
    }

    public String getOrdertime() {
        return ordertime;
    }

    public void setOrdertime(String ordertime) {
        this.ordertime = ordertime;
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
