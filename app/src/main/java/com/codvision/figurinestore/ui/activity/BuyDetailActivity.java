package com.codvision.figurinestore.ui.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.codvision.figurinestore.adapter.ViewPagerAdapter;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.ui.fragment.HomeFragment;
import com.codvision.figurinestore.utils.NormalToolbar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BuyDetailActivity extends AppCompatActivity {

    private NormalToolbar toolbar;
    private ViewPager vpBugPlay;
    private String[] from = {"img", "text", "price"};
    private int[] to = {R.id.img, R.id.text, R.id.price};
    private int[] imgae_ids = new int[]{R.id.pager_image1, R.id.pager_image2, R.id.pager_image3, R.id.pager_image4};
    private TextView mTvPagerTitle;
    private List<ImageView> mImageList;//轮播的图片集合
    private String[] mImageTitles;//标题集合
    private int previousPosition = 0;//前一个被选中的position


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);
        initView();
        initEvent();

    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        vpBugPlay = findViewById(R.id.vp_bug_play);
        mTvPagerTitle = (TextView) findViewById(R.id.tv_pager_title);
        initViewPageData();//初始化数据
        initViewPage();//初始化View，设置适配器
        Intent intent = getIntent();
        String NewsID = intent.getStringExtra("newsid");
        toolbar.setTitle(NewsID);
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
            iv = new ImageView(BuyDetailActivity.this);
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
                    Toast.makeText(BuyDetailActivity.this, "图片1被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image2:
                    Toast.makeText(BuyDetailActivity.this, "图片2被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image3:
                    Toast.makeText(BuyDetailActivity.this, "图片3被点击", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.pager_image4:
                    Toast.makeText(BuyDetailActivity.this, "图片4被点击", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    /**
     * 第三步、给PagerViw设置适配器，并实现自动轮播功能
     */
    public void initViewPage() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(mImageList, vpBugPlay);
        vpBugPlay.setAdapter(viewPagerAdapter);
        vpBugPlay.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
        vpBugPlay.setCurrentItem(currentPosition);
    }

    private void initEvent() {
        toolbar.mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
