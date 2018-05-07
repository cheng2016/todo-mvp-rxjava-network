package com.example.android.architecture.blueprints.todoapp.module.login;

import android.util.Log;

import com.example.android.architecture.blueprints.todoapp.data.source.bean.Profile;
import com.example.android.architecture.blueprints.todoapp.data.source.bean.Token;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.Http;
import com.example.android.architecture.blueprints.todoapp.data.source.remote.HttpFactory;
import com.google.gson.Gson;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/5/7.
 */

public class LoginPresenter implements LoginContract.Presenter {
    private final static String TAG = LoginPresenter.class.getSimpleName();
    private LoginContract.View view;
    private Http mApiClient;

    public LoginPresenter(LoginContract.View view) {
        this.view = view;
        view.setPresenter(this);
        mApiClient = HttpFactory.createRetrofit2RxJavaService(Http.class);
    }

    @Override
    public void login(String params) {
        mApiClient.login(params)
                .doOnNext(new Action1<Token>() {//该方法执行请求成功后的耗时操作，比如数据库读写
                    @Override
                    public void call(Token token) {
                        Log.i(TAG, "doOnNext() " + new Gson().toJson(token));
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new rx.Observer<Token>() {
                    @Override
                    public void onCompleted() {
                        Log.i(TAG, "onCompleted");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG, "login 请求失败！" + throwable.toString());
                        view.loginFail();
                    }

                    @Override
                    public void onNext(Token token) {
                        Log.i(TAG, "onNext()");
                        view.loginSuccess(token);
                    }
                });
    }

    @Override
    public void loginAndGetProfile(String auth) {
        mApiClient.login(auth)
                .flatMap(new Func1<Token, Observable<Profile>>() {
                    @Override
                    public Observable<Profile> call(Token token) {
                        Log.e(TAG,"loginAndGetProfile flatMap 请求成功！ " + new Gson().toJson(token));
                        return mApiClient.getProfile(token.getAccess_token());
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Profile>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG , "onCompleted！");
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Log.e(TAG,"loginAndGetProfile 请求失败！" + throwable.toString());
                        view.getProfileFail();
                    }

                    @Override
                    public void onNext(Profile profile) {
                        Log.e(TAG,"loginAndGetProfile 请求成功！" + new Gson().toJson(profile));
                        view.getProfileSuccess(profile);
                    }
                });
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }
}
