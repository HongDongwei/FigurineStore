package com.codvision.figurinestore.ui.activity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.sqlite.DBServer;
import com.codvision.figurinestore.sqlite.bean.Student;
import com.codvision.figurinestore.sqlite.bean.Class;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TextActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Class> classData = new ArrayList<Class>();
    private List<Student> studentsData = new ArrayList<Student>();
    private static final String className = "A/B/C/D/E";
    private static final String studentName = "彭大/黄二/张三/李四/王五/郑六/田七/周八/叶九/孔十/萧十一";
    private DBServer db;
    private SharedPreferences share;
    private SharedPreferences.Editor editor;
    private String info = "";
    private EditText editText;
    private Button b, b1, b2, b3, b4, b5, b6;
    private EditText sId, sName, score, cId, cName;

    private Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            if (msg.what == 0) {
                sId.setText("");
                sName.setText("");
                score.setText("");
                cName.setText("");
                cId.setText("");
            } else if (msg.what == 1) {
                db.deleteClass((String) msg.obj);
                info += "删除一个班级及班级里面的学生：班级Id:" + (String) msg.obj;
                editText.setText(info);
            }

        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text);
        initView();
        share = getSharedPreferences("DatabaseDamo", 0);
        editor = share.edit();
        db = new DBServer(this);
        if (share.getInt("times", 0) == 0) {
            initDatabase();
            editor.putInt("times", 1);
            editor.commit();
        }
    }

    private void initView() {
        editText = (EditText) findViewById(R.id.info);
        sId = (EditText) findViewById(R.id.studentId);
        sName = (EditText) findViewById(R.id.studentName);
        score = (EditText) findViewById(R.id.score);
        cId = (EditText) findViewById(R.id.classId);
        cName = (EditText) findViewById(R.id.className);
        b = (Button) findViewById(R.id.button);
        b1 = (Button) findViewById(R.id.button1);
        b2 = (Button) findViewById(R.id.button2);
        b3 = (Button) findViewById(R.id.button3);
        b4 = (Button) findViewById(R.id.button4);
        b5 = (Button) findViewById(R.id.button5);
        b6 = (Button) findViewById(R.id.button6);
        b.setOnClickListener(this);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        b3.setOnClickListener(this);
        b4.setOnClickListener(this);
        b5.setOnClickListener(this);
        b6.setOnClickListener(this);


    }

    private void initDatabase() {
        info = "";
        editText.setText("");
        String[] classTemp = className.split("/");
        Class c;
        for (int i = 0; i < classTemp.length; i++) {
            c = new Class();
            c.setClassName(classTemp[i]);
            c.setClassId("00" + i);
            db.addClass(c);
            info += '\n' + "add to database classes:" + c.toString();
        }
        String[] studentTemp = studentName.split("/");
        Student s;
        for (int j = 0; j < studentTemp.length; j++) {
            s = new Student();
            s.setStudentName(studentTemp[j]);
            s.setStudentId("2014050" + j);
            s.setClassId("00" + new Random().nextInt(classTemp.length));
            s.setScore(String.valueOf(new Random().nextInt(100) + 1));
            db.addStudent(s);
            info += '\n' + "add to database students:" + '\n' + s.toString();
        }
        editText.setText(info);
    }

    private void addAStudent() {
        info = "";
        editText.setText("");
        String tempSID = sId.getText().toString();
        String tempSName = sName.getText().toString();
        String tempScore = score.getText().toString();
        String tempCID = cId.getText().toString();
        if (checkInfo(tempSID) && checkInfo(tempSName) && checkInfo(tempScore) && checkInfo(tempCID)) {
            Student temp = new Student();
            temp.setStudentId(tempSID);
            temp.setStudentName(tempSName);
            temp.setScore(tempScore);
            temp.setClassId(tempCID);
            db.addStudent(temp);
            info += "add to database students:" + '\n' + temp.toString();
        } else {
            info += "加入一个学生失败：缺少必要信息，请确认studentId,studentName,score,classId的信息是否完整！";
        }
        editText.setText(info);
    }


    private void deleteAClass() {
        info = "";
        editText.setText("");
        final String tempCID = cId.getText().toString();
        if (checkInfo(tempCID)) {
            if (db.isClassExists(tempCID)) {
                new AlertDialog.Builder(this)
                        .setTitle("提示：")
                        .setMessage("删除一个班级将会删除该班的全部学生信息，确定？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                Message msg = new Message();
                                msg.what = 1;
                                msg.obj = tempCID;
                                hander.sendMessage(msg);
                            }
                        })
                        .setNegativeButton("取消", null)
                        .show();
            } else
                info += "删除一个班级失败：查无此相应班级，请确认classId的信息是否正确！";
        } else {
            info += "删除一个班级失败：缺少必要信息，请确认classId的信息是否完整！";
        }
        editText.setText(info);
    }

    private void deleteAStudent() {
        info = "";
        editText.setText("");
        String tempSID = sId.getText().toString();
        if (checkInfo(tempSID)) {
            if (db.isStudentsExists(tempSID)) {
                db.deleteStudent(tempSID);
                info += "删除一个学生：学生Id:" + tempSID;
            } else
                info += "删除一个学生失败：查无此相应学生，请确认studentId的信息是否正确！";
        } else {
            info += "删除一个学生失败：缺少必要信息，请确认studentId的信息是否完整！";
        }
        editText.setText(info);
    }


    private void updateStudent() {
        info = "";
        editText.setText("");
        String tempSID = sId.getText().toString();
        String tempSName = sName.getText().toString();
        String tempScore = score.getText().toString();
        String tempCID = cId.getText().toString();
        if (checkInfo(tempSID) && checkInfo(tempSName) && checkInfo(tempScore) && checkInfo(tempCID)) {
            if (db.isStudentsExists(tempSID)) {
                Student temp = new Student();
                temp.setStudentId(tempSID);
                temp.setStudentName(tempSName);
                temp.setScore(tempScore);
                temp.setClassId(tempCID);
                db.updateStudentInfo(temp);
                info += "update database students:" + '\n' + temp.toString();
            } else {
                Student temp = new Student();
                temp.setStudentId(tempSID);
                temp.setStudentName(tempSName);
                temp.setScore(tempScore);
                temp.setClassId(tempCID);
                db.addStudent(temp);
                info += "没有找到相应ID的学生，将此学生加入到数据库！" + '\n' +
                        "add to database students:" + '\n' + temp.toString();
            }
        } else {
            info += "更新学生失败：缺少必要信息，请确认studentId,studentName,score,classId的信息是否完整！";
        }
        editText.setText(info);
    }

    /**
     * 打印某班的学生
     */
    private void printStudentsOfClass() {
        info = "";
        editText.setText("");
        String tempCID = cId.getText().toString();
        String tempCName = cName.getText().toString();
        if (checkInfo(tempCID)) {
            if (db.isClassExists(tempCID)) {
                info += "使用ID查询";
                studentsData.clear();
                studentsData = db.findStudentsByClassId(tempCID);
            } else {
                info += "该ID相应的班级不存在";
            }
        } else if (checkInfo(tempCName)) {
            if (db.isClassExists(tempCName)) {
                info += "使用Name查询";
                studentsData.clear();
                studentsData = db.findStudentsByClassName(tempCName);
            } else {
                info += "该Name相应的班级不存在";
            }
        } else {
            studentsData.clear();
            info += "查找学生失败：缺少必要信息，请确认classId或className的信息是否完整！";
        }
        for (int i = 0; i < studentsData.size(); i++) {
            info += '\n' + studentsData.get(i).toString();
        }
        editText.setText(info);
        ;
    }

    private void printMaxScoreStudent() {
        info = "";
        editText.setText("");
        Student temp = db.findMaxScoreStudent();
        info += '\n' + temp.toString();
        editText.setText(info);
        ;
    }


    private void getAllStudent() {
        studentsData.clear();
        studentsData = db.findAllStudents();
        for (int i = 0; i < studentsData.size(); i++) {
            info += '\n' + studentsData.get(i).toString();
        }
    }

    private void getAllClass() {
        classData.clear();
        classData = db.findAllClasses();
        for (int i = 0; i < classData.size(); i++) {
            info += '\n' + classData.get(i).toString();
        }
    }

    private void printAllInfo() {
        info = "";
        editText.setText("");
        getAllStudent();
        getAllClass();
        editText.setText(info);

    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();
        switch (id) {
            case R.id.button:
                printAllInfo();
                break;
            case R.id.button1:
                addAStudent();
                break;
            case R.id.button2:
                deleteAStudent();
                break;
            case R.id.button3:
                deleteAClass();
                break;
            case R.id.button4:
                updateStudent();
                break;
            case R.id.button5:
                printStudentsOfClass();
                break;
            case R.id.button6:
                printMaxScoreStudent();
                break;
        }
        hander.sendEmptyMessageDelayed(0, 5000);
    }

    private boolean checkInfo(String s) {
        if (s.equals("") || s == null)
            return false;
        else
            return true;
    }
}
