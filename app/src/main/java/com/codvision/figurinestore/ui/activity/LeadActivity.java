package com.codvision.figurinestore.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.api.StateApplication;
import com.codvision.figurinestore.sqlite.DBServer;
import com.codvision.figurinestore.sqlite.bean.Good;

public class LeadActivity extends Activity {
    int time = 3;
    private TextView textView;
    SharedPreferences sp = null;
    private Good good;
    private DBServer db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        textView = findViewById(R.id.tv_lead_time);
        db = new DBServer(this);
        sp = this.getSharedPreferences("userinfo", Context.MODE_PRIVATE);
        if (sp.getString("init", null) == null) {
            initGood1();
            db.addGood(good);
            initGood2();
            db.addGood(good);
            initGood3();
            db.addGood(good);
            initGood4();
            db.addGood(good);
            initGood5();
            db.addGood(good);
            initGood6();
            db.addGood(good);
            initGood7();
            db.addGood(good);
            initGood8();
            db.addGood(good);
            initGood9();
            db.addGood(good);
            initGood10();
            db.addGood(good);
            initGood11();
            db.addGood(good);
            initGood12();
            db.addGood(good);
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("init", "true");
            editor.commit();
        }
        handler.postDelayed(runnable, 1000);

    }

    private void initGood1() {
        good = new Good();
        good.setGoodId("001");
        good.setGoodName("Max Factory 少女与战车 大吉岭&橙黄白豪套装 figma");
        good.setGoodPrice("629");
        good.setGoodPic1("good1_1");
        good.setGoodPic2("good1_2");
        good.setGoodPic3("good1_3");
        good.setGoodChoice("100");
        good.setGoodSign("你知道这句格言吗？");
        good.setGoodType("手办");
        good.setGoodTime("12h");
    }

    private void initGood2() {
        good = new Good();
        good.setGoodId("002");
        good.setGoodName("擎苍 狐妖小红娘 涂山红红&东方月初 Q版手办组合附独家特典");
        good.setGoodPrice("118");
        good.setGoodPic1("good2_1");
        good.setGoodPic2("good2_2");
        good.setGoodPic3("good2_3");
        good.setGoodChoice("5024");
        good.setGoodSign("我想永远和妖仙姐姐在一起~");
        good.setGoodType("手办");
        good.setGoodTime("12h");
    }

    private void initGood3() {
        good = new Good();
        good.setGoodId("003");
        good.setGoodName("哔哩哔哩 弹幕机甲22号 KT-01 可变形 成品模型");
        good.setGoodPrice("329");
        good.setGoodPic1("good3_1");
        good.setGoodPic2("good3_2");
        good.setGoodPic3("good3_3");
        good.setGoodChoice("54000");
        good.setGoodSign("守护你的热爱（…_…）");
        good.setGoodType("模型");
        good.setGoodTime("12h");
    }

    private void initGood4() {
        good = new Good();
        good.setGoodId("004");
        good.setGoodName("万代HG巴巴托斯高达天狼座拼装模型【机动战士高达铁血的奥尔芬斯】");
        good.setGoodPrice("49");
        good.setGoodPic1("good4_1");
        good.setGoodPic2("good4_2");
        good.setGoodPic3("good4_3");
        good.setGoodChoice("949");
        good.setGoodSign("利用阿赖耶识系统将三日月·奥格斯的感官和巴巴托斯高达动作同步做更进一步调整的产物！");
        good.setGoodType("模型");
        good.setGoodTime("12h");
    }

    private void initGood5() {
        good = new Good();
        good.setGoodId("005");
        good.setGoodName("哔哩哔哩223娘和风樱花折扇双面绢布周边");
        good.setGoodPrice("35");
        good.setGoodPic1("good5_1");
        good.setGoodPic2("good5_2");
        good.setGoodPic3("good5_3");
        good.setGoodChoice("37000");
        good.setGoodSign("嘤嘤嘤，还没有相关的介绍呢~~~");
        good.setGoodType("周边");
        good.setGoodTime("12h");
    }

    private void initGood6() {
        good = new Good();
        good.setGoodId("006");
        good.setGoodName("哔哩哔哩2233猪头系列手机壳 iphone＆安卓款周边");
        good.setGoodPrice("59");
        good.setGoodPic1("good6_1");
        good.setGoodPic2("good6_2");
        good.setGoodPic3("good6_3");
        good.setGoodChoice("150000");
        good.setGoodSign("嘤嘤嘤，还没有相关的介绍呢~~~");
        good.setGoodType("周边");
        good.setGoodTime("12h");
    }

    private void initGood7() {
        good = new Good();
        good.setGoodId("007");
        good.setGoodName("Alphamax绿之空春日野穹和服Ver．手办双版本");
        good.setGoodPrice("1199");
        good.setGoodPic1("good7_1");
        good.setGoodPic2("good7_2");
        good.setGoodPic3("good7_3");
        good.setGoodChoice("35000");
        good.setGoodSign("悠远的天空，在苍穹的尽头。。。");
        good.setGoodType("手办");
        good.setGoodTime("12h");
    }

    private void initGood8() {
        good = new Good();
        good.setGoodId("008");
        good.setGoodName("Furyu Re：从零开始的异世界生活雷姆雪女Ver．景品手办");
        good.setGoodPrice("99");
        good.setGoodPic1("good8_1");
        good.setGoodPic2("good8_2");
        good.setGoodPic3("good8_3");
        good.setGoodChoice("10000");
        good.setGoodSign("可爱的雪女为你带来清涼～！");
        good.setGoodType("手办");
        good.setGoodTime("12h");
    }

    private void initGood9() {
        good = new Good();
        good.setGoodId("009");
        good.setGoodName("万代HG瞬变高达冰川型拼装模型【高达创战者】");
        good.setGoodPrice("99");
        good.setGoodPic1("good9_1");
        good.setGoodPic2("good9_2");
        good.setGoodPic3("good9_3");
        good.setGoodChoice("2818");
        good.setGoodSign("瞬变高达为基础的改造机体登场！");
        good.setGoodType("模型");
        good.setGoodTime("12h");
    }

    private void initGood10() {
        good = new Good();
        good.setGoodId("010");
        good.setGoodName("万代超合金魂真盖塔机器人世界最后之日GX－87盖塔皇帝成品模型");
        good.setGoodPrice("1879");
        good.setGoodPic1("good10_1");
        good.setGoodPic2("good10_2");
        good.setGoodPic3("good10_3");
        good.setGoodChoice("76");
        good.setGoodSign("未来与虫人类作战的时候出现的谜一般的终极巨型盖塔机器人！");
        good.setGoodType("模型");
        good.setGoodTime("12h");
    }

    private void initGood11() {
        good = new Good();
        good.setGoodId("011");
        good.setGoodName("哔哩哔哩X外交官环球萌动万向轮拉杄箱周边");
        good.setGoodPrice("699");
        good.setGoodPic1("good11_1");
        good.setGoodPic2("good11_2");
        good.setGoodPic3("good11_3");
        good.setGoodChoice("5447");
        good.setGoodSign("带小电视一起环球世界");
        good.setGoodType("周边");
        good.setGoodTime("12h");
    }

    private void initGood12() {
        good = new Good();
        good.setGoodId("012");
        good.setGoodName("哔哩哔哩小电视暗纹商务款双肩包周边");
        good.setGoodPrice("399");
        good.setGoodPic1("good12_1");
        good.setGoodPic2("good12_2");
        good.setGoodPic3("good12_3");
        good.setGoodChoice("2583");
        good.setGoodSign("嘤嘤嘤，还没有相关的介绍呢~~~");
        good.setGoodType("周边");
        good.setGoodTime("12h");
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            time--;
            textView.setText(String.valueOf(time));
            handler.postDelayed(this, 1000);
            if (time == 0) {
                Intent intent = new Intent(LeadActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        }
    };
}

