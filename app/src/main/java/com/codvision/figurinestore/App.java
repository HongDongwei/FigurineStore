package com.codvision.figurinestore;

import android.app.Application;
import android.content.Context;

import com.codvision.figurinestore.module.bean.Msg;
import com.codvision.figurinestore.module.cookie.CookieReadInterceptor;
import com.codvision.figurinestore.module.cookie.CookiesSaveInterceptor;
import com.codvision.figurinestore.utils.InterceptorUtil;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.apache.mina.core.session.IoSession;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class App extends Application {
    public static final String TAG = "App";
    public static App app;
    public static final int TIMEOUT = 60;
    private static OkHttpClient mOkHttpClient;
    public static int LOGINSTATE = 0;
    public static String USER = "";
    public static String PAD = "";
    public static String MESSAGE = "";
    public static KProgressHUD kProgressHUD;
    public static IoSession session = null;

    public static ArrayList<Msg> msgArrayList = new ArrayList<>();
    public static int fragment = 1;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=5cc3450e");
    }

    /**
     * 全局httpclient
     *
     * @return
     */
    public static OkHttpClient initOKHttp() {
        if (mOkHttpClient == null) {
            mOkHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(TIMEOUT, TimeUnit.SECONDS)//设置连接超时时间
                    .readTimeout(TIMEOUT, TimeUnit.SECONDS)//设置读取超时时间
                    .writeTimeout(TIMEOUT, TimeUnit.SECONDS)//设置写入超时时间
                    .addInterceptor(InterceptorUtil.LogInterceptor())//添加日志拦截器
                    //cookie
                    .addInterceptor(new CookieReadInterceptor())
                    .addInterceptor(new CookiesSaveInterceptor())
                    .build();
        }
        return mOkHttpClient;
    }

    /**
     * 显示加载HUD 需要手动取消
     *
     * @param context 上下文
     * @return KProgressHUD
     */
    public static KProgressHUD show(Context context) {
        kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();
        return kProgressHUD;
    }

    /**
     * @return KProgressHUD
     */
    public static void hide() {
        if (kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }
    }

    /**
     * 显示带文字的加载HUD 需要手动取消
     *
     * @param context   上下文
     * @param tipString 提示文字
     * @return KProgressHUD
     */
    public static KProgressHUD show(Context context, String tipString) {
        kProgressHUD = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(tipString)
                .setCancellable(true)
                .setDimAmount(0.5f)
                .show();
        return kProgressHUD;
    }
}
