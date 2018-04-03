package com.guoziwei.timerecorder.component;


import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.bean.Record;
import com.guoziwei.timerecorder.component.adapter.AppListAdapter;
import com.guoziwei.timerecorder.utils.AppUtils;

import org.litepal.crud.DataSupport;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private Activity mActivity;

    public RecordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRecyclerView = new RecyclerView(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        return mRecyclerView;
    }


    @Override
    public void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Observable.create((ObservableOnSubscribe<List<Record>>) emitter -> {
            List<Record> records = DataSupport
                    .where("packagename not null and starttime > ?", AppUtils.getTodayTime() + "")
                    .order("packagename")
                    .find(Record.class);
            if (!emitter.isDisposed()) {
                emitter.onNext(records);
            }

        })
                .map(records -> {
                    Map<String, ApplicationInfo> infoMap = AppUtils.getInstallAppInfo(mActivity);
                    for (int i = 0; i < records.size(); i++) {
                        Record record = records.get(i);

                        ApplicationInfo info = infoMap.get(record.getPackageName());
                        if (info == null) {
                            record.setAppName("已卸载");
                            record.setIcon(ContextCompat.getDrawable(mActivity, R.drawable.track));
                            record.setPackageName("已卸载");
                        } else {
                            record.setAppName(info.loadLabel(mActivity.getPackageManager()));
                            record.setIcon(info.loadIcon(mActivity.getPackageManager()));
                        }

                        record.setTotalTime(record.getInterval());
                        if (i >= 1) {
                            Record prev = records.get(i - 1);
                            if (TextUtils.equals(record.getPackageName(), prev.getPackageName())) {
                                prev.setTotalTime(prev.getTotalTime() + record.getInterval());
                                records.remove(i);
                                i--;
                            }
                        }
                    }
                    Collections.sort(records, (o1, o2) -> (int) (o2.getTotalTime() - o1.getTotalTime()));
                    return records;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(records -> {
                    AppListAdapter adapter = new AppListAdapter(records);
                    mRecyclerView.setAdapter(adapter);

                }, throwable -> throwable.printStackTrace());
    }
}
