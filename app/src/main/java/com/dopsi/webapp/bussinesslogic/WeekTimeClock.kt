package com.dopsi.webapp.bussinesslogic

import com.dopsi.webapp.bussinesslogic.model.TimeManager
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import com.dopsi.webapp.intefaces.TimerCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable

class WeekTimeClock(
    private val timeManager: TimeManager,
    private var model: WeekTimeModel,
    private val timerCallback: TimerCallback
): Serializable {


    private val totalWeekCycleHours = model.totalWeekCycleHours * 60 * 60 * 1000
    private var totalConsumedTimeMillis: Long =
        timeManager.convertLongToTime(model.lastSavedCycleTime)
    private var job: Job? = null

    fun startCycle() {


        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive && totalConsumedTimeMillis < totalWeekCycleHours) {
                totalConsumedTimeMillis += DateTimeFormat.shiftUpdateIntervalTime
                val remainingTimeMillis = (totalWeekCycleHours) - totalConsumedTimeMillis
                val remainingTime = timeManager.convertMillisToTime(remainingTimeMillis)
                val consumedTime = timeManager.convertMillisToTime(totalConsumedTimeMillis)

                model.lastSavedCycleTime = consumedTime
                model.remainingTime = remainingTime
                model.consumedTime = model.lastSavedCycleTime
                model.consumeTimeInMillis = totalConsumedTimeMillis
                model.remainingTimeInMillis = remainingTimeMillis
                model.progressPercentage =
                    (totalConsumedTimeMillis.toFloat() / totalWeekCycleHours.toFloat()) * 100

                withContext(Dispatchers.Main) {

                    timerCallback.onWeekCycleTick(model.copy())
                }
                delay(DateTimeFormat.shiftUpdateIntervalTime)
            }

            job = null
            withContext(Dispatchers.Main) {
                timerCallback.onWeekCycleFinish()
            }
        }
    }

    fun stopCycle() {
        job?.cancel()
    }


    fun getCycleTimer(): WeekTimeModel {
        return this.model
    }
}
