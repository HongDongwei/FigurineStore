package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.module.bean.CommoditySubmit;

import java.util.List;

/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface CommoditySubmitContract {

    public interface Presenter {
        //登录
        void submitCommodity(CommoditySubmit commoditySubmit);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void submitCommoditySuccess();

        void submitCommodityFail(String code, String message);
    }
}
