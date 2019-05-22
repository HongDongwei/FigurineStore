package com.codvision.figurinestore.presenter;

import android.content.Context;
import android.util.Log;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.module.bean.User;
import com.codvision.figurinestore.module.bean.UserSubmit;
import com.codvision.figurinestore.presenter.contract.UserSubmitContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class UserSubmitPresenter implements UserSubmitContract.Presenter {
    public static final String TAG = "UserSubmitPresenter";
    private UserSubmitContract.View view;
    private Context context;

    public UserSubmitPresenter(UserSubmitContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void submit(User user) {
        Log.i(TAG, "submit: user=" + user);
        RetrofitUtil.getInstance().initRetrofit().userSubmit(new UserSubmit(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InsertId>(context, Constant.SUBMITING) {
                    @Override
                    protected void onSuccees(WrapperEntity<InsertId> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.submitSuccess();
                        } else {
                            view.submitFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.submitFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
