package com.codvision.figurinestore.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.App;
import com.codvision.figurinestore.module.bean.User;
import com.codvision.figurinestore.presenter.UserRegisterPresenter;
import com.codvision.figurinestore.presenter.contract.UserRegisterContract;

public class RegisterActivity extends AppCompatActivity implements UserRegisterContract.View, View.OnClickListener {
    public static final String TAG = "RegisterActivity";
    private UserRegisterPresenter presenter;
    private TextInputLayout inputUser;
    private TextInputLayout inputPassword;
    private TextView cvRegister;
    private TextView tvBack;
    private EditText etUser;
    private EditText etPwd;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initEvent();
    }

    private void initView() {
        inputUser = (TextInputLayout) findViewById(R.id.et_user);
        inputPassword = (TextInputLayout) findViewById(R.id.et_password);

        //有点击事件
        cvRegister = (TextView) findViewById(R.id.tv_register);
        tvBack = (TextView) findViewById(R.id.tv_register_back);

        etUser = inputUser.getEditText();
        etPwd = inputPassword.getEditText();
        presenter = new UserRegisterPresenter(this, this);
    }

    private void initEvent() {
        tvBack.setOnClickListener(this);
        cvRegister.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_register:
                if (TextUtils.isEmpty(etUser.getText())) {
                    inputUser.setError("用户账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etPwd.getText())) {
                    inputPassword.setError("用户密码不能为空");
                    return;
                }
                String name = etUser.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                presenter.register(name, pwd);
                break;
            case R.id.tv_register_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void registerSuccess() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        App.hide();
        finish();
    }

    @Override
    public void registerFail(String code, String message) {
        Log.i(TAG, "loginFail: " + message);
        if ("1002".equals(code)) {
            inputUser.setError("账号已存在，请重新输入");
        } else if ("1003".equals(code)) {
            inputPassword.setError("密码位数必须在8-16位之间，请重新输入");
        }
    }


}
