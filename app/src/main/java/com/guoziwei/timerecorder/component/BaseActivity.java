package com.guoziwei.timerecorder.component;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by guoziwei on 2018/4/2.
 */

public class BaseActivity extends AppCompatActivity {

    protected Activity mActivity;

    protected final String TAG = getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = this;
    }
}
