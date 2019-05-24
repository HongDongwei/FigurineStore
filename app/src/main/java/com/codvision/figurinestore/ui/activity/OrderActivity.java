package com.codvision.figurinestore.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codvision.figurinestore.R;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.module.bean.CommodityGetById;
import com.codvision.figurinestore.module.bean.OrderTableInsert;
import com.codvision.figurinestore.module.bean.User;
import com.codvision.figurinestore.presenter.CommodityGetByIdPresenter;
import com.codvision.figurinestore.presenter.OrderTableInsertPresenter;
import com.codvision.figurinestore.presenter.UserLoginPresenter;
import com.codvision.figurinestore.presenter.UserSubmitPresenter;
import com.codvision.figurinestore.presenter.contract.CommodityGetByIdContract;
import com.codvision.figurinestore.presenter.contract.OrderTableInsertContract;
import com.codvision.figurinestore.presenter.contract.UserLoginContract;
import com.codvision.figurinestore.presenter.contract.UserSubmitContract;
import com.codvision.figurinestore.utils.SharedPreferenceUtils;

public class OrderActivity extends AppCompatActivity implements CommodityGetByIdContract.View, OrderTableInsertContract.View, UserSubmitContract.View, UserLoginContract.View {

    private String NewsID;

    private TextView tvUserName;
    private TextView tvUserPhone;
    private TextView tvUserAddress;
    private TextView tvComType;
    private TextView tvComName;
    private TextView tvComPrice;
    private ImageView ivComPic;
    private EditText etNum;
    private TextView tvPrice;
    private TextView tvNum;
    private TextView tvSubmit;
    private TextView tvBack;

    private OrderTableInsertPresenter orderTableInsertPresenter;
    private CommodityGetByIdPresenter commodityGetByIdPresenter;
    private UserLoginPresenter userLoginPresenter;
    private UserSubmitPresenter userSubmitPresenter;
    private int comid;
    int type = 0;
    private int comNum;
    private boolean orderType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        initEvent();
    }


    private void initView() {
        tvUserName = findViewById(R.id.tv_order_user_name);
        tvUserPhone = findViewById(R.id.tv_order_user_phone);
        tvUserAddress = findViewById(R.id.tv_order_user_address);
        tvComType = findViewById(R.id.tv_order_com_type);
        tvComName = findViewById(R.id.tv_order_com_name);
        tvComPrice = findViewById(R.id.tv_order_price);
        ivComPic = findViewById(R.id.iv_order_com_pic);
        etNum = findViewById(R.id.et_order_num);
        tvPrice = findViewById(R.id.tv_order_price_add);
        tvNum = findViewById(R.id.tv_order_num);
        tvSubmit = findViewById(R.id.tv_order_submit);
        tvBack = findViewById(R.id.tv_order_back);
        commodityGetByIdPresenter = new CommodityGetByIdPresenter(this, this);
        orderTableInsertPresenter = new OrderTableInsertPresenter(this, this);
        userSubmitPresenter = new UserSubmitPresenter(this, this);
        userLoginPresenter = new UserLoginPresenter(this, this);
        comid = getIntent().getIntExtra("goodId", 0);
        if (Constant.orderNum != 0) {
            orderType = true;
        }
        commodityGetByIdPresenter.getCommodity(new CommodityGetById(comid));

    }

    private void initEvent() {
        etNum.addTextChangedListener(textWatcher);
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderType) {
                    showDialog();
                } else {
                    showMultiBtnDialog();
                }

            }
        });
        tvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            String content = etNum.getText().toString().trim();
            if (!TextUtils.isEmpty(etNum.getText().toString())) {
                tvPrice.setText(Float.parseFloat(tvComPrice.getText().toString().trim()) * Integer.parseInt(etNum.getText().toString()) + "");
                tvNum.setText(etNum.getText().toString().trim());
            } else {
                tvPrice.setText("0");
                SpannableString ss = new SpannableString("0");
                // 新建一个属性对象,设置文字的大小
                AbsoluteSizeSpan ass = new AbsoluteSizeSpan(15, true);
                // 附加属性到文本
                ss.setSpan(ass, 0, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                etNum.setHint(new SpannedString(ss));
                tvNum.setText("0");
            }

        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    /* @setNeutralButton 设置中间的按钮
     * 若只需一个按钮，仅设置 setPositiveButton 即可
     */
    private void showDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(OrderActivity.this);
        normalDialog.setTitle("订单确认").setMessage("请问是否购买" + etNum.getText().toString().trim() + "个商品，共计" + tvPrice.getText().toString().trim() + "元");
        normalDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type = 0;
                    }
                });
        normalDialog.setNegativeButton("确认支付", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (SharedPreferenceUtils.getBalance(OrderActivity.this) >= Float.valueOf(tvPrice.getText().toString())) {
                    orderTableInsertPresenter.orderInsert(new OrderTableInsert(SharedPreferenceUtils.getUserId(OrderActivity.this), comid, "2019-5-25", "已完成", Integer.parseInt(etNum.getText().toString())));
                    type = 2;
                    User user = SharedPreferenceUtils.getUser(OrderActivity.this);
                    user.setBalance(SharedPreferenceUtils.getBalance(OrderActivity.this) - Float.valueOf(tvPrice.getText().toString()));
                    userSubmitPresenter.submit(user);
                } else {
                    Toast.makeText(OrderActivity.this, "余额不足，请充值", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 创建实例并显示
        normalDialog.show();
    }

    /* @setNeutralButton 设置中间的按钮
     * 若只需一个按钮，仅设置 setPositiveButton 即可
     */
    private void showMultiBtnDialog() {
        AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(OrderActivity.this);
        normalDialog.setTitle("订单确认").setMessage("请问是否购买" + etNum.getText().toString().trim() + "个商品，共计" + tvPrice.getText().toString().trim() + "元");
        normalDialog.setPositiveButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        type = 0;
                    }
                });
        normalDialog.setNeutralButton("添加订单页",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        orderTableInsertPresenter.orderInsert(new OrderTableInsert(SharedPreferenceUtils.getUserId(OrderActivity.this), comid, "2019-5-25", "未支付", Integer.parseInt(etNum.getText().toString())));
                        type = 1;
                    }
                });
        normalDialog.setNegativeButton("确认支付", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (SharedPreferenceUtils.getBalance(OrderActivity.this) >= Float.valueOf(tvPrice.getText().toString())) {
                    orderTableInsertPresenter.orderInsert(new OrderTableInsert(SharedPreferenceUtils.getUserId(OrderActivity.this), comid, "2019-5-25", "已完成", Integer.parseInt(etNum.getText().toString())));
                    type = 2;
                    User user = SharedPreferenceUtils.getUser(OrderActivity.this);
                    user.setBalance(SharedPreferenceUtils.getBalance(OrderActivity.this) - Float.valueOf(tvPrice.getText().toString()));
                    userSubmitPresenter.submit(user);
                } else {
                    Toast.makeText(OrderActivity.this, "余额不足，请充值", Toast.LENGTH_SHORT).show();
                }

            }
        });
        // 创建实例并显示
        normalDialog.show();
    }

    @Override
    public void getCommoditySuccess(Commodity commodity) {
        tvUserName.setText(SharedPreferenceUtils.getUserName(this));
        tvUserPhone.setText(SharedPreferenceUtils.getPhone(this));
        tvUserAddress.setText(SharedPreferenceUtils.getAddress(this));
        tvComType.setText(commodity.getType());
        tvComName.setText(commodity.getName());
        tvComPrice.setText(commodity.getPrice());
        ivComPic.setBackgroundResource(getResources().getIdentifier(commodity.getPic1(), "drawable", getPackageName()));
        if (Constant.orderNum != 0) {
            etNum.setText(Constant.orderNum + "");
            tvNum.setText(Constant.orderNum + "");
            Constant.orderNum = 0;
        } else {
            etNum.setText("1");
            tvNum.setText("1");
        }
        tvPrice.setText(Float.parseFloat(commodity.getPrice()) * Integer.parseInt(etNum.getText().toString()) + "");
    }

    @Override
    public void getCommodityFail(String code, String message) {

    }

    @Override
    public void orderInsertSuccess() {
        if (type == 1) {
            Toast.makeText(OrderActivity.this, "订单已经添加到购物车，请移步去订单页查看！", Toast.LENGTH_SHORT).show();
        } else if (type == 2) {
            Toast.makeText(OrderActivity.this, "订单已经完成，请移步去订单页查看！", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void orderInsertFail(String code, String message) {

    }

    @Override
    public void submitSuccess() {
        userLoginPresenter.login(SharedPreferenceUtils.getUserName(OrderActivity.this), SharedPreferenceUtils.getPwd(OrderActivity.this));
    }

    @Override
    public void submitFail(String code, String message) {

    }

    @Override
    public void loginSuccess() {

    }

    @Override
    public void loginFail(String code, String message) {

    }
}
