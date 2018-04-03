package com.guoziwei.timerecorder.bean;

import android.graphics.drawable.Drawable;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class Record extends DataSupport {

    private String packageName;
    private long startTime;
    private long interval;
    @Column(ignore = true)
    private CharSequence appName;
    @Column(ignore = true)
    private Drawable icon;

    @Column(ignore = true)
    private long totalTime;

    public long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(long totalTime) {
        this.totalTime = totalTime;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }

    public CharSequence getAppName() {
        return appName;
    }

    public void setAppName(CharSequence appName) {
        this.appName = appName;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
