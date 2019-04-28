package com.codvision.figurinestore.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codvision.figurinestore.sqlite.bean.Good;
import com.codvision.figurinestore.sqlite.bean.Student;
import com.codvision.figurinestore.sqlite.bean.Class;
import com.codvision.figurinestore.sqlite.bean.User;


import java.util.ArrayList;
import java.util.List;

public class DBServer {
    private DBHelper dbhelper;

    public DBServer(Context context) {
        this.dbhelper = new DBHelper(context);
    }

    /**
     * 加入用户
     *
     * @param entity
     */
    public void addUser(User entity) {

        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[10];
        arrayOfObject[0] = entity.getUserId();
        arrayOfObject[1] = entity.getUserName();
        arrayOfObject[2] = entity.getUserPwd();
        arrayOfObject[3] = entity.getUserPhone();
        arrayOfObject[4] = entity.getUserSign();
        arrayOfObject[5] = entity.getUserPic();
        arrayOfObject[6] = entity.getUserSex();
        arrayOfObject[7] = entity.getUserBirth();
        arrayOfObject[8] = entity.getUserQrcode();
        arrayOfObject[9] = entity.getUserBalance();

        localSQLiteDatabase.execSQL("insert into users(user_id,user_name,user_pwd,user_phone,userp_sign,user_pic,user_sex,user_birth,user_qrcode,user_balance) values(?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 加入用户
     *
     * @param entity
     */
    public void addGood(Good entity) {

        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[10];
        arrayOfObject[0] = entity.getGoodId();
        arrayOfObject[1] = entity.getGoodName();
        arrayOfObject[2] = entity.getGoodPrice();
        arrayOfObject[3] = entity.getGoodPic1();
        arrayOfObject[4] = entity.getGoodPic2();
        arrayOfObject[5] = entity.getGoodPic3();
        arrayOfObject[6] = entity.getGoodChoice();
        arrayOfObject[7] = entity.getGoodSign();
        arrayOfObject[8] = entity.getGoodType();
        arrayOfObject[9] = entity.getGoodTime();

        localSQLiteDatabase.execSQL("insert into goods(good_id,good_name,good_price,good_pic1,good_pic2,good_pic3,good_choice,good_sign,good_type,good_time) values(?,?,?,?,?,?,?,?,?,?)", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 删除一个用户
     *
     * @param user_id
     */
    public void deleteUser(String user_id) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = user_id;
        localSQLiteDatabase.execSQL("delete from users where user_id=?", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 改动用户信息
     *
     * @param entity
     */
    public void updateUserInfo(User entity) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[10];
        arrayOfObject[0] = entity.getUserName();
        arrayOfObject[1] = entity.getUserPwd();
        arrayOfObject[2] = entity.getUserPhone();
        arrayOfObject[3] = entity.getUserSign();
        arrayOfObject[4] = entity.getUserPic();
        arrayOfObject[5] = entity.getUserSex();
        arrayOfObject[6] = entity.getUserBirth();
        arrayOfObject[7] = entity.getUserQrcode();
        arrayOfObject[8] = entity.getUserBalance();
        arrayOfObject[9] = entity.getUserId();
        localSQLiteDatabase.execSQL("update users set user_name=?,user_pwd=?,user_phone=?,userp_sign=?,user_pic=?,user_sex=?,user_birth=?,user_qrcode=?,user_balance=? where user_id=?", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 使用班级编号查找该班级全部学生
     *
     * @param classId
     * @return
     */
    public List<Student> findStudentsByClassId(String classId) {
        List<Student> localArrayList = new ArrayList<Student>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select student_id, student_name ,score  from students  " +
                "where class_id=?  order by score desc", new String[]{classId});

        while (localCursor.moveToNext()) {
            Student temp = new Student();
            temp.setStudentId(localCursor.getString(localCursor.getColumnIndex("student_id")));
            temp.setStudentName(localCursor.getString(localCursor.getColumnIndex("student_name")));
            temp.setScore(localCursor.getString(localCursor.getColumnIndex("score")));
            temp.setClassId(classId);
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 使用班级名查找该班级全部学生
     *
     * @param className
     * @return
     */
    public List<Student> findStudentsByClassName(String className) {
        List<Student> localArrayList = new ArrayList<Student>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select student_id, student_name,score,classes.class_id from students,classes" +
                " where students.class_id=classes.class_id and classes.class_name =?  order by score asc", new String[]{className});

        while (localCursor.moveToNext()) {
            Student temp = new Student();
            temp.setStudentId(localCursor.getString(localCursor.getColumnIndex("student_id")));
            temp.setStudentName(localCursor.getString(localCursor.getColumnIndex("student_name")));
            temp.setScore(localCursor.getString(localCursor.getColumnIndex("score")));
            temp.setClassId(localCursor.getString(3));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }


    /**
     * 查找全部用户
     * * @return
     */
    public List<User> findAllUsers() {
        List<User> localArrayList = new ArrayList<User>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from users " +
                "where 1=1", null);
        while (localCursor.moveToNext()) {
            User temp = new User();
            temp.setUserId(localCursor.getString(localCursor.getColumnIndex("user_id")));
            temp.setUserName(localCursor.getString(localCursor.getColumnIndex("user_name")));
            temp.setUserPwd(localCursor.getString(localCursor.getColumnIndex("user_pwd")));
            temp.setUserPhone(localCursor.getString(localCursor.getColumnIndex("user_phone")));
            temp.setUserSign(localCursor.getString(localCursor.getColumnIndex("userp_sign")));
            temp.setUserPic(localCursor.getString(localCursor.getColumnIndex("user_pic")));
            temp.setUserSex(localCursor.getString(localCursor.getColumnIndex("user_sex")));
            temp.setUserBirth(localCursor.getString(localCursor.getColumnIndex("user_birth")));
            temp.setUserQrcode(localCursor.getString(localCursor.getColumnIndex("user_qrcode")));
            temp.setUserBalance(localCursor.getString(localCursor.getColumnIndex("user_balance")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 查找全部商品
     * * @return
     */
    public List<Good> findAllGoods() {
        List<Good> localArrayList = new ArrayList<Good>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods ", null);
        while (localCursor.moveToNext()) {
            Good temp = new Good();
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 查找全部商品
     * * @return
     */
    public List<Good> findAllGoodsByPrice() {
        List<Good> localArrayList = new ArrayList<Good>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods order by ABS(good_price) ", null);
        while (localCursor.moveToNext()) {
            Good temp = new Good();
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 查找全部商品
     * * @return
     */
    public List<Good> findAllGoodsByChoice() {
        List<Good> localArrayList = new ArrayList<Good>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods order by ABS(good_choice) ", null);
        while (localCursor.moveToNext()) {
            Good temp = new Good();
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 成绩最好
     *
     * @return
     */
    public Student findMaxScoreStudent() {
        Student temp = new Student();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select student_id,student_name,class_id,max(score)  from students  " +
                "where 1=1", null);
        localCursor.moveToFirst();
        temp.setStudentId(localCursor.getString(0));
        temp.setStudentName(localCursor.getString(1));
        temp.setClassId(localCursor.getString(2));
        temp.setScore(localCursor.getString(3));
        return temp;
    }


    /**
     * 确认该用户是否存在
     *
     * @param UserId
     * @return
     */
    public boolean isUserExists(String UserId) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from users  " +
                "where user_id=? ", new String[]{UserId});
        localCursor.moveToFirst();
        if (localCursor.getLong(0) > 0)
            return true;
        else
            return false;
    }

    /**
     * 通过id查找用户
     *
     * @param UserId
     * @return
     */
    public User getUser(String UserId) {
        User temp = new User();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from users  " +
                "where user_id=? ", new String[]{UserId});
        if (localCursor.moveToFirst()) {
            temp.setUserId(localCursor.getString(localCursor.getColumnIndex("user_id")));
            temp.setUserName(localCursor.getString(localCursor.getColumnIndex("user_name")));
            temp.setUserPwd(localCursor.getString(localCursor.getColumnIndex("user_pwd")));
            temp.setUserPhone(localCursor.getString(localCursor.getColumnIndex("user_phone")));
            temp.setUserSign(localCursor.getString(localCursor.getColumnIndex("userp_sign")));
            temp.setUserPic(localCursor.getString(localCursor.getColumnIndex("user_pic")));
            temp.setUserSex(localCursor.getString(localCursor.getColumnIndex("user_sex")));
            temp.setUserBirth(localCursor.getString(localCursor.getColumnIndex("user_birth")));
            temp.setUserQrcode(localCursor.getString(localCursor.getColumnIndex("user_qrcode")));
            temp.setUserBalance(localCursor.getString(localCursor.getColumnIndex("user_balance")));
        }
        return temp;
    }

    /**
     * 通过id查找商品
     *
     * @param goodId
     * @return
     */
    public Good getGoodForId(String goodId) {
        Good temp = new Good();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods  " +
                "where good_id=? ", new String[]{goodId});
        if (localCursor.moveToFirst()) {
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
        }
        return temp;
    }

    /**
     * 通过type查找商品
     *
     * @param goodType
     * @return
     */
    public List<Good> getGoodForType(String goodType) {
        List<Good> localArrayList = new ArrayList<Good>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods  " +
                "where good_type=? ", new String[]{goodType});
        while (localCursor.moveToNext()) {
            Good temp = new Good();
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 通过type查找商品,并排序
     *
     * @param goodType
     * @return
     */
    public List<Good> getGoodForTypeByPrice(String goodType) {
        List<Good> localArrayList = new ArrayList<Good>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods  " +
                "where good_type=? order by ABS(good_price)", new String[]{goodType});
        while (localCursor.moveToNext()) {
            Good temp = new Good();
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 通过type查找商品,并排序
     *
     * @param goodType
     * @return
     */
    public List<Good> getGoodForTypeByChoice(String goodType) {
        List<Good> localArrayList = new ArrayList<Good>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from goods  " +
                "where good_type=? order by ABS(good_choice)", new String[]{goodType});
        while (localCursor.moveToNext()) {
            Good temp = new Good();
            temp.setGoodId(localCursor.getString(localCursor.getColumnIndex("good_id")));
            temp.setGoodName(localCursor.getString(localCursor.getColumnIndex("good_name")));
            temp.setGoodPrice(localCursor.getString(localCursor.getColumnIndex("good_price")));
            temp.setGoodPic1(localCursor.getString(localCursor.getColumnIndex("good_pic1")));
            temp.setGoodPic2(localCursor.getString(localCursor.getColumnIndex("good_pic2")));
            temp.setGoodPic3(localCursor.getString(localCursor.getColumnIndex("good_pic3")));
            temp.setGoodChoice(localCursor.getString(localCursor.getColumnIndex("good_choice")));
            temp.setGoodSign(localCursor.getString(localCursor.getColumnIndex("good_sign")));
            temp.setGoodType(localCursor.getString(localCursor.getColumnIndex("good_type")));
            temp.setGoodTime(localCursor.getString(localCursor.getColumnIndex("good_time")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }

    /**
     * 确认登录结果
     *
     * @param UserId,UserPassword
     * @return
     */
    public boolean isLoginExists(String UserId, String UserPassword) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from users  " +
                "where user_id=? and user_pwd=?", new String[]{UserId, UserPassword});
        localCursor.moveToFirst();
        if (localCursor.getLong(0) > 0)
            return true;
        else
            return false;
    }
}