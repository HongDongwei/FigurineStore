package com.codvision.figurinestore.presenter;

import android.content.Context;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.module.bean.OrderTableInsert;
import com.codvision.figurinestore.module.bean.OrderTableSubmit;
import com.codvision.figurinestore.presenter.contract.OrderTableInsertContract;
import com.codvision.figurinestore.presenter.contract.OrderTableSubmitContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class OrderTableSubmitPresenter implements OrderTableSubmitContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private OrderTableSubmitContract.View view;
    private Context context;

    public OrderTableSubmitPresenter(OrderTableSubmitContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }


    @Override
    public void orderSubmit(OrderTableSubmit orderTableSubmit) {
        RetrofitUtil.getInstance().initRetrofit().orderSubmit(orderTableSubmit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InsertId>(context, Constant.SUBMITING) {
                    @Override
                    protected void onSuccees(WrapperEntity<InsertId> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.orderSubmitSuccess();
                        } else {
                            view.orderSubmitFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.orderSubmitFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
