package com.dopsi.webapp.fragment

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.core.content.ContextCompat
import com.core.base.BaseFragment
import com.dopsi.webapp.R

import com.dopsi.webapp.databinding.FragmentEldLogsBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter


class ELDLogFragment : BaseFragment<FragmentEldLogsBinding>(FragmentEldLogsBinding::inflate) {
    private val dataSet: MutableList<Int> = mutableListOf()
    override fun initUserInterface(view: View?) {


        val webSettings: WebSettings = viewDataBinding.graphWebView.settings
        webSettings.javaScriptEnabled = true
        viewDataBinding.graphWebView.webChromeClient = WebChromeClient()
        viewDataBinding.graphWebView.loadUrl("file:///android_asset/index.html")


        dataSet.clear()
        for (x in 1..100) {
            dataSet.add((-10..100).random())
        }
        showChart()

    }
    private fun showChart() {
        val chart = viewDataBinding.chart
        chart.removeAllViews()

        val chartData = dataSet.mapIndexed { index, value ->
            Entry(index.toFloat(), value.toFloat())
        }

        val chartDataSet = LineDataSet(chartData, "data")
        val data = LineData(chartDataSet)
        chart.data = data

        chart.axisRight.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.description.isEnabled = false
        chart.legend.isEnabled = false

        chartDataSet.circleRadius = 0.0F
        chartDataSet.setDrawCircles(false)
        chartDataSet.setDrawValues(false)

        chartDataSet.color = Color.GREEN
        chartDataSet.setDrawFilled(true)
        chartDataSet.fillColor = Color.GREEN
        chartDataSet.fillAlpha = 50

        chart.axisLeft.labelCount = 3
        chartDataSet.mode = LineDataSet.Mode.STEPPED


        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false

        chart.legend.isEnabled = false

        chartDataSet.setDrawCircles(false)
        chartDataSet.circleRadius = 0.0F
        chartDataSet.color = Color.GREEN
        chartDataSet.fillAlpha = 50


        chart.axisLeft.axisMaximum = 23.0F
        chart.axisLeft.axisMinimum = 0.0F
        chart.axisLeft.labelCount = 5
        chart.xAxis.valueFormatter = MyValueFormatter()
        chart.notifyDataSetChanged()
    }


    private class MyValueFormatter() : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return "A"
        }
    }
}