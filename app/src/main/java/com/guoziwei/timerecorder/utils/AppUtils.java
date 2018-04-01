package com.guoziwei.timerecorder.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class AppUtils {


    private static final String LogTag = "AppUtils";

    public static List<ApplicationInfo> getInstallAppInfo(Context context) {
        PackageManager mypm = context.getPackageManager();
        List<ApplicationInfo> appInfoList = mypm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        Collections.sort(appInfoList, new ApplicationInfo.DisplayNameComparator(mypm));// 排序

        for(ApplicationInfo app: appInfoList) {
            //Log.v(LogTag, "RunningAppInfoParam  getInstallAppInfo app label = " + (String)app.loadLabel(umpm));
            //Log.v(LogTag, "RunningAppInfoParam  getInstallAppInfo app packageName = " + app.packageName);
        }

        return appInfoList;
    }

    //获取第三方应用信息
    public static ArrayList<String> getThirdAppInfo(Context context) {
        List<ApplicationInfo> appList = getInstallAppInfo(context);
        List<ApplicationInfo> thirdAppList = new ArrayList<ApplicationInfo>();
        thirdAppList.clear();
        for (ApplicationInfo app : appList) {
            //非系统程序
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                thirdAppList.add(app);
            }
            //本来是系统程序，被用户手动更新后，该系统程序也成为第三方应用程序了
            else if ((app.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0){
                thirdAppList.add(app);
            }
        }
        PackageManager mypm = context.getPackageManager();
        ArrayList<String> thirdAppNameList = new ArrayList<String>();
        for(ApplicationInfo app : thirdAppList) {
            Log.v(LogTag, "RunningAppInfoParam getThirdAppInfo app label = " + (String)app.loadLabel(mypm));
            thirdAppNameList.add((String)app.loadLabel(mypm));
        }

        return thirdAppNameList;
    }

    //获取系统应用信息
    public static ArrayList<String> getSystemAppInfo(Context context) {
        List<ApplicationInfo> appList = getInstallAppInfo(context);
        List<ApplicationInfo> sysAppList = new ArrayList<ApplicationInfo>();
        sysAppList.clear();
        for (ApplicationInfo app : appList) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                sysAppList.add(app);
            }
        }
        PackageManager mypm = context.getPackageManager();
        ArrayList<String> sysAppNameList = new ArrayList<String>();
        for(ApplicationInfo app : sysAppList) {
            Log.v(LogTag, "RunningAppInfoParam getThirdAppInfo app label = " + (String)app.loadLabel(mypm));
            sysAppNameList.add((String)app.loadLabel(mypm));
        }

        return sysAppNameList;

    }
}
