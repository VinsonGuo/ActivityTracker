package com.guoziwei.timerecorder.component.adapter;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guoziwei.timerecorder.R;

import java.util.List;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class AppListAdapter extends BaseQuickAdapter<ApplicationInfo, BaseViewHolder> {
    private PackageManager mPackageManager;

    public AppListAdapter(@Nullable List<ApplicationInfo> data, PackageManager packageManager) {
        super(R.layout.item_app_list, data);
        mPackageManager = packageManager;
    }

    @Override
    protected void convert(BaseViewHolder helper, ApplicationInfo item) {
            helper.setImageDrawable(R.id.iv_logo, item.loadIcon(mPackageManager))
                    .setText(R.id.tv_name, item.loadLabel(mPackageManager))
                    .setText(R.id.tv_package_name, item.packageName);
    }
}
