package com.dopsi.webapp.bussinesslogic.model

import java.io.Serializable

data class WeekTimeModel(
    val totalWeeklyShiftHoursInMillis: Long = 0,
    val consumeTimeInMillis:Long = 0,
    val remainingTimeInMillis:Long = 0,
    var serverTimeInMillis:Long = 0,
    val weekCycleStartTimeInMillis:Long = 0,


    val consumedTime : String = "",
    val remainingTime : String = "",
    val serverCurrentTime: String ="",
    val weekCycleStartTime: String ="",
    val lastDayShiftStartTime : String = "",
    val lastDayShiftCompletedTime : String = "",
    val currentDayShiftStartTime : String = "",
    val currentDayShiftCompletedTime : String = "",




    val progressPercentage: Float = 0F,
    val totalWeekCycleHours: Int = 0,

    val lastShiftStartTimeInMillis:Long = 0,
    val lastShiftCompletedTimeInMillis:Long = 0,
    val lastSavedCycleTimeInMillis:Long = 0,
    val lastSavedCycleTime : String = "",

    ) : Serializable
