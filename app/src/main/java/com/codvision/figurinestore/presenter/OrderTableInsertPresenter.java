package com.codvision.figurinestore.presenter;

import android.content.Context;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGetById;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.module.bean.OrderTableInsert;
import com.codvision.figurinestore.presenter.contract.CommodityGetByIdContract;
import com.codvision.figurinestore.presenter.contract.OrderTableInsertContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class OrderTableInsertPresenter implements OrderTableInsertContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private OrderTableInsertContract.View view;
    private Context context;

    public OrderTableInsertPresenter(OrderTableInsertContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void orderInsert(OrderTableInsert orderTableInsert) {
        RetrofitUtil.getInstance().initRetrofit().orderInsert(orderTableInsert)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InsertId>(context, Constant.SUBMITING) {
                    @Override
                    protected void onSuccees(WrapperEntity<InsertId> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.orderInsertSuccess();
                        } else {
                            view.orderInsertFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.orderInsertFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
