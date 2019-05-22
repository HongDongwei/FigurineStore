package com.codvision.figurinestore.ui.activity;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.App;
import com.codvision.figurinestore.presenter.UserLoginPresenter;
import com.codvision.figurinestore.presenter.contract.UserLoginContract;

public class LoginActivity extends AppCompatActivity implements UserLoginContract.View, View.OnClickListener {
    public static final String TAG = "LoginActivity";
    private UserLoginPresenter presenter;
    private TextInputLayout inputUser;
    private TextInputLayout inputPassword;
    private TextView tvRegister;
    private ImageButton ibfinish;
    private TextView cvLogin;
    private EditText etUser;
    private EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initEvent();
    }

    private void initView() {
        inputUser = (TextInputLayout) findViewById(R.id.et_user);
        inputPassword = (TextInputLayout) findViewById(R.id.et_password);
        cvLogin = (TextView) findViewById(R.id.tv_login);
        tvRegister = findViewById(R.id.tv_login_register);
        ibfinish = findViewById(R.id.ib_login_finish);
        etUser = inputUser.getEditText();
        etPwd = inputPassword.getEditText();
        presenter = new UserLoginPresenter(this, this);
    }

    private void initEvent() {
        tvRegister.setOnClickListener(this);
        ibfinish.setOnClickListener(this);
        cvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                // 清空错误提示
                etUser.setError(null);
                etPwd.setError(null);
                String name = etUser.getText().toString().trim();
                String pwd = etPwd.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    inputUser.setError("用户账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(pwd)) {
                    inputPassword.setError("用户密码不能为空");
                    return;
                }
                presenter.login(name, pwd);
                break;
            case R.id.tv_login_register:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_login_finish:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void loginSuccess() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        App.hide();
        finish();
    }

    @Override
    public void loginFail(String code, String message) {
        Log.i(TAG, "loginFail: " + message);
        if ("1004".equals(code)) {
            inputPassword.setError("密码错误，请重新输入");
        } else if ("1005".equals(code)) {
            inputUser.setError("账号错误，请重新输入");
        }

    }


}
