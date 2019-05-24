package com.codvision.figurinestore.presenter;

import android.content.Context;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.module.bean.CommoditySubmit;
import com.codvision.figurinestore.module.bean.InsertId;
import com.codvision.figurinestore.presenter.contract.CommodityGetContract;
import com.codvision.figurinestore.presenter.contract.CommoditySubmitContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class CommoditySubmitPresenter implements CommoditySubmitContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private CommoditySubmitContract.View view;
    private Context context;

    public CommoditySubmitPresenter(CommoditySubmitContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void submitCommodity(CommoditySubmit commoditySubmit) {
        RetrofitUtil.getInstance().initRetrofit().getCommoditySubmit(commoditySubmit)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InsertId>(context, Constant.GETING) {
                    @Override
                    protected void onSuccees(WrapperEntity<InsertId> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.submitCommoditySuccess();
                        } else {
                            view.submitCommodityFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.submitCommodityFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
