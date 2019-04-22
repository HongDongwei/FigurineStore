package com.codvision.figurinestore.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toolbar;

import com.codvision.figurinestore.R;

public class NormalToolbar extends Toolbar {

    public static final String TAG = "MyToolBar";
    //布局
    private LayoutInflater mInflater;
    //右边按钮
    private ImageButton mRightButton;
    //左边按钮
    private ImageButton mLeftButton;
    //标题
    private TextView mTextTitle;

    private View view;

    public NormalToolbar(Context context) {
        this(context, null);
    }

    public NormalToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NormalToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //初始化函数
        initView();

        setContentInsetsRelative(10, 10);

        if (attrs != null) {
            setLeftButtonIcon(R.drawable.white_left);//设置左图标
            //设置点击事件
            setLeftButtonOnClickLinster(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: left ");
                }
            });
            setRightButtonIcon(R.drawable.message);//设置右图标
            //设置点击事件
            setRightButtonOnClickLinster(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(TAG, "onClick: right");

                }
            });
        }
    }

    private void initView() {
        if (view == null) {
            //初始化
            mInflater = LayoutInflater.from(getContext());
            //添加布局文件
            view = mInflater.inflate(R.layout.toolbar, null);
            //绑定控件
            // mEditSearchView = (EditText) view.findViewById(R.id.toolbar_searchview);
            mTextTitle = (TextView) view.findViewById(R.id.toolbar_title);
            mLeftButton = (ImageButton) view.findViewById(R.id.mLeftButton);
            mRightButton = (ImageButton) view.findViewById(R.id.mRightButton);

            LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(view, layoutParams);
        }
    }

    public void setTitle(String title) {
        mTextTitle.setText(title);
    }

    public void setRightButtonIcon(int icon) {

        if (mRightButton != null) {

            mRightButton.setBackgroundResource(icon);
            // mRightButton.setVisibility(VISIBLE);
        }

    }

    public void setLeftButtonIcon(int icon) {

        if (mLeftButton != null) {

            mLeftButton.setBackgroundResource(icon);
            //mLeftButton.setVisibility(VISIBLE);
        }

    }


    //设置右侧按钮监听事件
    public void setRightButtonOnClickLinster(OnClickListener linster) {
        mRightButton.setOnClickListener(linster);
    }


    //设置左侧按钮监听事件
    public void setLeftButtonOnClickLinster(OnClickListener linster) {
        mLeftButton.setOnClickListener(linster);
    }

    public void hideLeftButton() {
        mLeftButton.setVisibility(GONE);
    }

    public void hideRightButton() {
        mRightButton.setVisibility(GONE);
    }

    public void showLeftButton() {
        mLeftButton.setVisibility(VISIBLE);
    }

    public void showRightButton() {
        mRightButton.setVisibility(VISIBLE);
    }
}
