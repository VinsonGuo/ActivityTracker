package com.guoziwei.timerecorder.component;

import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.bean.Record;
import com.guoziwei.timerecorder.component.adapter.AppListAdapter;
import com.guoziwei.timerecorder.component.adapter.SimpleFragmentPagerAdapter;
import com.guoziwei.timerecorder.utils.AppUtils;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {

    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        Fragment[] fragments = {new RecordFragment()};
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), Arrays.asList(fragments), Arrays.asList("今天")));
        tabLayout.setupWithViewPager(viewPager);

//        loadData();


    }

}
