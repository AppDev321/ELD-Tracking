package com.dopsi.webapp.bussinesslogic.model

import java.io.Serializable

data class WeekTimeModel(
    var totalWeeklyShiftHoursInMillis: Long = 0,
    var consumeTimeInMillis:Long = 0,
    var remainingTimeInMillis:Long = 0,
    var serverTimeInMillis:Long = 0,
    var weekCycleStartTimeInMillis:Long = 0,


    var consumedTime : String = "",
    var remainingTime : String = "",
    var serverCurrentTime: String ="",
    var weekCycleStartTime: String ="",
    var lastDayShiftStartTime : String = "",
    var lastDayShiftCompletedTime : String = "",
    var currentDayShiftStartTime : String = "",
    var currentDayShiftCompletedTime : String = "",




    var progressPercentage: Float = 0F,
    var totalWeekCycleHours: Int = 0,

    var lastShiftStartTimeInMillis:Long = 0,
    var lastShiftCompletedTimeInMillis:Long = 0,
    var lastSavedCycleTimeInMillis:Long = 0,
    var lastSavedCycleTime : String = "",

    ) : Serializable
