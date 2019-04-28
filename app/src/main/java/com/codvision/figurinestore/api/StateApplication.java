package com.codvision.figurinestore.api;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;

public class StateApplication extends Application {
    public static final String TAG = "StateApplication";

    public static int LOGINSTATE = 0;
    public static String USER = "";
    public static String PAD = "";

    @Override
    public void onCreate() {
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5cc3450e");
        super.onCreate();
    }
}
