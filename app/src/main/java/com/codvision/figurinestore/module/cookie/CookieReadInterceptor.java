package com.codvision.figurinestore.module.cookie;

import com.codvision.figurinestore.App;
import com.codvision.figurinestore.utils.SharedPreferenceUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sxy on 2019/5/8 13:48
 * todo
 */

public class CookieReadInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Cookie", SharedPreferenceUtils.getString(App.app, "cookiess", ""));
        return chain.proceed(builder.build());
    }
}
