package com.codvision.figurinestore.presenter;

import android.content.Context;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.module.bean.OrderTableDelete;
import com.codvision.figurinestore.module.bean.OrderTableSubmit;
import com.codvision.figurinestore.presenter.contract.OrderTableDeleteContract;
import com.codvision.figurinestore.presenter.contract.OrderTableSubmitContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class OrderTableDeletePresenter implements OrderTableDeleteContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private OrderTableDeleteContract.View view;
    private Context context;

    public OrderTableDeletePresenter(OrderTableDeleteContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void orderDelete(OrderTableDelete orderTableDelete) {
        RetrofitUtil.getInstance().initRetrofit().orderDelete(orderTableDelete)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InsertId>(context, Constant.SUBMITING) {
                    @Override
                    protected void onSuccees(WrapperEntity<InsertId> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.orderDeleteSuccess();
                        } else {
                            view.orderDeleteFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.orderDeleteFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
