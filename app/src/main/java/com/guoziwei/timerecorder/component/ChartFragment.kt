package com.guoziwei.timerecorder.component


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.guoziwei.timerecorder.bean.Record
import com.guoziwei.timerecorder.utils.AppUtils


/**
 * A simple [Fragment] subclass.
 */
class ChartFragment : Fragment() {


    private var barChart: BarChart? = null
    private val mData: MutableList<Record> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        barChart = BarChart(activity)
        barChart!!.setDrawGridBackground(false)
        barChart!!.description.isEnabled = false
        barChart!!.legend.isEnabled = false
        barChart!!.isScaleYEnabled = false
        barChart!!.xAxis.position = XAxis.XAxisPosition.BOTTOM
        barChart!!.xAxis.setDrawGridLines(false)
        barChart!!.xAxis.labelRotationAngle = 60f
        barChart!!.xAxis.granularity = 1f
        barChart!!.xAxis.setValueFormatter { value, _ ->
            val index: Int = value.toInt()
            if (index >= 0 && index < mData.size) {
                return@setValueFormatter mData[index].appName.toString()
            }
            return@setValueFormatter ""
        }
        barChart!!.axisRight.setDrawGridLines(false)
        barChart!!.axisRight.setDrawAxisLine(false)
        barChart!!.axisRight.setDrawLabels(false)

        barChart!!.axisLeft.setDrawGridLines(false)
        barChart!!.axisLeft.axisMinimum = 0f
        barChart!!.axisLeft.setValueFormatter { value, _ ->
            return@setValueFormatter AppUtils.getFormatTime(value.toLong())
        }
        return barChart
    }

    fun setData(records: List<Record>) {
        mData.clear()
        mData.addAll(records)
        val data = BarData()
        val list: MutableList<BarEntry> = MutableList(records.size, { i ->
            BarEntry(i.toFloat(), records[i].totalTime.toFloat())
        })
        val dataSet = BarDataSet(list, "统计")
        dataSet.setDrawValues(false)
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.asList()
        data.addDataSet(dataSet)
        barChart?.data = data
        barChart?.setVisibleXRange(5f, 5f)
        barChart?.notifyDataSetChanged()
        barChart?.invalidate()
    }

}// Required empty public constructor
