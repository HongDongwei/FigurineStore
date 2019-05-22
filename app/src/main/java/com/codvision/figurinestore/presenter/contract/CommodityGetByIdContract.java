package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGet;
import com.codvision.figurinestore.module.bean.CommodityGetById;

import java.util.List;

/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface CommodityGetByIdContract {

    public interface Presenter {
        //登录
        void getCommodity(CommodityGetById commodityGetById);
    }

    public interface View extends BaseView<Presenter> {

        //成功
        void getCommoditySuccess(Commodity commodity);

        void getCommodityFail(String code, String message);
    }
}
