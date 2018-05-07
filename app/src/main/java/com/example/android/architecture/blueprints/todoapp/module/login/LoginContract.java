package com.example.android.architecture.blueprints.todoapp.module.login;

import com.example.android.architecture.blueprints.todoapp.BasePresenter;
import com.example.android.architecture.blueprints.todoapp.BaseView;
import com.example.android.architecture.blueprints.todoapp.data.source.bean.Profile;
import com.example.android.architecture.blueprints.todoapp.data.source.bean.Token;

/**
 * Created by Administrator on 2018/5/7.
 */

public interface LoginContract {
    interface View extends BaseView<Presenter> {
        void loginSuccess(Token token);

        void loginFail();

        void getProfileSuccess(Profile profile);

        void getProfileFail();
    }

    interface Presenter extends BasePresenter {
        void login(String params);

        void loginAndGetProfile(String auth);
    }
}
