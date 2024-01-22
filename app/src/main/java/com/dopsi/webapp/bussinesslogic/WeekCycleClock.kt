package com.dopsi.webapp.bussinesslogic

import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class TimeCycle(
    private val model: WeekTimeModel,
    private val timerCallback: TimerCallback
) {

    interface TimerCallback {
        fun onCycleTick(totalConsumedTime: String)
        fun onCycleFinish()
    }
    private val dateFormat =DateTimeFormat.completeDateFormat
    private var totalConsumedTimeMillis: Long = dateFormat.parse(model.lastSavedCycleTime).time
    private var job: Job? = null

    fun startCycle() {
        job = CoroutineScope(Dispatchers.Main).launch {
            while (isActive && totalConsumedTimeMillis < model.totalWeekCycleHours * 60 * 60 * 1000) {
                totalConsumedTimeMillis += 1000 // Increment by one second
                val totalConsumedTime = convertMillisToTime(totalConsumedTimeMillis)
                withContext(Dispatchers.Main) {
                    timerCallback.onCycleTick(totalConsumedTime)
                }
                delay(1000) // Update every second
            }

            job = null
            withContext(Dispatchers.Main) {
                timerCallback.onCycleFinish()
            }
        }
    }

    fun stopCycle() {
        job?.cancel()
    }

    private fun convertMillisToTime(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
        return String.format("%02d:%02d:%02d", hours, minutes, seconds)
    }
}
