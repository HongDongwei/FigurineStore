package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.adapter.ViewPagerAdapter;
import com.codvision.figurinestore.sqlite.DBServer;
import com.codvision.figurinestore.sqlite.bean.Good;
import com.codvision.figurinestore.ui.activity.BuyDetailActivity;
import com.codvision.figurinestore.ui.activity.SearchActivity;
import com.codvision.figurinestore.utils.GlideImageLoader;
import com.codvision.figurinestore.utils.NoScrollGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class HomeFragment extends Fragment implements View.OnClickListener {
    /**
     * TAG
     */
    public static final String TAG = "HomeFragment";

    private View view;

    private LinearLayout llHomeSearch;

    private TextView tvHomeBanner1;
    private TextView tvHomeBanner2;
    private TextView tvHomeBanner3;
    private TextView tvHomeBanner4;

    private ImageView ivHomeFigurine;
    private ImageView ivHomeModel;
    private ImageView ivHomePeriphery;
    private ImageView ivHomeAll;

    private NoScrollGridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;
    private DBServer db;
    private List<Good> goodList;

    private String[] from = {"img", "text", "price"};
    private int[] to = {R.id.img, R.id.text, R.id.price};
    private List<Integer> images = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    private Banner bannerHome;

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_home, container, false);
        initGridViewData();
        initView();
        initBannner();
        initEvent();
        return view;
    }

    private void initBannner() {
        images.add(R.drawable.bannner1);
        images.add(R.drawable.bannner2);
        images.add(R.drawable.bannner3);
        titles.add("可爱小园丁！");
        titles.add("新英雄咒术师手办上架！！！");
        titles.add("厂长紫皮限量售卖！");

        //设置banner样式
        bannerHome.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        bannerHome.setImageLoader(new GlideImageLoader());
        //设置图片集合
        bannerHome.setImages(images);
        //设置banner动画效果
        bannerHome.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        bannerHome.setBannerTitles(titles);
        //设置自动轮播，默认为true
        bannerHome.isAutoPlay(true);
        //设置轮播时间
        bannerHome.setDelayTime(8000);
        //设置指示器位置（当banner模式中有指示器时）
        bannerHome.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        bannerHome.start();
    }

    private void initView() {
        llHomeSearch = view.findViewById(R.id.ll_home_search);
        tvHomeBanner1 = view.findViewById(R.id.tv_home_banner1);
        tvHomeBanner2 = view.findViewById(R.id.tv_home_banner2);
        tvHomeBanner3 = view.findViewById(R.id.tv_home_banner3);
        tvHomeBanner4 = view.findViewById(R.id.tv_home_banner4);
        ivHomeFigurine = view.findViewById(R.id.iv_home_figurine);
        ivHomeModel = view.findViewById(R.id.iv_home_model);
        ivHomePeriphery = view.findViewById(R.id.iv_home_periphery);
        ivHomeAll = view.findViewById(R.id.iv_home_all);
        gridView = view.findViewById(R.id.gridview);
        bannerHome = view.findViewById(R.id.banner_home);
        adapter = new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter);

        changeHeadPic(R.drawable.head1, ivHomeFigurine);
        changeHeadPic(R.drawable.head2, ivHomeModel);
        changeHeadPic(R.drawable.head3, ivHomePeriphery);
        changeHeadPic(R.drawable.head4, ivHomeAll);
        setClick(tvHomeBanner1);
    }

    private void initEvent() {
        llHomeSearch.setOnClickListener(this);
        tvHomeBanner1.setOnClickListener(this);
        tvHomeBanner2.setOnClickListener(this);
        tvHomeBanner3.setOnClickListener(this);
        tvHomeBanner4.setOnClickListener(this);
        ivHomeFigurine.setOnClickListener(this);
        ivHomeModel.setOnClickListener(this);
        ivHomePeriphery.setOnClickListener(this);
        ivHomeAll.setOnClickListener(this);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("goodId", goodList.get(position).getGoodId());//设置参数,""
                intent.setClass(getActivity(), BuyDetailActivity.class);//从哪里跳到哪里
                getActivity().startActivity(intent);
            }
        });
    }

    public void initGridViewData() {
        db = new DBServer(getActivity());
        goodList = db.findAllGoods();
        Log.i(TAG, "initGridViewData: " + goodList.size());
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < goodList.size(); i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", getResources().getIdentifier(goodList.get(i).getGoodPic1(), "drawable", getActivity().getPackageName()));
            map.put("text", goodList.get(i).getGoodName());
            map.put("price", goodList.get(i).getGoodPrice());
            dataList.add(map);
        }
    }


    private void setClick(TextView textView) {
        tvHomeBanner1.setSelected(false);
        tvHomeBanner2.setSelected(false);
        tvHomeBanner3.setSelected(false);
        tvHomeBanner4.setSelected(false);
        textView.setSelected(true);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_home_search:
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_home_banner1:
                setClick(tvHomeBanner1);
                break;
            case R.id.tv_home_banner2:
                setClick(tvHomeBanner2);
                break;
            case R.id.tv_home_banner3:
                setClick(tvHomeBanner3);
                break;
            case R.id.tv_home_banner4:
                setClick(tvHomeBanner4);
                break;
            case R.id.iv_home_figurine:

                break;
            case R.id.iv_home_model:

                break;
            case R.id.iv_home_periphery:

                break;
            case R.id.iv_home_all:

                break;
        }
    }

    private void changeHeadPic(Object src, ImageView imageView) {
        //设置圆形图像
        Glide.with(getActivity()).load(src)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(imageView);
    }
}
