package com.codvision.figurinestore.ui.activity;


import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.ui.fragment.HomeFragment;
import com.codvision.figurinestore.ui.fragment.MessageFragment;
import com.codvision.figurinestore.ui.fragment.OrderFragment;
import com.codvision.figurinestore.ui.fragment.UserFragment;
import com.codvision.figurinestore.utils.NormalToolbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "MainActivity";
    private TextView tvHome;
    private TextView tvMessage;
    private TextView tvOrder;
    private TextView tvUser;

    private HomeFragment homeFragment;
    private MessageFragment messageFragment;
    private OrderFragment orderFragment;
    private UserFragment userFragment;
    private FragmentManager fragmentManager;

    private NormalToolbar toolbar;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initWidget();
        intiEvent();
    }

    private void initWidget() {
        tvHome = (TextView) findViewById(R.id.tv_home);
        tvMessage = (TextView) findViewById(R.id.tv_message);
        tvOrder = (TextView) findViewById(R.id.tv_order);
        tvUser = (TextView) findViewById(R.id.tv_user);
        homeFragment = new HomeFragment();
        messageFragment = new MessageFragment();
        orderFragment = new OrderFragment();
        userFragment = new UserFragment();
        fragmentManager = getFragmentManager();
        toolbar = findViewById(R.id.toolbar);
    }

    private void intiEvent() {
        tvHome.setClickable(true);
        tvMessage.setClickable(true);
        tvOrder.setClickable(true);
        tvUser.setClickable(true);

        tvHome.setOnClickListener(this);
        tvMessage.setOnClickListener(this);
        tvOrder.setOnClickListener(this);
        tvUser.setOnClickListener(this);


        //首页
        fragmentManager.beginTransaction().replace(R.id.main_fragment, homeFragment).commit();
        setClick(tvHome);

        toolbar.setTitle("首页");
        toolbar.hideLeftButton();
        sp = getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("state", "true");
        editor.commit();
        Log.i(TAG, "uname: " + sp.getString("uname", null));
        Log.i(TAG, "upswd: " + sp.getString("upswd", null));
        Log.i(TAG, "state: " + sp.getString("state", null));
    }

    private void setClick(TextView textView) {
        tvHome.setSelected(false);
        tvMessage.setSelected(false);
        tvOrder.setSelected(false);
        tvUser.setSelected(false);

        Drawable iconUser = getResources().getDrawable(R.drawable.user_unclick);
        iconUser.setBounds(0, 0, 80, 80);
        tvUser.setCompoundDrawables(null, iconUser, null, null);
        Drawable iconMessage = getResources().getDrawable(R.drawable.message_unclick);
        iconMessage.setBounds(0, 0, 80, 80);
        tvMessage.setCompoundDrawables(null, iconMessage, null, null);
        Drawable iconOrder = getResources().getDrawable(R.drawable.order_unclick);
        iconOrder.setBounds(0, 0, 80, 80);
        tvOrder.setCompoundDrawables(null, iconOrder, null, null);
        Drawable iconHome = getResources().getDrawable(R.drawable.home_unclick);
        iconHome.setBounds(0, 0, 80, 80);
        tvHome.setCompoundDrawables(null, iconHome, null, null);

        switch (textView.getId()) {
            case R.id.tv_home:
                iconHome = getResources().getDrawable(R.drawable.home_click);
                iconHome.setBounds(0, 0, 80, 80);
                tvHome.setCompoundDrawables(null, iconHome, null, null);

                break;
            case R.id.tv_message:
                iconMessage = getResources().getDrawable(R.drawable.message_click);
                iconMessage.setBounds(0, 0, 80, 80);
                tvMessage.setCompoundDrawables(null, iconMessage, null, null);
                break;
            case R.id.tv_order:
                iconOrder = getResources().getDrawable(R.drawable.order_click);
                iconOrder.setBounds(0, 0, 80, 80);
                tvOrder.setCompoundDrawables(null, iconOrder, null, null);
                break;
            case R.id.tv_user:
                iconUser = getResources().getDrawable(R.drawable.user_click);
                iconUser.setBounds(0, 0, 80, 80);
                tvUser.setCompoundDrawables(null, iconUser, null, null);
                break;
        }
        textView.setSelected(true);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_home:
                setClick(tvHome);
                toolbar.setTitle("首页");
                fragmentManager.beginTransaction().replace(R.id.main_fragment, homeFragment).commit();
                break;
            case R.id.tv_message:
                setClick(tvMessage);
                toolbar.setTitle("消息");
                fragmentManager.beginTransaction().replace(R.id.main_fragment, messageFragment).commit();
                break;
            case R.id.tv_order:
                setClick(tvOrder);
                toolbar.setTitle("订单");
                fragmentManager.beginTransaction().replace(R.id.main_fragment, orderFragment).commit();
                break;
            case R.id.tv_user:
                setClick(tvUser);
                toolbar.setTitle("用户");
                fragmentManager.beginTransaction().replace(R.id.main_fragment, userFragment).commit();
                break;
            default:
                break;
        }
    }
}