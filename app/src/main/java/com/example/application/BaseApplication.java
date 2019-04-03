package com.example.application;

import android.app.Application;
import android.content.Context;

import com.example.util.StaticClass;

import cn.bmob.v3.Bmob;


public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
    }

    public static Context getContext(){
        return mContext;
    }
}
