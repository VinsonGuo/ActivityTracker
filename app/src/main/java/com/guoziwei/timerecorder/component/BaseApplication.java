package com.guoziwei.timerecorder.component;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.umeng.commonsdk.UMConfigure;

import org.litepal.LitePal;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        Stetho.initializeWithDefaults(this);
        UMConfigure.init(this, "5ac481a7f43e481dc500009c", "official", 1,null);

    }
}
