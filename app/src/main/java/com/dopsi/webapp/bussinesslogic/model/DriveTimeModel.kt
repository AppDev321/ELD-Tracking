package com.dopsi.webapp.bussinesslogic.model

import com.dopsi.webapp.bussinesslogic.AppConfigurations
import java.io.Serializable

data class DriveTimeModel(
    var totalDriveShiftHoursInMillis: Long = 0,
    var consumeTimeInMillis: Long = 0,
    var remainingTimeInMillis: Long = 0,
    var serverTimeInMillis: Long = 0,

    var consumedTime: String = AppConfigurations.defaultTimeValue,
    var remainingTime: String = AppConfigurations.defaultTimeValue,
    var serverCurrentTime: String = AppConfigurations.defaultTimeValue,
    var driveStartTime: String = AppConfigurations.defaultTimeValue,
    var driveEndTime: String = AppConfigurations.defaultTimeValue,
    var progressPercentage: Float = 0F,
    var totalDriveHours: Int = AppConfigurations.totalDriveHours,
    var continuesDriveHour: Int = 0,
    var maxDriveHour: Int = AppConfigurations.maxDriveTime,
    var lastSavedDriveTimeInMillis: Long = 0,
    var lastSavedDriveTime: String = AppConfigurations.defaultTimeValue,
    var isBreakConsumed :Boolean = false

    ) : Serializable
