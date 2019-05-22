package com.codvision.figurinestore.module.bean;

import android.content.SharedPreferences;

import java.io.Serializable;

/**
 * HongDongwei
 */
public class User implements Serializable {

    private int id;

    private String username;

    private String password;

    private String phone;

    private String sign;

    private String image;

    private int gender;

    private float balance;

    private String address;

    public User(int id, String username, String password, String phone, String sign, String image, int gender, float balance, String address) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.sign = sign;
        this.image = image;
        this.gender = gender;
        this.balance = balance;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void putInSP(SharedPreferences.Editor editor) {
        editor.putInt("id", id);
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putString("phone", phone);
        editor.putString("sign", sign);
        editor.putString("image", image);
        editor.putInt("gender", gender);
        editor.putFloat("balance", balance);
        editor.putString("address", address);
    }

    public User(SharedPreferences preferences) {
        id = preferences.getInt("id", 0);
        username = preferences.getString("username", "");
        password = preferences.getString("password", "");
        phone = preferences.getString("phone", "您还没有电话");
        sign = preferences.getString("sign", "您还没有签名");
        image = preferences.getString("image", "");
        gender = preferences.getInt("gender", 0);
        balance = preferences.getFloat("balance", 0);
        address = preferences.getString("address", "您还没有地址");
    }

    public User(UserLogin userLogin, String pwd, boolean state) {
        this.id = userLogin.getId();
        this.username = userLogin.getName();
        this.password = pwd;
        this.phone = userLogin.getPhone();
        this.image = userLogin.getImage();
        this.gender = userLogin.getGender();
        this.sign = userLogin.getSign();
        this.balance = userLogin.getBalance();
        this.address = userLogin.getAddress();
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
