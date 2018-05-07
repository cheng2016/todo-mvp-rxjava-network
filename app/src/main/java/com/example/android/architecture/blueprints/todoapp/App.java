package com.example.android.architecture.blueprints.todoapp;

import android.app.Application;

/**
 * Created by Administrator on 2018/5/7.
 */

public class App extends Application{

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public  synchronized static  App getInstance(){
        return sInstance;
    }
}
