package com.codvision.figurinestore.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.codvision.figurinestore.App;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.presenter.UserLoginPresenter;
import com.codvision.figurinestore.presenter.contract.UserLoginContract;
import com.codvision.figurinestore.utils.SharedPreferenceUtils;

public class LeadActivity extends AppCompatActivity implements UserLoginContract.View {
    public static final String TAG = "LeadActivity";
    private UserLoginPresenter presenter;
    private Handler handler = new Handler();
    int time = 3;
    private Context context;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        initView();
        initEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initView() {
        context = this;
        tvTime = findViewById(R.id.tv_lead_time);
        tvTime.setText(String.valueOf(time));
        handler = new Handler();
        presenter = new UserLoginPresenter(this, this);

    }

    private void initEvent() {
        handler.postDelayed(runnable, 1);

    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            handler.postDelayed(this, 1000);
            tvTime.setText(String.valueOf(time));
            if (time == 0) {
                if (SharedPreferenceUtils.getUserState(context)) {
                    Intent intent = new Intent(LeadActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(LeadActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                //  presenter.login(SharedPreferenceUtils.getUserName(context), SharedPreferenceUtils.getPwd(context));
            }
        }
    };


    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LeadActivity.this, MainActivity.class);
        startActivity(intent);
        App.hide();
        finish();
    }

    @Override
    public void loginFail(String code, String message) {
        Log.i(TAG, "loginFail: " + message);
        Toast.makeText(LeadActivity.this, "用户还未登录", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(LeadActivity.this, LoginActivity.class);
        startActivity(intent);
        App.hide();
        finish();
    }
}
