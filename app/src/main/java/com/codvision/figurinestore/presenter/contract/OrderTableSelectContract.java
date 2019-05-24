package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.OrderTableInsert;
import com.codvision.figurinestore.module.bean.OrderTableRecieve;
import com.codvision.figurinestore.module.bean.OrderTableSelect;

import java.util.ArrayList;

/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface OrderTableSelectContract {

    public interface Presenter {
        //登录
        void orderSelect(OrderTableSelect orderSelect);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void orderSelectSuccess(ArrayList<OrderTableRecieve> orderTableRecieveArrayList);

        void orderSelectFail(String code, String message);
    }
}
