package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.adapter.ViewPagerAdapter;
import com.codvision.figurinestore.ui.activity.BuyDetailActivity;
import com.codvision.figurinestore.ui.activity.MainActivity;
import com.codvision.figurinestore.ui.activity.TextActivity;
import com.codvision.figurinestore.utils.NoScrollGridView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class HomeFragment extends Fragment implements View.OnClickListener {
    /**
     * TAG
     */
    public static final String TAG = "HomeFragment";

    private View view;

    private LinearLayout llHomeSearch;
    private ViewPager mViewPager;
    private TextView mTvPagerTitle;
    private List<ImageView> mImageList;//轮播的图片集合
    private String[] mImageTitles;//标题集合
    private int previousPosition = 0;//前一个被选中的position
    private boolean isStop = false;//线程是否停止
    private static int PAGER_TIOME = 5000;//间隔时间
    private TextView tvHomeBanner1;
    private TextView tvHomeBanner2;
    private TextView tvHomeBanner3;
    private TextView tvHomeBanner4;

    private NoScrollGridView gridView;
    private List<Map<String, Object>> dataList;
    private SimpleAdapter adapter;

    // 在values文件假下创建了pager_image_ids.xml文件，并定义了4张轮播图对应的id，用于点击事件
    private int[] imgae_ids = new int[]{R.id.pager_image1, R.id.pager_image2, R.id.pager_image3, R.id.pager_image4};
    private String[] from = {"img", "text", "price"};
    private int[] to = {R.id.img, R.id.text, R.id.price};
    //图标下的文字
    private String name[] = {"CAPCOM 怪物猎人 钢龙", "信号", "宝箱", "秒钟", "大象", "FF", "记事本", "书签", "印象", "商店", "主题", "迅雷"};

    @Nullable
    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fagment_home, container, false);
        initView();
        initEvent();
        return view;
    }

    private void initView() {
        llHomeSearch = view.findViewById(R.id.ll_home_search);
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        mTvPagerTitle = (TextView) view.findViewById(R.id.tv_pager_title);
        tvHomeBanner1 = (TextView) view.findViewById(R.id.tv_home_banner1);
        tvHomeBanner2 = (TextView) view.findViewById(R.id.tv_home_banner2);
        tvHomeBanner3 = (TextView) view.findViewById(R.id.tv_home_banner3);
        tvHomeBanner4 = (TextView) view.findViewById(R.id.tv_home_banner4);
        setClick(tvHomeBanner1);
        gridView = (NoScrollGridView) view.findViewById(R.id.gridview);
        initGridViewData();
        adapter = new SimpleAdapter(getActivity(), dataList, R.layout.gridview_item, from, to);
        gridView.setAdapter(adapter);
        initViewPageData();//初始化数据
        initViewPage();//初始化View，设置适配器
    }

    private void initEvent() {
        llHomeSearch.setOnClickListener(this);
        tvHomeBanner1.setOnClickListener(this);
        tvHomeBanner2.setOnClickListener(this);
        tvHomeBanner3.setOnClickListener(this);
        tvHomeBanner4.setOnClickListener(this);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("newsid", name[position]);//设置参数,""
                intent.setClass(getActivity(), BuyDetailActivity.class);//从哪里跳到哪里
                getActivity().startActivity(intent);
            }
        });
    }

    public void initGridViewData() {
        //图标
        int icno[] = {R.drawable.head, R.drawable.head, R.drawable.head,
                R.drawable.head, R.drawable.head, R.drawable.head, R.drawable.head,
                R.drawable.head, R.drawable.head, R.drawable.head, R.drawable.head, R.drawable.head};

        //价格
        String price[] = {"$99", "$99", "$99", "$99", "$99", "$99", "$99", "$99", "$99", "$99", "$99", "$99"};
        dataList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < icno.length; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("img", icno[i]);
            map.put("text", name[i]);
            map.put("price", price[i]);
            dataList.add(map);
        }
    }

    /**
     * 第二步、初始化数据（图片、标题、点击事件）
     */
    public void initViewPageData() {
        //初始化标题列表和图片
        mImageTitles = new String[]{"这是一个好看的标题1", "这是一个优美的标题2", "这是一个快乐的标题3", "这是一个开心的标题4"};
        int[] imageRess = new int[]{R.drawable.pager_image1, R.drawable.pager_image1, R.drawable.pager_image1, R.drawable.pager_image1};

        //添加图片到图片列表里
        mImageList = new ArrayList<>();
        ImageView iv;
        for (int i = 0; i < imageRess.length; i++) {
            iv = new ImageView(getActivity());
            iv.setBackgroundResource(imageRess[i]);//设置图片
            iv.setId(imgae_ids[i]);//顺便给图片设置id
            iv.setOnClickListener(new pagerImageOnClick());//设置图片点击事件
            mImageList.add(iv);
        }
    }

    //图片点击事件
    private class pagerImageOnClick implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.pager_image1:
                    Toast.makeText(getActivity(), "图片1被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image2:
                    Toast.makeText(getActivity(), "图片2被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image3:
                    Toast.makeText(getActivity(), "图片3被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image4:
                    Toast.makeText(getActivity(), "图片4被点击", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 第三步、给PagerViw设置适配器，并实现自动轮播功能
     */
    public void initViewPage() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mImageList, mViewPager);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //伪无限循环，滑到最后一张图片又从新进入第一张图片
                int newPosition = position % mImageList.size();
                // 把当前选中的点给切换了, 还有描述信息也切换
                mTvPagerTitle.setText(mImageTitles[newPosition]);//图片下面设置显示文本

                // 把当前的索引赋值给前一个索引变量, 方便下一次再切换.
                previousPosition = newPosition;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setFirstLocation();
    }

    /**
     * 第四步：设置刚打开app时显示的图片和文字
     */
    private void setFirstLocation() {
        mTvPagerTitle.setText(mImageTitles[previousPosition]);
        // 把ViewPager设置为默认选中Integer.MAX_VALUE / t2，从十几亿次开始轮播图片，达到无限循环目的;
        int m = (1000 / 2) % mImageList.size();
        int currentPosition = Integer.MAX_VALUE / 2 - m;
        mViewPager.setCurrentItem(currentPosition);
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
                Intent intent = new Intent(getActivity(), TextActivity.class);
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
        }
    }
}
