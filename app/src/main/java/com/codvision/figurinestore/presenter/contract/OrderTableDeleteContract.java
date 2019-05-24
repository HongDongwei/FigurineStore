package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.OrderTableDelete;
import com.codvision.figurinestore.module.bean.OrderTableSubmit;

/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface OrderTableDeleteContract {

    public interface Presenter {
        //登录
        void orderDelete(OrderTableDelete orderTableDelete);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void orderDeleteSuccess();

        void orderDeleteFail(String code, String message);
    }
}
