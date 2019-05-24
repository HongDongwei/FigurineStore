package com.codvision.figurinestore.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class ConnectUtils {

    private static final String TAG = "HeartBeatService";

    public static final int REPEAT_TIME = 5;//表示重连次数
    public static final String HOST = "192.168.1.105";//表示IP地址
    public static final int PORT = 60000;//表示端口号
    public static final int TIMEOUT = 5;//设置连接超时时间,超过5s还没连接上便抛出异常

    public static final int LOGIN = 1;
    public static final int UNLOGIN = 0;
    public static final int LOGINOUT = -1;
    public static final int LOGINWRONG = -2;


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String stringNowTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }



}
