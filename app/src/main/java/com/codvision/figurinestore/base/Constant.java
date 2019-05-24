package com.codvision.figurinestore.base;

import android.util.Log;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by sxy on 2019/5/8 13:48
 * todo
 */
public class Constant {
    public static String logger = "logger";

    public static int orderNum = 0;
    public static int orderid = 0;
    private static boolean isPrintLog = true; //是否打开日志打印


    //日志打印
    public static void printLogger(String logTxt) {
        if (isPrintLog) {
            Log.d(logger, logTxt);
        }
    }


    public static int SUCCESS_CODE = 200;//成功的code

    public static String LOAING = "正在加载";
    public static String LOGINING = "正在登录";
    public static String REGISTERING = "正在注册";
    public static String SUBMITING = "正在提交";
    public static String GETING = "正在获取";
    public static String DELETE = "正在删除";
}
