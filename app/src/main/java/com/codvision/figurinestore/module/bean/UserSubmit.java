package com.codvision.figurinestore.module.bean;

/**
 * Created by sxy on 2019/5/11 15:56
 * todo
 */
public class UserSubmit {


    private int id;

    private String password;

    private String phone;

    private String sign;

    private String image;

    private int gender;

    private float balance;

    private String address;

    public UserSubmit(User user) {
        this.id = user.getId();
        this.password = user.getPassword();
        this.phone = user.getPhone();
        this.image = user.getImage();
        this.gender = user.getGender();
        this.sign = user.getSign();
        this.balance = user.getBalance();
        this.address = user.getAddress();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
