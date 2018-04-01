package com.guoziwei.timerecorder.component;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.component.adapter.AppListAdapter;
import com.guoziwei.timerecorder.utils.AppUtils;

public class AppListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        AppListAdapter adapter = new AppListAdapter(AppUtils.getInstallAppInfo(this), getPackageManager());
        recyclerView.setAdapter(adapter);
    }
}
