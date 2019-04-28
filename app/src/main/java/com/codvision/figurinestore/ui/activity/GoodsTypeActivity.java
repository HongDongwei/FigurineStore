package com.codvision.figurinestore.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.sqlite.DBServer;
import com.codvision.figurinestore.sqlite.bean.Good;
import com.codvision.figurinestore.utils.NoScrollGridView;
import com.codvision.figurinestore.utils.NormalToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsTypeActivity extends AppCompatActivity implements View.OnClickListener {
    /**
     * TAG
     */
    public static final String TAG = "GoodsTypeActivity";
    private TextView tvTypeDefault;
    private TextView tvTypePrice;
    private TextView tvTypeChoice;
    private NormalToolbar toolbar;
    private NoScrollGridView gridView;
    private List<Map<String, Object>> dataList;
    private List<Good> goodList;
    private DBServer db;
    private String[] from = {"img", "text", "price", "click"};
    private int[] to = {R.id.img, R.id.text, R.id.price, R.id.click};
    private SimpleAdapter adapter;
    private String goodType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_type);
        initGridViewData();
        initView();
        initEvent();
    }

    public void initGridViewData() {
        db = new DBServer(this);
        Intent intent = getIntent();
        goodType = intent.getStringExtra("goodType");
        getGoods(1);
    }

    private void initView() {
        tvTypeDefault = findViewById(R.id.tv_type_default);
        tvTypePrice = findViewById(R.id.tv_type_price);
        tvTypeChoice = findViewById(R.id.tv_type_choice);
        toolbar = findViewById(R.id.toolbar);

    }

    private void initEvent() {
        tvTypeDefault.setOnClickListener(this);
        tvTypePrice.setOnClickListener(this);
        tvTypeChoice.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("goodId", goodList.get(position).getGoodId());//设置参数,""
                intent.setClass(GoodsTypeActivity.this, BuyDetailActivity.class);//从哪里跳到哪里
                GoodsTypeActivity.this.startActivity(intent);
            }
        });
        toolbar.mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //初始化
        setClick(tvTypeDefault);
        toolbar.setTitle(goodType);
    }

    private void getGoods(int sort) {
        switch (sort) {
            case 1:
                if (goodType.equals("全部")) {
                    goodList = db.findAllGoods();
                } else {
                    goodList = db.getGoodForType(goodType);
                }
                break;
            case 2:
                if (goodType.equals("全部")) {
                    goodList = db.findAllGoodsByPrice();
                } else {
                    goodList = db.getGoodForTypeByPrice(goodType);
                }
                break;
            case 3:
                if (goodType.equals("全部")) {
                    goodList = db.findAllGoodsByChoice();
                } else {
                    goodList = db.getGoodForTypeByChoice(goodType);
                }

                break;
            default:
                break;
        }
        Log.i(TAG, "initGridViewData: " + goodList.size());
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < goodList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", getResources().getIdentifier(goodList.get(i).getGoodPic1(), "drawable", getPackageName()));
            map.put("text", goodList.get(i).getGoodName());
            map.put("price", goodList.get(i).getGoodPrice());
            map.put("click", goodList.get(i).getGoodChoice());
            dataList.add(map);
        }
        gridView = findViewById(R.id.gridview);
        adapter = new SimpleAdapter(this, dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter);
    }

    private void setClick(TextView textView) {
        tvTypeDefault.setSelected(false);
        tvTypePrice.setSelected(false);
        tvTypeChoice.setSelected(false);
        textView.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_type_default:
                setClick(tvTypeDefault);
                getGoods(1);
                break;
            case R.id.tv_type_price:
                setClick(tvTypePrice);
                getGoods(2);
                break;
            case R.id.tv_type_choice:
                setClick(tvTypeChoice);
                getGoods(3);
                break;
            default:
                break;
        }
    }
}
