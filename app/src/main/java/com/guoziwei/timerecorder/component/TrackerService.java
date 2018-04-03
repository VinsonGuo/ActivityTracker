package com.guoziwei.timerecorder.component;

import android.accessibilityservice.AccessibilityService;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import com.guoziwei.timerecorder.bean.Record;
import com.guoziwei.timerecorder.view.TrackerWindowManager;

import org.litepal.crud.DataSupport;

import de.greenrobot.event.EventBus;

public class TrackerService extends AccessibilityService {
    public static final String TAG = "TrackerService";
    public static final String COMMAND = "COMMAND";
    public static final String COMMAND_OPEN = "COMMAND_OPEN";
    public static final String COMMAND_CLOSE = "COMMAND_CLOSE";
    TrackerWindowManager mTrackerWindowManager;

    private long mStartTime;

    private String mLastPackageName;

    @Override
    public void onCreate() {
        super.onCreate();
        mStartTime = System.currentTimeMillis();
    }

    private void initTrackerWindowManager() {
        if (mTrackerWindowManager == null)
            mTrackerWindowManager = new TrackerWindowManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand");
        initTrackerWindowManager();

        String command = intent.getStringExtra(COMMAND);
        if (command != null) {
            if (command.equals(COMMAND_OPEN))
                mTrackerWindowManager.addView();
            else if (command.equals(COMMAND_CLOSE))
                mTrackerWindowManager.removeView();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onInterrupt() {

    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        Log.d(TAG, "onAccessibilityEvent: " + event.getPackageName());
        if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {

            String packageName = event.getPackageName().toString();
            EventBus.getDefault().post(new ActivityChangedEvent(
                    packageName,
                    event.getClassName().toString()
            ));

            if (!TextUtils.equals(mLastPackageName, packageName)) {
                long now = System.currentTimeMillis();
                long interval = now - mStartTime;
                Record record = new Record();
                record.setStartTime(mStartTime);
                record.setInterval(interval);
                record.setPackageName(mLastPackageName);
                record.save();

                mLastPackageName = packageName;
                mStartTime = System.currentTimeMillis();

            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    public static class ActivityChangedEvent {
        private final String mPackageName;
        private final String mClassName;

        public ActivityChangedEvent(String packageName, String className) {
            mPackageName = packageName;
            mClassName = className;
        }

        public String getPackageName() {
            return mPackageName;
        }

        public String getClassName() {
            return mClassName;
        }
    }
}
