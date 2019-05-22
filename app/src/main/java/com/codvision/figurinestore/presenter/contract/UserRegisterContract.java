package com.codvision.figurinestore.presenter.contract;


import com.codvision.figurinestore.base.BaseView;

/**
 * Created by sxy on 2019/5/9 18:52
 * todo
 */
public interface UserRegisterContract {
    public interface Presenter {
        //登录
        void register(String user, String pwd);
    }

    public interface View extends BaseView<UserLoginContract.Presenter> {

        //成功
        void registerSuccess();

        void registerFail(String code, String message);
    }
}
