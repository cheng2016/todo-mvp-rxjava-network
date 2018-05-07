package com.example.android.architecture.blueprints.todoapp.module.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.architecture.blueprints.todoapp.App;
import com.example.android.architecture.blueprints.todoapp.R;
import com.example.android.architecture.blueprints.todoapp.data.source.bean.Profile;
import com.example.android.architecture.blueprints.todoapp.data.source.bean.Token;
import com.example.android.architecture.blueprints.todoapp.module.BaseActivity;
import com.example.android.architecture.blueprints.todoapp.util.PreferenceConstants;
import com.example.android.architecture.blueprints.todoapp.util.PreferenceUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by mitnick.cheng on 2016/7/28.
 */

public class LoginActivity extends BaseActivity implements LoginContract.View{
    @Bind(R.id.login)
    Button login;
    @Bind(R.id.loginAndGetProfile)
    Button loginAndGetProfile;
    @Bind(R.id.textView)
    TextView textView;

    private String mAccessToken = "";

    private LoginPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        new LoginPresenter(this);
    }

    @Override
    public void initUI() {
    }

    @Override
    protected int getRootViewId() {
        return R.layout.activity_login;
    }

    //activity_login.xml 中onClick方法
    void login(View view) {
        showProgressDialog("login...");
        mPresenter.login("Basic dG1qMDAxOjEyMzQ1Ng==");
    }

    @OnClick({R.id.login, R.id.loginAndGetProfile})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                showProgressDialog("login...");
                mPresenter.login("Basic dG1qMDAxOjEyMzQ1Ng==");
                break;
            case R.id.loginAndGetProfile:
                textView.setText("init");
                showProgressDialog("login...");
                mPresenter.loginAndGetProfile("Basic dG1qMDAxOjEyMzQ1Ng==");
                break;
        }
    }

    @Override
    public void loginSuccess(Token token) {
        mAccessToken = token.getAccess_token();
        PreferenceUtils.setPrefString(App.getInstance(), PreferenceConstants.REFRESH_TOKEN, token.getRefresh_token());
        Toast.makeText(LoginActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void loginFail() {
        Toast.makeText(this, "登录失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getProfileSuccess(Profile profile) {
        textView.setText("name :" + profile.getUsername() + "\t email :"
                + profile.getEmail() + "\t birthday :" + profile.getBirthday() + "\t height: " + profile.getHeight() + "\t weight :" + profile.getWeight());
    }

    @Override
    public void getProfileFail() {
        Toast.makeText(this, "获取用户信息失败！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = (LoginPresenter) presenter;
    }
}
