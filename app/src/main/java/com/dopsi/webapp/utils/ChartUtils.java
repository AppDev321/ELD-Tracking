package com.dopsi.webapp.utils;


import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;

import com.dopsi.webapp.R;
import com.dopsi.webapp.utils.chart.ChartConstants;
import com.dopsi.webapp.utils.chart.DutyHour;
import com.dopsi.webapp.utils.chart.DutyStatus;
import com.scichart.charting.model.dataSeries.XyDataSeries;
import com.scichart.charting.numerics.labelProviders.DateLabelProvider;
import com.scichart.charting.numerics.labelProviders.ILabelFormatter;
import com.scichart.charting.numerics.labelProviders.NumericLabelProvider;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.charting.visuals.axes.AxisAlignment;
import com.scichart.charting.visuals.axes.AxisTitleOrientation;
import com.scichart.charting.visuals.axes.AxisTitlePlacement;
import com.scichart.charting.visuals.axes.IAxis;
import com.scichart.charting.visuals.axes.IDateAxis;
import com.scichart.charting.visuals.axes.INumericAxis;
import com.scichart.charting.visuals.renderableSeries.FastLineRenderableSeries;
import com.scichart.core.framework.UpdateSuspender;
import com.scichart.core.model.DoubleValues;
import com.scichart.core.utility.ComparableUtil;
import com.scichart.drawing.common.PenStyle;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;


public class ChartUtils {
    String[] labels = {"M", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "N", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11"};


    public void initHOSChart(Context context, final SciChartSurface surface, ArrayList<DutyHour> dutyHours, Map<String, String> dutyTimes) {
        SciChartBuilder.init(context);
        Date minorDelta = new Date();
        minorDelta.setHours(0);
        minorDelta.setMinutes(30);
        minorDelta.setSeconds(0);

        Date majorDelta = new Date();
        majorDelta.setHours(0);
        majorDelta.setMinutes(30);
        majorDelta.setSeconds(0);

        Date min = new Date();
        min.setHours(0);
        min.setMinutes(0);
        min.setSeconds(0);

        Date max = new Date();
        max.setHours(24);
        max.setMinutes(0);
        max.setSeconds(0);

        final SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

        final IAxis xAxis = sciChartBuilder.newDateAxis()
                .withVisibleRange(min, max)
                .withAxisAlignment(AxisAlignment.Bottom)
                .withMinorDelta(minorDelta)
                .withMajorDelta(majorDelta)
                .withAutoTicks(true)
                .withDrawMajorBands(false)
                .withDrawMajorTicks(false)
                .withDrawMinorTicks(false)
                .withIsLabelCullingEnabled(false)
                .build();

        final IAxis leftYAxis = sciChartBuilder.newNumericAxis()
                .withVisibleRange(-0.5, 3.5)
                .withAutoTicks(false)
                .withMajorDelta(1d)
                .withMinorDelta(0.5)
                .withAxisId("LeftAxis")
                .withDrawMinorTicks(false)
                .withDrawMajorBands(false)
                .withDrawMajorTicks(false)
                .withDrawMajorGridLines(false)
                .withAxisAlignment(AxisAlignment.Left)
                .build();

        final IAxis rightYAxis = sciChartBuilder.newNumericAxis()
                .withVisibleRange(-0.5, 3.5)
                .withAutoTicks(false)
                .withMajorDelta(1d)
                .withMinorDelta(0.5)
                .withDrawMinorTicks(false)
                .withDrawMajorBands(false)
                .withDrawMajorTicks(false)
                .withDrawMajorGridLines(false)
                .withAxisId("RightAxis")
                .build();

        xAxis.setLabelProvider(new DateLabelProvider(new XAxisLabelFormatter()));
        leftYAxis.setLabelProvider(new NumericLabelProvider(new YAxisLabelFormatter(Arrays.asList("Off", "SB", "D", "ON"))));
        rightYAxis.setLabelProvider(new NumericLabelProvider(new YAxisLabelFormatter(
                Arrays.asList(dutyTimes.get(ChartConstants.CHART_ID_OFF_DUTY),
                        dutyTimes.get(ChartConstants.CHART_ID_SLEEPER_BERTH),
                        dutyTimes.get(ChartConstants.CHART_ID_DRIVING),
                        dutyTimes.get(ChartConstants.CHART_ID_ON_DUTY)))));

        rightYAxis.setAxisTitle("");
        rightYAxis.setAxisTitlePlacement(AxisTitlePlacement.Top);
        rightYAxis.setAxisTitleOrientation(AxisTitleOrientation.Horizontal);
        rightYAxis.setAxisTitleGravity(Gravity.TOP | Gravity.END);

        final PenStyle minorGridLineStyle = sciChartBuilder.newPen().withColor(Color.BLACK).withThickness(0.5f).withAntiAliasing(true).build();
        xAxis.setMajorGridLineStyle(minorGridLineStyle);
        leftYAxis.setMinorGridLineStyle(minorGridLineStyle);
        rightYAxis.setMinorGridLineStyle(minorGridLineStyle);

        XyDataSeries dataSeries = sciChartBuilder.newXyDataSeries(Date.class, Double.class).build();
        dataSeries.setAcceptsUnsortedData(true);

        for (DutyHour dutyHour : dutyHours) {
            double y = -1;
            if (dutyHour.getDutyType().equals(DutyStatus.STATUS_DRIVING))
                y = 2;
            if (dutyHour.getDutyType().equals(DutyStatus.STATUS_OFF_DUTY)) {
                Log.w("ERRORR", dutyHour.getDutyType());
                y = 0;
            }
            if (dutyHour.getDutyType().equals(DutyStatus.STATUS_ON_DUTY))
                y = 3;
            if (dutyHour.getDutyType().equals(DutyStatus.STATUS_SLEEP))
                y = 1;

            if (y > -1)
                for (Date x = new Date(dutyHour.getStartTime()); x.getTime() < dutyHour.getEndTime(); x.setTime(x.getTime() + 3600000)) {
                    dataSeries.append(x, y);
                }
        }

        final FastLineRenderableSeries rSeries = sciChartBuilder.newLineSeries().withYAxisId("LeftAxis").withDataSeries(dataSeries).withStrokeStyle(context.getColor(R.color.primary_button_color), 1.5f, true).withIsDigitalLine(true).build();

        UpdateSuspender.using(surface, new Runnable() {
            @Override
            public void run() {
               surface.getXAxes().clear();
              //surface.setTheme(com.scichart.charting.R.style.SciChart_ElectricStyle);
              Collections.addAll(surface.getXAxes(), xAxis);
               Collections.addAll(surface.getYAxes(), leftYAxis, rightYAxis);
                Collections.addAll(surface.getRenderableSeries(), rSeries);
            }
        });
    }

    class XAxisLabelFormatter implements ILabelFormatter<IDateAxis> {

        @Override
        public void update(IDateAxis dateAxis) {

        }

        @Override
        public boolean update(List<CharSequence> list, DoubleValues doubleValues) {
            return false;
        }

  /*  @Override
    public CharSequence formatLabel(Comparable dataValue) {
        return format(ComparableUtil.toDate(dataValue).getHours());
    }

    @Override
    public CharSequence formatCursorLabel(Comparable dataValue) {
        return format(ComparableUtil.toDate(dataValue).getHours());
    }*/

        private CharSequence format(int dataValue) {
            Log.e("TAG",""+dataValue);
            return labels[dataValue];
        }

        @Override
        public CharSequence formatLabel(double v) {
            return format(ComparableUtil.toDate(v).getHours());
        }

        @Override
        public CharSequence formatCursorLabel(double v) {
            return format(ComparableUtil.toDate(v).getHours());
        }
    }

    class YAxisLabelFormatter implements ILabelFormatter<INumericAxis> {

        private final List<String> labels;

        YAxisLabelFormatter(List<String> labels) {

            this.labels = labels;
        }

        @Override
        public void update(INumericAxis axis) {

        }

        @Override
        public boolean update(List<CharSequence> list, DoubleValues doubleValues) {
            return false;
        }
/*

    @Override
    public CharSequence formatLabel(Comparable dataValue) {
        return format((Double) dataValue);
    }

    @Override
    public CharSequence formatCursorLabel(Comparable dataValue) {
        return format((Double) dataValue);
    }
*/

        private CharSequence format(Double dataValue) {
            final int index = dataValue.intValue();

            if (index < labels.size()) {
                return labels.get(index);
            } else {
                return "";
            }
        }

        @Override
        public CharSequence formatLabel(double v) {
            return format(v);
        }

        @Override
        public CharSequence formatCursorLabel(double v) {
            return format(v);
        }
    }


}



