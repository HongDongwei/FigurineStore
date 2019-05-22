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
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.module.bean.OrderTableRecieve;
import com.codvision.figurinestore.presenter.CommodityGetPresenter;
import com.codvision.figurinestore.presenter.contract.CommodityGetContract;
import com.codvision.figurinestore.ui.adapter.CommodityAdapter;
import com.codvision.figurinestore.utils.NoScrollGridView;
import com.codvision.figurinestore.utils.NormalToolbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsTypeActivity extends AppCompatActivity implements View.OnClickListener, CommodityGetContract.View {
    /**
     * TAG
     */
    public static final String TAG = "GoodsTypeActivity";
    private TextView tvTypeDefault;
    private TextView tvTypePrice;
    private TextView tvTypeChoice;
    private NormalToolbar toolbar;
    private NoScrollGridView gridView;
    private List<Commodity> commodityList = new ArrayList<>();
    private CommodityAdapter commodityAdapter;


    private String goodType;
    private CommodityGetPresenter commodityGetPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_type);
        initGridViewData();
        initView();
        initEvent();
    }

    public void initGridViewData() {
        Intent intent = getIntent();
        goodType = intent.getStringExtra("goodType");

    }

    private void initView() {
        tvTypeDefault = findViewById(R.id.tv_type_default);
        tvTypePrice = findViewById(R.id.tv_type_price);
        tvTypeChoice = findViewById(R.id.tv_type_choice);
        toolbar = findViewById(R.id.toolbar);
        gridView = findViewById(R.id.gridview);
        commodityAdapter = new CommodityAdapter(this, commodityList);
        gridView.setAdapter(commodityAdapter);
        commodityGetPresenter = new CommodityGetPresenter(this, this);
        commodityGetPresenter.getCommodity(new CommodityGet("id"));
    }

    private void initEvent() {
        tvTypeDefault.setOnClickListener(this);
        tvTypePrice.setOnClickListener(this);
        tvTypeChoice.setOnClickListener(this);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("goodId", commodityList.get(position).getId()+"");//设置参数,""
                intent.setClass(GoodsTypeActivity.this, BuyDetailActivity.class);//从哪里跳到哪里
                startActivity(intent);
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
                commodityGetPresenter.getCommodity(new CommodityGet("id"));
                break;
            case R.id.tv_type_price:
                setClick(tvTypePrice);
                commodityGetPresenter.getCommodity(new CommodityGet("price"));
                break;
            case R.id.tv_type_choice:
                commodityGetPresenter.getCommodity(new CommodityGet("choice"));
                setClick(tvTypeChoice);
                break;
            default:
                break;
        }
    }

    @Override
    public void getCommoditySuccess(List<Commodity> commodityList) {
        this.commodityList.clear();
        for (int i = 0; i < commodityList.size(); i++) {
            if (commodityList.get(i).getType().equals(goodType)||goodType.equals("全部")) {
                this.commodityList.add(commodityList.get(i));
            }

        }
        commodityAdapter.notifyDataSetChanged();

    }


    @Override
    public void getCommodityFail(String code, String message) {

    }
}
