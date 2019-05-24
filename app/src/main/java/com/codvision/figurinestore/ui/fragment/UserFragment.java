package com.codvision.figurinestore.ui.fragment;


import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
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
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.codvision.figurinestore.R;
import com.codvision.figurinestore.App;
import com.codvision.figurinestore.module.bean.AddressBean;
import com.codvision.figurinestore.module.bean.User;
import com.codvision.figurinestore.presenter.UserLoginPresenter;
import com.codvision.figurinestore.presenter.UserSubmitPresenter;
import com.codvision.figurinestore.presenter.contract.UserLoginContract;
import com.codvision.figurinestore.presenter.contract.UserSubmitContract;
import com.codvision.figurinestore.ui.activity.LoginActivity;
import com.codvision.figurinestore.utils.AreaPickerView;
import com.codvision.figurinestore.utils.CircleTransform;
import com.codvision.figurinestore.utils.DateTimeDialogOnlyYMD;
import com.codvision.figurinestore.utils.SharedPreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class UserFragment extends Fragment implements View.OnClickListener, UserLoginContract.View, UserSubmitContract.View {
    public static final String TAG = "UserFragment";
    private View view;
    private ImageView ivHeadChoice;
    private TextView tvSexChange;
    private TextView tvNameChange;
    private TextView tvSignChange;
    private TextView tvMoneyChange;
    private TextView tvAddressChange;
    private TextView tvTelChange;
    private TextView tv_logout;
    private TextView tvSave;
    private User user;
    private String[] sexArry = new String[]{"保密", "女", "男"};// 性别选择
    private int checkdItem = 0;
    private UserLoginPresenter userLoginPresenter;
    private UserSubmitPresenter userSubmitPresenter;
    private String uri;
    private boolean imageBack;

    private AreaPickerView areaPickerView;
    private List<AddressBean> addressBeans;
    private int[] i;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user, container, false);
        initView();
        initEvent();
        initCity();
        return view;
    }


    @Override
    public void onResume() {
        initDate();
        super.onResume();
    }

    private void initView() {

        ivHeadChoice = view.findViewById(R.id.iv_head_change);
        tvSexChange = view.findViewById(R.id.tv_sex_change);
        tvNameChange = view.findViewById(R.id.tv_name_change);
        tvSignChange = view.findViewById(R.id.tv_sign_change);
        tvMoneyChange = view.findViewById(R.id.tv_money_change);
        tvTelChange = view.findViewById(R.id.tv_tel_change);
        tvAddressChange = view.findViewById(R.id.tv_address_change);
        tv_logout = view.findViewById(R.id.tv_logout);
        tvSave = view.findViewById(R.id.tv_save);
        userLoginPresenter = new UserLoginPresenter(this, getActivity());
        userSubmitPresenter = new UserSubmitPresenter(this, getActivity());
        userLoginPresenter.login(SharedPreferenceUtils.getUserName(getActivity()), SharedPreferenceUtils.getPwd(getActivity()));
    }

    private void initDate() {

    }


    private void initEvent() {
        ivHeadChoice.setOnClickListener(this);
        tvSexChange.setOnClickListener(this);
        tvSignChange.setOnClickListener(this);
        tvTelChange.setOnClickListener(this);
        tvSave.setOnClickListener(this);
        tvAddressChange.setOnClickListener(this);
        tv_logout.setOnClickListener(this);
    }


    private void onCreateDialog(String title, final TextView textView) {
        // 使用LayoutInflater来加载dialog_setname.xml布局
        LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
        View nameView = layoutInflater.inflate(R.layout.dialog_setname, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        // 使用setView()方法将布局显示到dialog
        alertDialogBuilder.setView(nameView);
        final EditText userInput = (EditText) nameView.findViewById(R.id.et_name_change);
        SpannableString ss = new SpannableString(title);
        // 新建一个属性对象,设置文字的大小
        AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
        // 附加属性到文本
        ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        userInput.setHint(new SpannedString(ss));
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("修改",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                textView.setText(userInput.getText().toString().trim());
                            }
                        })
                .setNegativeButton("取消",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }


    private void showSexChooseDialog() {
        AlertDialog.Builder builder3 = new AlertDialog.Builder(getActivity());// 自定义对话框
        builder3.setSingleChoiceItems(sexArry, checkdItem, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                checkdItem = which;
                tvSexChange.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder3.show();// 让弹出框显示
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
            case R.id.tv_sign_change:
                onCreateDialog("请输入您修改的签名!", tvSignChange);
                break;
            case R.id.tv_tel_change:
                onCreateDialog("请输入您的电话!", tvTelChange);
                break;
            case R.id.tv_save:
                user.setPhone(tvTelChange.getText().toString().trim());
                user.setGender(checkdItem);
                user.setSign(tvSignChange.getText().toString().trim());
                user.setAddress(tvAddressChange.getText().toString().trim());
                user.setImage(uri);
                userSubmitPresenter.submit(user);
                break;
            case R.id.tv_address_change:
                i = null;
                areaPickerView.setSelect(i);
                areaPickerView.show();
                break;
            case R.id.tv_logout:
                SharedPreferenceUtils.clearLoginInfo(getActivity());
                Intent intent1 = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent1);
                getActivity().finish();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (data != null) {
//                uri = data.getData().toString();
                uri = getRealPathFromUri_Api11To18(getActivity(), data.getData());
                user.setImage(uri);
                Picasso.with(getActivity()).load(new File(uri)).transform(new CircleTransform()).error(R.drawable.head1).into(ivHeadChoice);
                // changeHeadPic(uri);
                //changeHeadPic(data.getData());
                imageBack = true;
            }
        }
    }

    /**
     * //适配api11-api18,根据uri获取图片的绝对路径。
     * 针对图片URI格式为Uri:: content://media/external/images/media/1028
     */
    private static String getRealPathFromUri_Api11To18(Context context, Uri uri) {
        String filePath = null;
        String[] projection = {MediaStore.Images.Media.DATA};

        CursorLoader loader = new CursorLoader(context, uri, projection, null,
                null, null);
        Cursor cursor = loader.loadInBackground();

        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
            cursor.close();
        }
        return filePath;
    }

    private void initCity() {

        Gson gson = new Gson();
        addressBeans = gson.fromJson(getCityJson(), new TypeToken<List<AddressBean>>() {
        }.getType());

        areaPickerView = new AreaPickerView(getActivity(), R.style.Dialog, addressBeans);
        areaPickerView.setAreaPickerViewCallback(new AreaPickerView.AreaPickerViewCallback() {
            @Override
            public void callback(int... value) {
                i = value;
                if (value.length == 3)
                    tvAddressChange.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getChildren().get(value[2]).getLabel());
                else
                    tvAddressChange.setText(addressBeans.get(value[0]).getLabel() + "-" + addressBeans.get(value[0]).getChildren().get(value[1]).getLabel());
            }
        });
    }

    private String getCityJson() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = getActivity().getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open("region.json")));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void loginSuccess() {
        user = SharedPreferenceUtils.getUser(getActivity());
        tvSexChange.setText(sexArry[user.getGender()]);
        tvNameChange.setText(user.getUsername());
        tvSignChange.setText(user.getSign());
        checkdItem = user.getGender();
        tvMoneyChange.setText(user.getBalance() + "");
        tvTelChange.setText(user.getPhone());
        tvAddressChange.setText(user.getAddress());
        Picasso.with(getActivity()).load(new File(user.getImage())).transform(new CircleTransform()).error(R.drawable.head1).into(ivHeadChoice);
        imageBack = false;
    }

    @Override
    public void loginFail(String code, String message) {

    }

    @Override
    public void submitSuccess() {
        userLoginPresenter.login(SharedPreferenceUtils.getUserName(getActivity()), SharedPreferenceUtils.getPwd(getActivity()));
    }

    @Override
    public void submitFail(String code, String message) {

    }
}
