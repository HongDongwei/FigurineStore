package com.codvision.figurinestore.presenter;

import android.content.Context;

import com.codvision.figurinestore.base.BaseObserver;
import com.codvision.figurinestore.base.Constant;
import com.codvision.figurinestore.base.WrapperEntity;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.presenter.contract.CommodityGetContract;
import com.codvision.figurinestore.utils.RetrofitUtil;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sxy on 2019/5/9 14:48
 * todo
 */
public class CommodityGetPresenter implements CommodityGetContract.Presenter {
    public static final String TAG = "UserLoginPresenter";
    private CommodityGetContract.View view;
    private Context context;

    public CommodityGetPresenter(CommodityGetContract.View view, Context context) {
        this.view = view;
        this.context = context;
    }

    @Override
    public void getCommodity(CommodityGet commodityGet) {
        RetrofitUtil.getInstance().initRetrofit().getCommodity(commodityGet)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<ArrayList<Commodity>>(context, Constant.GETING) {
                    @Override
                    protected void onSuccees(WrapperEntity<ArrayList<Commodity>> wrapperEntity) throws Exception {
                        if (wrapperEntity.getStatus()) {
                            view.getCommoditySuccess(wrapperEntity.getData());
                        } else {
                            view.getCommodityFail(wrapperEntity.getCode(), wrapperEntity.getCode() + ": " + wrapperEntity.getMessage());
                        }
                    }

                    @Override
                    protected void onFailure(Throwable e, boolean isNetWorkError) throws Exception {
                        view.getCommodityFail("1", "连接服务器失败，请检查网与服务器");
                    }
                });
    }

}
