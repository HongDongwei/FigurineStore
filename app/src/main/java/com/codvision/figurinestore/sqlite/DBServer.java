package com.codvision.figurinestore.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.codvision.figurinestore.sqlite.bean.Student;
import com.codvision.figurinestore.sqlite.bean.Class;


import java.util.ArrayList;
import java.util.List;

public class DBServer {
    private DBHelper dbhelper;

    public DBServer(Context context) {
        this.dbhelper = new DBHelper(context);
    }

    /**
     * 加入班级
     *
     * @param entity
     */
    public void addClass(Class entity) {

        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = entity.getClassId();
        arrayOfObject[1] = entity.getClassName();
        localSQLiteDatabase.execSQL("insert into classes(class_id,class_name) values(?,?)", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 加入学生
     *
     * @param entity
     */
    public void addStudent(Student entity) {

        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[4];
        arrayOfObject[0] = entity.getStudentId();
        arrayOfObject[1] = entity.getStudentName();
        arrayOfObject[2] = entity.getScore();
        arrayOfObject[3] = entity.getClassId();
        localSQLiteDatabase.execSQL("insert into students(student_id,student_name,score,class_id) values(?,?,?,?)", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 删除一个班级
     * 同一时候会删除students中该班级的学生
     *
     * @param class_id
     */
    public void deleteClass(String class_id) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        //设置了级联删除和级联更新
        //在运行有级联关系的语句的时候必须先设置“PRAGMA foreign_keys=ON”
        //否则级联关系默认失效
        localSQLiteDatabase.execSQL("PRAGMA foreign_keys=ON");
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = class_id;
        localSQLiteDatabase.execSQL("delete from classes where class_id=?", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 删除一个学生
     *
     * @param student_id
     */
    public void deleteStudent(String student_id) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[1];
        arrayOfObject[0] = student_id;
        localSQLiteDatabase.execSQL("delete from students where student_id=?", arrayOfObject);
        localSQLiteDatabase.close();
    }

    /**
     * 改动学生信息
     *
     * @param entity
     */
    public void updateStudentInfo(Student entity) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Object[] arrayOfObject = new Object[4];

        arrayOfObject[0] = entity.getStudentName();
        arrayOfObject[1] = entity.getScore();
        arrayOfObject[2] = entity.getClassId();
        arrayOfObject[3] = entity.getStudentId();

        localSQLiteDatabase.execSQL("update students set student_name=?,score=?,class_id=?  where student_id=?", arrayOfObject);
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
     * 查找全部学生
     *
     * @param className
     * @return
     */
    public List<Student> findAllStudents() {
        List<Student> localArrayList = new ArrayList<Student>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from students " +
                "where 1=1  order by score desc ", null);
        while (localCursor.moveToNext()) {
            Student temp = new Student();
            temp.setStudentId(localCursor.getString(localCursor.getColumnIndex("student_id")));
            temp.setStudentName(localCursor.getString(localCursor.getColumnIndex("student_name")));
            temp.setScore(localCursor.getString(localCursor.getColumnIndex("score")));
            temp.setClassId(localCursor.getString(localCursor.getColumnIndex("class_id")));
            localArrayList.add(temp);
        }
        localSQLiteDatabase.close();
        return localArrayList;
    }


    /**
     * 取得全部班级
     *
     * @return
     */
    public List<Class> findAllClasses() {
        List<Class> localArrayList = new ArrayList<Class>();
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select * from classes " +
                "where 1=1", null);
        while (localCursor.moveToNext()) {
            Class temp = new Class();
            temp.setClassId(localCursor.getString(localCursor.getColumnIndex("class_id")));
            temp.setClassName(localCursor.getString(localCursor.getColumnIndex("class_name")));
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
     * 查找是否有该学生
     *
     * @param studentId
     * @return
     */
    public boolean isStudentsExists(String studentId) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from students  " +
                "where student_id=?", new String[]{studentId});
        localCursor.moveToFirst();
        if (localCursor.getLong(0) > 0)
            return true;
        else
            return false;
    }

    /**
     * 确认该班级是否存在
     *
     * @param classId
     * @return
     */
    public boolean isClassExists(String s) {
        SQLiteDatabase localSQLiteDatabase = this.dbhelper.getWritableDatabase();
        Cursor localCursor = localSQLiteDatabase.rawQuery("select count(*)  from classes  " +
                "where class_id=? or class_name=?", new String[]{s, s});
        localCursor.moveToFirst();
        if (localCursor.getLong(0) > 0)
            return true;
        else
            return false;
    }

}