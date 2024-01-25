package com.dopsi.webapp.bussinesslogic

import com.dopsi.webapp.bussinesslogic.model.BreakTimeModel
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

class BreakTimeClock(
    private val timeManager: TimeManager,
    private var model: BreakTimeModel,
    private val timerCallback: TimerCallback
): Serializable {


    private val totalBreakMin = model.totalBreakMin * 60 * 1000
    private var totalConsumedTimeMillis: Long =  timeManager.convertLongToTime(model.consumedTime)
    private var job: Job? = null

    fun startBreakTime() {


        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive && totalConsumedTimeMillis < totalBreakMin) {
                totalConsumedTimeMillis += DateTimeFormat.shiftUpdateIntervalTime
                val remainingTimeMillis = (totalBreakMin) - totalConsumedTimeMillis
                val remainingTime = timeManager.convertMillisToTime(remainingTimeMillis)
                val consumedTime = timeManager.convertMillisToTime(totalConsumedTimeMillis)

                model.remainingTime = remainingTime
                model.consumedTime = consumedTime
                model.consumeTimeInMillis = totalConsumedTimeMillis
                model.remainingTimeInMillis = remainingTimeMillis
                model.progressPercentage =
                    (totalConsumedTimeMillis.toFloat() / totalBreakMin.toFloat()) * 100

                withContext(Dispatchers.Main) {

                    timerCallback.onBreakTimeTick(model.copy())
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


    fun getBreakTimer(): BreakTimeModel {
        return this.model
    }
}
