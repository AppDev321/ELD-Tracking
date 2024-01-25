package com.dopsi.webapp.bussinesslogic.model

import java.io.Serializable

data class DriveTimeModel(
    var totalDriveShiftHoursInMillis: Long = 0,
    var consumeTimeInMillis:Long = 0,
    var remainingTimeInMillis:Long = 0,
    var serverTimeInMillis:Long = 0,

    var consumedTime : String = "",
    var remainingTime : String = "",
    var serverCurrentTime: String ="",
    var driveStartTime: String ="",
    var driveEndTime: String ="",
    var progressPercentage: Float = 0F,
    var totalDriveHours: Int = 0,
    var continuesDriveHour :Int = 0,

    var lastSavedDriveTimeInMillis:Long = 0,
    var lastSavedDriveTime : String = "",

    ) : Serializable
