package com.codvision.figurinestore.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.sip.SipProfile;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.api.StateApplication;
import com.codvision.figurinestore.sqlite.DBServer;
import com.codvision.figurinestore.sqlite.bean.Good;
import com.codvision.figurinestore.sqlite.bean.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "LoginActivity";
    private TextInputLayout inputUser;
    private TextInputLayout inputPassword;
    protected SipProfile account = null;
    SharedPreferences sp = null;
    private User user;

    private DBServer db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initVisible();
    }

    private void initVisible() {
        TextView tvLogin = (TextView) findViewById(R.id.tv_login);
        TextView tvRegister = (TextView) findViewById(R.id.tv_register);
        TextView tvDelete = (TextView) findViewById(R.id.tv_delete);
        inputUser = (TextInputLayout) findViewById(R.id.et_user);
        inputPassword = (TextInputLayout) findViewById(R.id.et_password);

        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if (sp.getString("state", null) != null) {
            StateApplication.USER = sp.getString("uname", null);
            StateApplication.PAD = sp.getString("upswd", null);
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }


        inputUser.getEditText().setText(sp.getString("uname", null));
        inputPassword.getEditText().setText(sp.getString("upswd", null));

        final EditText etUser = inputUser.getEditText();
        final EditText etPwd = inputPassword.getEditText();
        Drawable iconUser = getResources().getDrawable(R.drawable.icon_user);
        iconUser.setBounds(0, 0, 80, 80);
        etUser.setCompoundDrawables(iconUser, null, null, null);
        Drawable iconPassword = getResources().getDrawable(R.drawable.icon_password);
        iconPassword.setBounds(0, 0, 80, 80);
        etPwd.setCompoundDrawables(iconPassword, null, null, null);
        etUser.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputUser.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etPwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                inputPassword.setError("");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        db = new DBServer(this);

        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etUser.getText())) {
                    inputUser.setError("用户账号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(etPwd.getText())) {
                    inputPassword.setError("用户密码不能为空");
                    return;
                }
                StateApplication.USER = etUser.getText().toString().trim();
                StateApplication.PAD = etPwd.getText().toString().trim();
                SharedPreferences.Editor editor = sp.edit();
                Log.d(TAG, "USER:" + StateApplication.USER + ";PAD:" + StateApplication.PAD);
                editor.putString("uname", StateApplication.USER);
                editor.putString("upswd", StateApplication.PAD);
                editor.commit();
                if (db.isLoginExists(StateApplication.USER, StateApplication.PAD)) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "账号密码错误", Toast.LENGTH_LONG).show();
                }

            }
        });
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!db.isUserExists(etUser.getText().toString().trim())) {
                    initUser();
                    user.setUserId(etUser.getText().toString().trim());
                    user.setUserPwd(etPwd.getText().toString().trim());
                    db.addUser(user);
                    Toast.makeText(LoginActivity.this, "注册成功！", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(LoginActivity.this, "该账号已被注册！", Toast.LENGTH_LONG).show();
                }
            }
        });
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(etUser.getText())) {
                    inputUser.setError("删除账号不能为空！");
                    return;
                }
                if (!db.isUserExists(etUser.getText().toString().trim())) {
                    Toast.makeText(LoginActivity.this, "删除失败，数据库中没有该用户！", Toast.LENGTH_LONG).show();
                    return;
                }
                db.deleteUser(etUser.getText().toString().trim());
                Toast.makeText(LoginActivity.this, "删除成功！", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initUser() {
        user = new User();
//        user.setUserName("");
//        user.setUserPhone("");
//        user.setUserSign("");
//        user.setUserPic("");
//        user.setUserSex("");
//        user.setUserBirth("");
//        user.setUserQrcode("");
//        user.setUserBalance("");
        user.setUserName("暂无");
        user.setUserPhone("暂无");
        user.setUserSign("这个人很懒，什么都没有留下！");
        user.setUserPic("");
        user.setUserSex("保密");
        user.setUserBirth("2000-01-01");
        user.setUserQrcode("");
        user.setUserBalance("0");
    }


}
