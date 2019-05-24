package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.Commodity;
import com.codvision.figurinestore.module.bean.CommodityGetById;
import com.codvision.figurinestore.module.bean.OrderTableInsert;

/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface OrderTableInsertContract {

    public interface Presenter {
        //登录
        void orderInsert(OrderTableInsert orderTableInsert);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void orderInsertSuccess();

        void orderInsertFail(String code, String message);
    }
}
