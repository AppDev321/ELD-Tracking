package com.dopsi.webapp.utils.chartUtil

object ChartUtils {
    const val CHART_ID_OFF_DUTY = "OFF_DUTY"
    const val CHART_ID_SLEEPER_BERTH = "SLEEPER_BERTH"
    const val CHART_ID_DRIVING = "DRIVING"
    const val CHART_ID_ON_DUTY = "ON_DUTY"

    const val STATUS_DRIVING = "DRIVING"
    const val STATUS_OFF_DUTY = "OFF_DUTY"
    const val STATUS_ON_DUTY = "ON_DUTY"
    const val STATUS_SLEEP = "SLEEP"

    val labelYSide = arrayOf(
        "SB",
        "ON",
        "D",
        "OFF",
    )

    val labels = arrayOf(
        "M",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11",
        "N",
        "1",
        "2",
        "3",
        "4",
        "5",
        "6",
        "7",
        "8",
        "9",
        "10",
        "11"
    )
}


data class DutyHour (
    private var dutyType:String,
    private var startTime:Long,
    private var endTime:Long,
)