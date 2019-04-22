package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.codvision.figurinestore.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class UserFragment extends Fragment {
    private ImageView ivBg;
    private ImageView ivHead;
    private ImageView ivHeadChoice;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initEvent();
        return view;
    }

    private void initView() {
        ivBg = view.findViewById(R.id.iv_user_bg);
        ivHead = view.findViewById(R.id.iv_user_head);
        ivHeadChoice = view.findViewById(R.id.iv_head_change);
    }

    private void initEvent() {
        Glide.with(getActivity()).load(R.drawable.head)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(ivBg);
        //设置圆形图像
        Glide.with(getActivity()).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(ivHead);
        //设置圆形图像
        Glide.with(getActivity()).load(R.drawable.head)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(ivHeadChoice);
    }
}
