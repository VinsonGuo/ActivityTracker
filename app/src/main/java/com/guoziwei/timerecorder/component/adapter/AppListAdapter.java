package com.guoziwei.timerecorder.component.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.guoziwei.timerecorder.R;
import com.guoziwei.timerecorder.bean.Record;
import com.guoziwei.timerecorder.utils.AppUtils;

import java.util.List;

/**
 * Created by guoziwei on 2018/4/1.
 */

public class AppListAdapter extends BaseQuickAdapter<Record, BaseViewHolder> {


    public AppListAdapter(@Nullable List<Record> data) {
        super(R.layout.item_app_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Record item) {
        String time = AppUtils.getFormatTime(item.getTotalTime());
        helper.setImageDrawable(R.id.iv_logo, item.getIcon())
                .setText(R.id.tv_name, item.getAppName())
                .setText(R.id.tv_package_name, item.getPackageName())
                .setText(R.id.tv_time, time);
    }
}
