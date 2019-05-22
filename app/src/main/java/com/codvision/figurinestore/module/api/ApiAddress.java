package com.codvision.figurinestore.module.api;

/**
 * Created by sxy on 2019/5/8 13:48
 * todo
 */
public class ApiAddress {

    //生成环境
//    public final static String API = "http://192.168.2.102:8767/";
    public final static String API = "http://192.168.1.104:8767/";

    /**************************************个人中心************************************************/

    //登录
    public final static String USER_LOGIN = "v1/vsm/user/login";
    //注册
    public final static String USER_REGISTER = "v1/vsm/user/register";
    //提交
    public final static String USER_SUBMIT = "v1/vsm/user/info/submit";
    //获取商品
    public final static String COMMODITY_SELECT = "v1/vsm/commodity/select/all";
    //获取商品
    public final static String COMMODITY_SELECT_BY_ID = "v1/vsm/commodity/select";
}
