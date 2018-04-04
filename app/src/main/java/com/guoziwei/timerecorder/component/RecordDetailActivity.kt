package com.guoziwei.timerecorder.component

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.guoziwei.timerecorder.R
import com.guoziwei.timerecorder.bean.Record
import com.guoziwei.timerecorder.component.adapter.RecordDetailAdapter
import com.guoziwei.timerecorder.utils.AppUtils
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.litepal.crud.DataSupport

class RecordDetailActivity : BaseActivity() {


    companion object {
        fun launch(context: Context, packageName: String) {
            val intent = Intent(context, RecordDetailActivity::class.java)
            intent.putExtra(DATA, packageName)
            context.startActivity(intent)

        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView = RecyclerView(this)
        setContentView(recyclerView)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val packageName = intent.getStringExtra(DATA)
        recyclerView.layoutManager = LinearLayoutManager(this)

        Observable.create({ emitter: ObservableEmitter<MutableList<Record>> ->
            val records = DataSupport
                    .where("packagename not null and starttime > ? and starttime < ? and packagename = ?",
                            MainActivity.sStartTime.toString(),
                            (MainActivity.sStartTime + 1000 * 60 * 60 * 24).toString(),
                            packageName)
                    .order("starttime desc")
                    .find(Record::class.java)
            if (!emitter.isDisposed) {
                emitter.onNext(records)
            }
        })
                .map({ records: MutableList<Record> ->
                    val infoMap = AppUtils.getInstallAppInfo(mActivity)
                    for (i in records.indices) {
                        val record = records[i]

                        val info = infoMap[record.packageName]
                        record.appName = info!!.loadLabel(mActivity.packageManager)
                        record.icon = info.loadIcon(mActivity.packageManager)
                    }
                    return@map records
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ records: MutableList<Record> ->
                    val adapter = RecordDetailAdapter(records)
                    adapter.bindToRecyclerView(recyclerView)
                    adapter.setEmptyView(R.layout.item_empty_view)
                }, { t: Throwable -> t.printStackTrace() })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}
