package com.guoziwei.timerecorder.component;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);

    }
}
