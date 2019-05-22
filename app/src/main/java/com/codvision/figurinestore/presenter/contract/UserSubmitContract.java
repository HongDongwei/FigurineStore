package com.codvision.figurinestore.presenter.contract;


import com.codvision.figurinestore.base.BaseView;
import com.codvision.figurinestore.module.bean.User;

/**
 * Created by sxy on 2019/5/9 22:19
 * todo
 */
public interface UserSubmitContract {
    public interface Presenter {
        //提交
        void submit(User user);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void submitSuccess();

        void submitFail(String code, String message);
    }
}