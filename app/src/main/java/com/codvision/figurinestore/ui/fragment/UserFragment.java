package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.style.AbsoluteSizeSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.api.StateApplication;
import com.codvision.figurinestore.sqlite.DBServer;
import com.codvision.figurinestore.sqlite.bean.User;
import com.codvision.figurinestore.utils.DateTimeDialogOnlyYMD;

import java.text.SimpleDateFormat;
import java.util.Date;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;


public class UserFragment extends Fragment implements View.OnClickListener, DateTimeDialogOnlyYMD.MyOnDateSetListener {
    public static final String TAG = "UserFragment";
    private View view;
    private ImageView ivBg;
    private ImageView ivHead;
    private ImageView ivHeadChoice;
    private TextView tvSexChange;
    private TextView tvNameChange;
    private TextView tvAgeChange;
    private TextView tvSignChange;
    private TextView tvIdChange;
    private TextView tvMoneyChange;
    private TextView tvUserName;
    private TextView tvUserTel;
    private TextView tvTelChange;
    private Button btLogout;
    private DBServer db;
    private User user;
    private String[] sexArry = new String[]{"保密", "女", "男"};// 性别选择
    private int checkdItem = 0;

    private DateTimeDialogOnlyYMD dateTimeDialogOnlyYM;
    private DateTimeDialogOnlyYMD dateTimeDialogOnlyYMD;
    private DateTimeDialogOnlyYMD dateTimeDialogOnlyYear;
    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SharedPreferences sp;

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
        tvSexChange = view.findViewById(R.id.tv_sex_change);
        tvNameChange = view.findViewById(R.id.tv_name_change);
        tvAgeChange = view.findViewById(R.id.tv_age_change);
        tvSignChange = view.findViewById(R.id.tv_sign_change);
        tvIdChange = view.findViewById(R.id.tv_id_change);
        tvMoneyChange = view.findViewById(R.id.tv_money_change);
        tvUserName = view.findViewById(R.id.tv_user_name);
        tvUserTel = view.findViewById(R.id.tv_user_tel);
        tvTelChange = view.findViewById(R.id.tv_tel_change);
        btLogout = view.findViewById(R.id.bt_logout);

        dateTimeDialogOnlyYMD = new DateTimeDialogOnlyYMD(getActivity(), this, true, true, true);
        dateTimeDialogOnlyYM = new DateTimeDialogOnlyYMD(getActivity(), this, false, true, true);
        db = new DBServer(getActivity());
        user = db.getUser(StateApplication.USER);

    }


    private void initEvent() {
        ivHeadChoice.setOnClickListener(this);
        tvSexChange.setOnClickListener(this);
        tvNameChange.setOnClickListener(this);
        tvAgeChange.setOnClickListener(this);
        tvSignChange.setOnClickListener(this);
        tvTelChange.setOnClickListener(this);
        btLogout.setOnClickListener(this);
        //初始化加载
        changeHeadPic(R.drawable.head);
        initDate();

    }

    private void initDate() {
        tvIdChange.setText(user.getUserId());
        tvNameChange.setText(user.getUserName());
        tvUserName.setText(user.getUserName());
        tvSexChange.setText(user.getUserSex());
        tvSignChange.setText(user.getUserSign());
        tvAgeChange.setText(user.getUserBirth());
        tvMoneyChange.setText(user.getUserBalance());
        tvUserTel.setText(user.getUserPhone());
        tvTelChange.setText(user.getUserPhone());
        switch (user.getUserSex()) {
            case "保密":
                checkdItem = 0;
                break;
            case "女":
                checkdItem = 1;
                break;
            case "男":
                checkdItem = 2;
                break;
            default:
                break;
        }
        //
    }

    private void changeHeadPic(Object src) {
        Glide.with(getActivity()).load(src)
                .bitmapTransform(new BlurTransformation(getActivity(), 25), new CenterCrop(getActivity()))
                .into(ivBg);

        //设置圆形图像
        Glide.with(getActivity()).load(src)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(ivHead);
        //设置圆形图像
        Glide.with(getActivity()).load(src)
                .bitmapTransform(new CropCircleTransformation(getActivity()))
                .into(ivHeadChoice);
    }

    private void onCreateNameDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.et_name_change);

        SpannableString ss = new SpannableString("请输入您要修改的名字!");
// 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(8, true);
// 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userInput.setHint(new SpannedString(ss));

        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("修改",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                user.setUserName(userInput.getText().toString().trim());
                                db.updateUserInfo(user);
                                initDate();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void onCreateSignDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.et_name_change);

        SpannableString ss = new SpannableString("请输入您修改的签名!");
// 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(8, true);
// 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userInput.setHint(new SpannedString(ss));
        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("修改",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                user.setUserSign(userInput.getText().toString().trim());
                                db.updateUserInfo(user);
                                initDate();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void onCreateTelDialog() {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);

        final EditText userInput = (EditText) nameView.findViewById(R.id.et_name_change);

        SpannableString ss = new SpannableString("请输入您的电话");
// 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(8, true);
// 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userInput.setHint(new SpannedString(ss));
        // 设置Dialog按钮
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("修改",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // 获取edittext的内容,显示到textview
                                user.setUserPhone(userInput.getText().toString().trim());
                                db.updateUserInfo(user);
                                initDate();
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }

    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, checkdItem, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
                user.setUserSex(sexArry[which]);
                db.updateUserInfo(user);
                initDate();
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
    }


    @Override
    public void onDateSet(Date date, int type) {
        String str = mFormatter.format(date);
        String[] s = str.split("-");
        String time = "";
        if (type == 1) {
            time = s[0];
        } else if (type == 2) {
            time = s[0] + "-" + s[1];

        } else if (type == 3) {
            time = s[0] + "-" + s[1] + "-" + s[2];
        }
        user.setUserBirth(time);
        db.updateUserInfo(user);
        initDate();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_head_change:
                Log.i(TAG, "onClick: iv_head_change ");
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
                break;
            case R.id.tv_sex_change:
                showSexChooseDialog();
                break;
            case R.id.tv_name_change:
                onCreateNameDialog();
                break;
            case R.id.tv_age_change:
                dateTimeDialogOnlyYMD.hideOrShow();
                break;
            case R.id.tv_sign_change:
                onCreateSignDialog();
                break;
            case R.id.tv_tel_change:
                onCreateTelDialog();
                break;
            case R.id.bt_logout:
                onCreateLogoutDialog();
                break;
            default:
                break;
        }
    }

    private void onCreateLogoutDialog() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(getActivity());
        builder.setTitle("提示");
        builder.setMessage("请确认是否退出登录？");
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sp = getActivity().getSharedPreferences("userinfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("state", "");
                editor.commit();
                StateApplication.USER = "";
                StateApplication.PAD = "";
                getActivity().finish();

            }
        });
        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            //     ivHeadChoice.setImageURI(data.getData());
            changeHeadPic(data.getData());
        }
    }
}
