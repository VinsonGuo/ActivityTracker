package com.guoziwei.timerecorder.component;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.bean.Record;
import com.guoziwei.timerecorder.component.adapter.SimpleFragmentPagerAdapter;
import com.guoziwei.timerecorder.utils.AppUtils;

import org.litepal.crud.DataSupport;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {


    private RecordFragment mRecordFragment;
    private ChartFragment mChartFragment;

    public static long sStartTime = AppUtils.getTodayTime();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        ViewPager viewPager = findViewById(R.id.view_pager);
        mRecordFragment = new RecordFragment();
        mChartFragment = new ChartFragment();

        Fragment[] fragments = {mRecordFragment, mChartFragment};
        viewPager.setAdapter(new SimpleFragmentPagerAdapter(getSupportFragmentManager(), Arrays.asList(fragments), Arrays.asList("列表", "图表")));
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Observable.create((ObservableOnSubscribe<List<Record>>) emitter -> {
            List<Record> records = DataSupport
                    .where("packagename not null and starttime > ? and starttime < ?",
                            sStartTime + "",
                            (sStartTime + 1000 * 60 * 60 * 24) + "")
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
                            record.setIcon(ContextCompat.getDrawable(mActivity, R.mipmap.ic_launcher));
                            records.remove(i);
                            i--;
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
                    mRecordFragment.setData(records);
                    mChartFragment.setData(records);
                }, Throwable::printStackTrace);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.date:
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(sStartTime);
                DatePickerDialog dialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
                    sStartTime = AppUtils.getDayTime(year, month, dayOfMonth);
                    loadData();
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                dialog.show();
                return true;
            case R.id.setting:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
            case R.id.overlay:
                checkOverlayPermission();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                int REQUEST_CODE = 1;
                Toast.makeText(this, "请先授予 \"" + getString(R.string.app_name) + "\" 悬浮窗权限", Toast.LENGTH_LONG).show();
                startActivityForResult(
                        new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()))
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
                        REQUEST_CODE
                );
            } else {
                startService(new Intent(this, TrackerService.class).putExtra(TrackerService.COMMAND, TrackerService.COMMAND_OPEN));
            }
        } else {
            startService(new Intent(this, TrackerService.class).putExtra(TrackerService.COMMAND, TrackerService.COMMAND_OPEN));

        }
    }

}
