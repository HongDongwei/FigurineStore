package com.codvision.figurinestore.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.codvision.figurinestore.module.bean.User;

/**
 * Created by sxy on 2019/5/9 16:50
 * todo
 */
public class SharedPreferenceUtils {
    private final static String SP_NAME = "sp_cache";
    private static SharedPreferences mPreferences;        // SharedPreferences的实例

    /**
     * @param context
     * @param name
     * @return
     */
    private static SharedPreferences getSharedPreference(Context context, String name) {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * @param context
     * @param user
     */
    public static void putSelfInfo(Context context, User user) {
        SharedPreferences.Editor editor = getSharedPreference(context, user.getClass().getSimpleName()).edit();
        user.putInSP(editor);
        editor.apply();
    }

    public static void clearLoginInfo(Context context) {
        SharedPreferences.Editor editor = getSharedPreference(context, User.class.getSimpleName()).edit();
        editor.clear();
        editor.apply();
    }

    public static User getUser(Context context) {
        SharedPreferences preferences = getSharedPreference(context, User.class.getSimpleName());
        return new User(preferences);
    }

    public static String getImage(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getString("image", null);
    }

    public static String getPhone(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getString("phone", null);
    }

    public static String getAddress(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getString("address", null);
    }
    public static float getBalance(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getFloat("balance", 0);
    }
    public static String getPwd(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getString("password", null);
    }

    public static String getUserName(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getString("username", null);
    }

    public static boolean getUserState(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getBoolean("state", false);
    }

    public static int getUserId(Context context) {
        return getSharedPreference(context, User.class.getSimpleName()).getInt("id", 0);
    }

    private static SharedPreferences getSp(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        }
        return mPreferences;
    }

    /**
     * 通过SP获得boolean类型的数据，没有默认为false
     *
     * @param context : 上下文
     * @param key     : 存储的key
     * @return
     */
    public static boolean getBoolean(Context context, String key) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, false);
    }

    /**
     * 通过SP获得boolean类型的数据，没有默认为false
     *
     * @param context  : 上下文
     * @param key      : 存储的key
     * @param defValue : 默认值
     * @return
     */
    public static boolean getBoolean(Context context, String key, boolean defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getBoolean(key, defValue);
    }

    /**
     * 设置int的缓存数据
     *
     * @param context
     * @param key     :缓存对应的key
     * @param value   :缓存对应的值
     */
    public static void setBoolean(Context context, String key, boolean value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.putBoolean(key, value);
        edit.commit();
    }

    public static int getInt(Context context, String key, int defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getInt(key, defValue);
    }

    public static String getString(Context context, String key, String defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getString(key, defValue);
    }

    public static long getLong(Context context, String key, long defValue) {
        SharedPreferences sp = getSp(context);
        return sp.getLong(key, 0);
    }

    /**
     * 设置int的缓存数据
     *
     * @param context
     * @param key     :缓存对应的key
     * @param value   :缓存对应的值
     */
    public static void setInt(Context context, String key, int value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.putInt(key, value);
        edit.commit();
    }


    public static void setString(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.putString(key, value);
        edit.commit();
    }

    public static void setLong(Context context, String key, long value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.putLong(key, value);
        edit.commit();
    }


    public static void setInt(Context context, String key, String value) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.putString(key, value);
        edit.commit();
    }


    /**
     * 删除指定key的value
     *
     * @param context
     * @param key
     */
    public static void deleteKeyData(Context context, String key) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.remove(key);
        edit.commit();
    }

    /**
     * 删除全部值
     *
     * @param context
     */
    public static void deleteData(Context context) {
        SharedPreferences sp = getSp(context);
        SharedPreferences.Editor edit = sp.edit();// 获取编辑器
        edit.clear().commit();
    }
}
