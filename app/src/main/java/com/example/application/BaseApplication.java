package com.example.application;

import android.app.Application;
import com.example.util.StaticClass;
import cn.bmob.v3.Bmob;


public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);


    }
}
