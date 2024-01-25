package com.dopsi.webapp.bussinesslogic

import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.TimeManager
import com.dopsi.webapp.intefaces.TimerCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class DriveTimeClock(
    private val timeManager: TimeManager,
    private var model: DriveTimeModel,
    private val timerCallback: TimerCallback
) : Serializable {
    private var maxContinuesHourDrive = model.maxDriveHour
    private var continuesHour: Int = 0
    private val hoursInMilliseconds: Long = 60*1000//1 * 60 * 60 * 1000 // 1 hour

    private val totalWeekCycleHours = model.totalDriveHours * 60 * 60 * 1000
    private var totalConsumedTimeMillis: Long =
        timeManager.convertLongToTime(model.lastSavedDriveTime)
    private var job: Job? = null
    private var hourUpdateJob: Job? = null
    fun startDriveTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            hourUpdateJob = CoroutineScope(Dispatchers.Default).launch {
                while (isActive) {
                    delay(hoursInMilliseconds)
                    continuesHour++
                    if (continuesHour > maxContinuesHourDrive && model.isBreakConsumed.not()) {
                        //Trigger Alert for taking break
                        withContext(Dispatchers.Main){
                            timerCallback.onBreakTimeReached()
                        }
                    }
                }
            }
            while (isActive && totalConsumedTimeMillis < totalWeekCycleHours) {
                totalConsumedTimeMillis += DateTimeFormat.shiftUpdateIntervalTime
                val remainingTimeMillis = (totalWeekCycleHours) - totalConsumedTimeMillis
                val remainingTime = timeManager.convertMillisToTime(remainingTimeMillis)
                val consumedTime = timeManager.convertMillisToTime(totalConsumedTimeMillis)
                model.lastSavedDriveTime = consumedTime
                model.remainingTime = remainingTime
                model.consumedTime = model.lastSavedDriveTime
                model.consumeTimeInMillis = totalConsumedTimeMillis
                model.remainingTimeInMillis = remainingTimeMillis
                model.progressPercentage =
                    (totalConsumedTimeMillis.toFloat() / totalWeekCycleHours.toFloat()) * 100
                model.continuesDriveHour = continuesHour

                withContext(Dispatchers.Main) {
                    timerCallback.onDriveTimeTick(model.copy())
                }
                delay(DateTimeFormat.shiftUpdateIntervalTime)
            }

            job = null
            withContext(Dispatchers.Main) {
                timerCallback.onDriveTimeFinish()
            }
        }
    }


    /*
     Because hour job run in separate thread if
     parent close than it will not affect the hour
     job so that;s why we need to cancel hour job
     */

    fun stopCycle() {
        job?.cancel()
        hourUpdateJob?.cancel()
    }


    fun getDriveTimer(): DriveTimeModel {
        return this.model
    }

    fun breakTimeConsumed(isConsumed:Boolean)
    {
        model.isBreakConsumed = isConsumed
    }
}
