package com.codvision.figurinestore.presenter;

import android.content.Context;
import android.util.Log;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.presenter.contract.UserRegisterContract;
import com.codvision.figurinestore.utils.RetrofitUtil;


import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class UserRegisterPresenter implements UserRegisterContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private UserRegisterContract.View view;
    private Context context;

    public UserRegisterPresenter(UserRegisterContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void register(String user, String pwd) {
        Map<String, String> map = new HashMap<>();
        map.put("username", user);
        map.put("password", pwd);
        Log.i(TAG, "register: "+map);
        RetrofitUtil.getInstance().initRetrofit().userRegister(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InsertId>(context, Constant.REGISTERING) {
                    @Override
                    protected void onSuccees(WrapperEntity<InsertId> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.registerSuccess();
                        } else {
                            view.registerFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.registerFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }
}
