package com.guoziwei.timerecorder.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class AppUtils {


    private static final String LogTag = "AppUtils";

    public static Map<String, ApplicationInfo> getInstallAppInfo(Context context) {
        Map<String, ApplicationInfo> map = new HashMap<>(50);
        PackageManager mypm = context.getPackageManager();
        List<ApplicationInfo> appInfoList = mypm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);

        for (ApplicationInfo app : appInfoList) {
            map.put(app.packageName, app);
        }

        return map;
    }

    public static long getTodayTime() {
        Calendar now = Calendar.getInstance();
        Calendar today = Calendar.getInstance();
        today.clear();
        today.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        return today.getTimeInMillis();
    }

    public static String getFormatTime(long ms) {
        String time;
        long hour = 60 * 60 * 1000;
        if (ms >= hour) {
            time = ms / hour + "小时" + (ms % hour) / (60 * 1000) + "分钟";
        } else if (ms >= 60 * 1000) {
            time = ms / (60 * 1000) + "分钟";
        } else {
            time = ms / 1000 + "秒";
        }
        return time;
    }

}
