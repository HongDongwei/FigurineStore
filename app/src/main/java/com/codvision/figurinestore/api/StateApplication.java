package com.codvision.figurinestore.api;

import android.app.Application;

public class StateApplication extends Application {
    public static final String TAG = "StateApplication";

    public static int LOGINSTATE = 0;
    public static String USER = "";
    public static String PAD = "";

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
