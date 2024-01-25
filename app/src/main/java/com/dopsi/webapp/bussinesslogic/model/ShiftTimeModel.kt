package com.dopsi.webapp.bussinesslogic.model

import com.dopsi.webapp.bussinesslogic.AppConfigurations
import java.io.Serializable

data class ShiftTimeModel(
    val totalShiftHoursInMillis: Long = 0,
    val consumeTimeInMillis: Long = 0,
    val remainingTimeInMillis: Long = 0,
    var serverTimeInMillis: Long = 0,
    val shiftStartTimeInMillis: Long = 0,


    val consumedTime: String = AppConfigurations.defaultTimeValue,
    val remainingTime: String = AppConfigurations.defaultTimeValue,
    val serverCurrentTime: String = AppConfigurations.defaultTimeValue,
    val shiftStartTime: String = AppConfigurations.defaultTimeValue,
    val progressPercentage: Float = 0F,
    val totalShiftHours: Int = AppConfigurations.totalShiftHours,
) : Serializable
