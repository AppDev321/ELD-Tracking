package com.dopsi.webapp.bussinesslogic.model

import com.dopsi.webapp.bussinesslogic.AppConfigurations
import java.io.Serializable

data class WeekTimeModel(
    var totalWeeklyShiftHoursInMillis: Long = 0,
    var consumeTimeInMillis: Long = 0,
    var remainingTimeInMillis: Long = 0,
    var serverTimeInMillis: Long = 0,
    var weekCycleStartTimeInMillis: Long = 0,


    var consumedTime: String = AppConfigurations.defaultTimeValue,
    var remainingTime: String = AppConfigurations.defaultTimeValue,
    var serverCurrentTime: String = AppConfigurations.defaultTimeValue,
    var weekCycleStartTime: String = AppConfigurations.defaultTimeValue,
    var lastDayShiftStartTime: String = AppConfigurations.defaultTimeValue,
    var lastDayShiftCompletedTime: String = AppConfigurations.defaultTimeValue,
    var currentDayShiftStartTime: String = AppConfigurations.defaultTimeValue,
    var currentDayShiftCompletedTime: String = AppConfigurations.defaultTimeValue,


    var progressPercentage: Float = 0F,
    var totalWeekCycleHours: Int = AppConfigurations.totalWeekCycleHours,

    var lastShiftStartTimeInMillis: Long = 0,
    var lastShiftCompletedTimeInMillis: Long = 0,
    var lastSavedCycleTimeInMillis: Long = 0,
    var lastSavedCycleTime: String = AppConfigurations.defaultTimeValue,

    ) : Serializable
