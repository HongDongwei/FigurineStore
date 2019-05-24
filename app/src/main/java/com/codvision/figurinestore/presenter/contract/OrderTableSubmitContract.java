package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.OrderTableRecieve;
import com.codvision.figurinestore.module.bean.OrderTableSelect;
import com.codvision.figurinestore.module.bean.OrderTableSubmit;

import java.util.ArrayList;

/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface OrderTableSubmitContract {

    public interface Presenter {
        //登录
        void orderSubmit(OrderTableSubmit orderSelect);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void orderSubmitSuccess();

        void orderSubmitFail(String code, String message);
    }
}
