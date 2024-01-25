package com.dopsi.webapp.bussinesslogic.model

import com.dopsi.webapp.bussinesslogic.AppConfigurations
import java.io.Serializable

data class BreakTimeModel(
    var totalBreakTimeInMillis: Long = 0,
    var consumeTimeInMillis:Long = 0,
    var remainingTimeInMillis:Long = 0,

    var consumedTime : String = AppConfigurations.defaultTimeValue,
    var remainingTime : String = AppConfigurations.defaultTimeValue,

    var progressPercentage: Float = 0F,
    var totalBreakMin: Int = AppConfigurations.breakTimeInMin,

    ) : Serializable
