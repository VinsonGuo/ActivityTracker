package com.guoziwei.timerecorder.component.adapter

import android.text.format.DateUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.guoziwei.timerecorder.R
import com.guoziwei.timerecorder.bean.Record
import com.guoziwei.timerecorder.utils.AppUtils

/**
 * Created by guoziwei on 2018/4/3.
 */
class RecordDetailAdapter(records: MutableList<Record>)
    : BaseQuickAdapter<Record, BaseViewHolder>(R.layout.item_record_detail, records) {


    override fun convert(helper: BaseViewHolder, item: Record) {
        helper.setImageDrawable(R.id.iv_logo, item.icon)
                .setText(R.id.tv_name, item.appName)
                .setText(R.id.tv_package_name, item.packageName)
                .setText(R.id.tv_time, DateUtils.formatDateTime(mContext, item.startTime,
                                DateUtils.FORMAT_SHOW_TIME))
                .setText(R.id.tv_interval, AppUtils.getFormatTime(item.interval))
    }

}