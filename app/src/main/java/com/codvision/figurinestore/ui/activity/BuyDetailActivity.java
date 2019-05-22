package com.codvision.figurinestore.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGetById;
import com.codvision.figurinestore.presenter.CommodityGetByIdPresenter;
import com.codvision.figurinestore.presenter.contract.CommodityGetByIdContract;
import com.codvision.figurinestore.utils.GlideImageLoader;
import com.codvision.figurinestore.utils.NormalToolbar;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.List;


public class BuyDetailActivity extends AppCompatActivity implements CommodityGetByIdContract.View {
    /**
     * TAG
     */
    public static final String TAG = "BuyDetailActivity";
    private NormalToolbar toolbar;
    private Banner banner;
    private TextView tvGoodName;
    private TextView tvGoodPrice;
    private TextView tvGoodTime;
    private TextView tvGoodSign;
    private CommodityGetByIdPresenter commodityGetByIdPresenter;
    private List<Integer> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);
        initView();
        initEvent();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        banner = findViewById(R.id.banner);
        tvGoodName = findViewById(R.id.tv_good_name);
        tvGoodPrice = findViewById(R.id.tv_good_price);
        tvGoodTime = findViewById(R.id.tv_good_time);
        tvGoodSign = findViewById(R.id.tv_good_sign);
        commodityGetByIdPresenter = new CommodityGetByIdPresenter(this, this);
        Intent intent = getIntent();
        String NewsID = intent.getStringExtra("goodId");
        commodityGetByIdPresenter.getCommodity(new CommodityGetById(Integer.parseInt(NewsID)));
        toolbar.setTitle("购买页面");
    }

    private void initBanner(Commodity commodity) {
        images.add(getResources().getIdentifier(commodity.getPic1(), "drawable", getPackageName()));
        images.add(getResources().getIdentifier(commodity.getPic2(), "drawable", getPackageName()));
        images.add(getResources().getIdentifier(commodity.getPic3(), "drawable", getPackageName()));
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(8000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }

    private void initEvent() {
        toolbar.mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void getCommoditySuccess(Commodity commodity) {
        initBanner(commodity);
        tvGoodName.setText(commodity.getName());
        tvGoodPrice.setText(commodity.getPrice() + "");
        tvGoodTime.setText(commodity.getTime() + "");
        tvGoodSign.setText(commodity.getSign());
    }

    @Override
    public void getCommodityFail(String code, String message) {

    }
}
