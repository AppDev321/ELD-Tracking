package com.dopsi.webapp.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dopsi.webapp.R;
import com.dopsi.webapp.utils.ChartUtils;
import com.dopsi.webapp.utils.chart.DutyHour;
import com.scichart.charting.visuals.SciChartSurface;
import com.scichart.extensions.builders.SciChartBuilder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static com.dopsi.webapp.utils.chart.ChartConstants.CHART_ID_DRIVING;
import static com.dopsi.webapp.utils.chart.ChartConstants.CHART_ID_OFF_DUTY;
import static com.dopsi.webapp.utils.chart.ChartConstants.CHART_ID_ON_DUTY;
import static com.dopsi.webapp.utils.chart.ChartConstants.CHART_ID_SLEEPER_BERTH;
import static com.dopsi.webapp.utils.chart.DutyStatus.STATUS_DRIVING;
import static com.dopsi.webapp.utils.chart.DutyStatus.STATUS_OFF_DUTY;

public class LineChartFragment extends Fragment {

    SciChartBuilder sciChartBuilder = SciChartBuilder.instance();

    SciChartSurface surface;
    View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sciChartBuilder = SciChartBuilder.instance();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_eld_logs, container, false);

        surface = view.findViewById(R.id.surface);
        surface.setBackgroundColor(getContext().getColor(R.color.white));


        // Dummy data for dutyHours list
        ArrayList<DutyHour> dutyHourList = new ArrayList<>();

        Date originalDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(originalDate);
        calendar.add(Calendar.HOUR_OF_DAY, -1);
        Date startDrivering = calendar.getTime();


        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(originalDate);
        calendar2.add(Calendar.HOUR_OF_DAY, -12);
        calendar2.add(Calendar.MINUTE, 60);
        Date startOff = calendar2.getTime();
        dutyHourList.add(new DutyHour(STATUS_OFF_DUTY, startOff.getTime(), startDrivering.getTime()));
        dutyHourList.add(new DutyHour(STATUS_DRIVING, startDrivering.getTime(), new Date().getTime()));
// Add more DutyHour objects as needed

// Dummy data for dutyTimes map
        Map<String, String> dutyTimesMap = new HashMap<>();
        dutyTimesMap.put(CHART_ID_OFF_DUTY, "" + startOff.getHours());
        dutyTimesMap.put(CHART_ID_SLEEPER_BERTH, "00:00");
        dutyTimesMap.put(CHART_ID_DRIVING, "" + startDrivering.getHours());
        dutyTimesMap.put(CHART_ID_ON_DUTY, "00:00");


        new ChartUtils().initHOSChart(requireContext(), surface, dutyHourList, dutyTimesMap);


        return view;
    }


}