package com.dopsi.webapp.bussinesslogic.model

import java.io.Serializable

data class ShiftTimeModel(
    val totalShiftHoursInMillis: Long = 0,
    val consumeTimeInMillis:Long = 0,
    val remainingTimeInMillis:Long = 0,
    var serverTimeInMillis:Long = 0,
    val shiftStartTimeInMillis:Long = 0,


    val consumedTime : String = "",
    val remainingTime : String = "",
    val serverCurrentTime: String ="",
    val shiftStartTime: String ="",
    val progressPercentage: Float = 0F,
    val totalShiftHours: Int = 0,
) : Serializable
