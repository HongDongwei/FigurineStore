package com.codvision.figurinestore.presenter.contract;

import com.codvision.figurinestore.base.BaseView;
/**
 * Created by sxy on 2019/5/9 14:38
 * todo
 */
public interface UserLoginContract {

    public interface Presenter {
        //登录
        void login(String user, String pwd);
    }

    public interface View extends BaseView<Presenter> {

        //成功
        void loginSuccess();

        void loginFail(String code, String message);
    }
}
