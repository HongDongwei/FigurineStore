package com.codvision.figurinestore.presenter;

import android.content.Context;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.module.bean.OrderTableInsert;
import com.codvision.figurinestore.module.bean.OrderTableRecieve;
import com.codvision.figurinestore.module.bean.OrderTableSelect;
import com.codvision.figurinestore.presenter.contract.OrderTableInsertContract;
import com.codvision.figurinestore.presenter.contract.OrderTableSelectContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class OrderTableSelectPresenter implements OrderTableSelectContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private OrderTableSelectContract.View view;
    private Context context;

    public OrderTableSelectPresenter(OrderTableSelectContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void orderSelect(OrderTableSelect orderTableSelect) {
        RetrofitUtil.getInstance().initRetrofit().orderSelect(orderTableSelect)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<OrderTableRecieve>>(context, Constant.SUBMITING) {
                    @Override
                    protected void onSuccees(WrapperEntity<ArrayList<OrderTableRecieve>> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.orderSelectSuccess(wrapperEntity.getData());
                        } else {
                            view.orderSelectFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.orderSelectFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
