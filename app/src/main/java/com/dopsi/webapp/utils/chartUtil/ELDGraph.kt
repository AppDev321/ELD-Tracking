package com.dopsi.webapp.utils.chartUtil

import android.content.Context
import android.graphics.Color
import com.dopsi.webapp.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis

import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject


class ELDGraph @Inject constructor(
    var context: Context,
) {

    fun showChart(chart: LineChart, dataSet: List<Entry>) {
        val barColors = context.getColor(R.color.seprator_color)
        chart.removeAllViews()
        val chartDataSet = LineDataSet(dataSet, "data")
        val data = LineData(chartDataSet)


        //*************Configure Chart Settings***********
        chart.axisRight.isEnabled = false
        chart.xAxis.position = XAxis.XAxisPosition.BOTTOM
        chart.description.isEnabled = false
        chart.legend.isEnabled = false
        chartDataSet.mode = LineDataSet.Mode.STEPPED
        chart.axisRight.isEnabled = false
        chart.description.isEnabled = false
        chart.isDoubleTapToZoomEnabled = false
        chart.setPinchZoom(false)
        chart.setScaleEnabled(false)
        chart.setTouchEnabled(false)
        chart.legend.isEnabled = false
        chart.dragDecelerationFrictionCoef = 0.25F
        chart.setDrawBorders(true)
        chart.setBorderWidth(0.5f)
        //******************************************

        //*********Chart line drawing Settings
        chartDataSet.circleRadius = 0.0F
        chartDataSet.setDrawCircles(false)
        chartDataSet.setDrawValues(false)
        chartDataSet.circleRadius = 0.0F
        chartDataSet.color = context.getColor(R.color.primary_button_color)
        chartDataSet.fillAlpha = 50
        chartDataSet.lineWidth = 2.0f
        //******************************************


        //******** Configure XAxisBottomSize
        val labelXAxis = XAxisFormatter()
        labelXAxis.values = ChartUtils.labels
        val xAxis: XAxis = chart.xAxis
        chart.xAxis.valueFormatter = labelXAxis
        xAxis.textSize = 12.0f
        xAxis.spaceMax = 2f
        //  xAxis.axisLineWidth = 2.0f
        xAxis.axisLineColor = barColors
        xAxis.setDrawAxisLine(true)
        xAxis.setDrawGridLines(true)
        // xAxis.gridColor = -3355444
        // xAxis.gridLineWidth = 2.0f
        xAxis.xOffset = 0.5f
        xAxis.yOffset = 0.0F
        xAxis.position = XAxis.XAxisPosition.TOP
        xAxis.axisMaximum = 23.0F
        xAxis.labelCount = 23
        //******************************************


        //**********Configure YAxisRightSide
        val yRightAxis = chart.axisRight
        yRightAxis.xOffset = 5.0F
        yRightAxis.yOffset = 25.0F
        yRightAxis.valueFormatter = LogbookNamesFormatter()
        yRightAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yRightAxis.labelCount = ChartUtils.labelYSide.size - 1
        yRightAxis.axisMaximum = ChartUtils.labelYSide.size - 1 * 1.0f
        yRightAxis.setDrawGridLines(true)
        yRightAxis.axisLineWidth = 2.0F

        //******************************************

        //*************Configure YAxisLeftSide
        val yLeftAxis = chart.axisLeft
        yLeftAxis.textSize = 12.0f
        // yLeftAxis.axisLineWidth = 2.0f
        yLeftAxis.axisMaximum = ChartUtils.labelYSide.size - 1 * 1.0f
        yLeftAxis.setDrawGridLines(true)
        // yLeftAxis.gridLineWidth = 2.0f
        yLeftAxis.valueFormatter = LogbookNamesFormatter()
        yLeftAxis.xOffset = 5.0f
        yLeftAxis.yOffset = 25.0f
        yLeftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yLeftAxis.axisLineColor = barColors
        yLeftAxis.labelCount = ChartUtils.labelYSide.size - 1

        //******************************************

        chart.data = data
        //    setDataSetConfig(chart,dataSet)
        chart.notifyDataSetChanged()
    }

    fun setDataSetConfig(chart: LineChart, paramList: List<Entry>) {

        val stringBuilder = StringBuilder()
        stringBuilder.append("Driver")
        stringBuilder.append(" ")
        stringBuilder.append("Last")
        stringBuilder.append(" (")
        stringBuilder.append("HOS")
        stringBuilder.append(")")
        val lineDataSet = LineDataSet(paramList, stringBuilder.toString())
        lineDataSet.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet.setCircleColor(-16777216)
        lineDataSet.lineWidth = 3.0f
        lineDataSet.circleSize = 3.0f
        lineDataSet.fillAlpha = 65

        lineDataSet.highLightColor = Color.rgb(244, 117, 117)
        lineDataSet.setDrawCircleHole(false)
        lineDataSet.setDrawCircles(false)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawFilled(false)
        /*val arrayList: ArrayList<Int> = getColors(paramInt, 0, paramList)
        if (arrayList.size > 0) {
            lineDataSet.colors = arrayList
        } else {
            lineDataSet.color = paramInt
        }*/

        val lineData = chart.data
        if (lineData != null) {
            lineData.addDataSet(lineDataSet)
        } else {
            val arrayList1: ArrayList<LineDataSet> = arrayListOf()
            arrayList1.add(lineDataSet)
            val arrayList2: ArrayList<String> = arrayListOf()
            var paramInt = 0
            while (paramInt < paramList.size) {
                val stepLineFormatDate: StepLineFormatDate =
                    paramList[paramInt].data as StepLineFormatDate
                val stringBuilder1 = StringBuilder()
                stringBuilder1.append("")
                stringBuilder1.append(stepLineFormatDate.toCarrierString())
                arrayList2.add(stringBuilder1.toString())
                paramInt++
            }
            /* val lineData1 = LineDataSet(arrayList2, arrayList1)
             lineData1.setValueTextSize(12.0f)
             lineData1.setValueTextColor(-16777216)*/
            chart.setData(lineData)
        }
    }

    fun clear(chart: LineChart) {
        chart.clear()
    }

    private class MyValueFormatter : ValueFormatter() {

        override fun getFormattedValue(value: Float): String {
            return ChartUtils.labels[value.toInt()]
        }
    }

    class XAxisTimeFormatter : ValueFormatter() {
        override fun getAxisLabel(value: Float, axis: AxisBase?): String {
            return SimpleDateFormat("HH:MM", Locale.getDefault()).format(Date(value.toLong()))
        }
    }

    class XAxisFormatter : IndexAxisValueFormatter() {
        override fun setValues(values: Array<out String>?) {

            super.setValues(values)
        }

        override fun getValues(): Array<String> {
            return super.getValues()
        }

        override fun getFormattedValue(value: Float): String {
            return super.getFormattedValue(value)
        }
    }

    class YAxisFormatter : IndexAxisValueFormatter() {
        override fun setValues(values: Array<String>) {

            super.setValues(values)
        }

        override fun getValues(): Array<String> {
            return super.getValues()
        }

        override fun getFormattedValue(value: Float): String {
            return super.getFormattedValue(value)
        }
    }


    class LogbookNamesFormatter : ValueFormatter() {
        override fun getFormattedValue(paramFloat: Float): String {
            val index = (paramFloat + 0.5f).toInt()
            return if (index < ChartUtils.labelYSide.size && index >= 0)
                ChartUtils.labelYSide[index]
            else ""
        }
    }

}