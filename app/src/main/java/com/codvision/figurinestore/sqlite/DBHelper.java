package com.codvision.figurinestore.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {

    public DBHelper(Context context) {
        super(context, "info.db", null, 1);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        String classesSQL = "CREATE TABLE classes(class_id varchar(10) primary key , " +
                "class_name varchar(20))";

        String studentsSQL = "CREATE TABLE students(student_id varchar(10) primary key , " +
                "student_name varchar(20) ,score varchar(4) ,class_id varchar(10), " +
                "foreign key (class_id) references classes(class_id) " +
                "on delete cascade on update cascade )";

        String usersSQL = "CREATE TABLE users(user_id varchar(100) primary key , " +
                "user_name varchar(100) ,user_pwd varchar(100) ,user_phone varchar(100) ,userp_sign varchar(100),user_pic varchar(100),user_sex varchar(100)," +
                "user_birth varchar(100) ,user_qrcode varchar(100) ,user_balance varchar(100))";

        String goodsSQL = "CREATE TABLE goods(good_id varchar(100) primary key , " +
                "good_name varchar(100) ,good_price varchar(100) ,good_pic1 varchar(100) ,good_pic2 varchar(100),good_pic3 varchar(100),good_choice varchar(100)," +
                "good_sign varchar(100) ,good_type varchar(100) ,good_time varchar(100))";

        String ordersSQL = "CREATE TABLE orders(order_id varchar(100) primary key , " +
                "user_id varchar(100) ,good_id varchar(100) ,order_time varchar(100) ,order_state varchar(100))";
//        +
//                "foreign key (user_id) references users(user_id) " +
//                "on delete cascade on update cascade )" +
//                "foreign key (good_id) references goods(good_id) " +
//                "on delete cascade on update cascade )";
        ;


        db.execSQL(classesSQL);
        Log.d("my", "create table classes:" + classesSQL);
        db.execSQL(studentsSQL);
        Log.d("my", "create table students:" + studentsSQL);
        db.execSQL(usersSQL);
        Log.d("my", "create table users:" + usersSQL);
        db.execSQL(goodsSQL);
        Log.d("my", "create table users:" + goodsSQL);
        db.execSQL(ordersSQL);
        Log.d("my", "create table students:" + ordersSQL);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }

}