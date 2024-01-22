package com.dopsi.webapp.bussinesslogic

import com.dopsi.webapp.bussinesslogic.model.TimeManager
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale


class WeekCycleTimeClock(
    private var timeManager: TimeManager,
    private var weekTimeModel: WeekTimeModel,
    private val timerCallback: TimerCallback
) : java.io.Serializable {

    interface TimerCallback {
        fun onWeekCycleTick(timeModel: WeekTimeModel)
        fun onWeekCycleFinish()
    }

    private val totalWeekCycle: Int = weekTimeModel.totalWeekCycleHours
    private var serverCurrentTime: Long = weekTimeModel.serverTimeInMillis
    private val weekCycleStartTime: String = weekTimeModel.weekCycleStartTime
    private val lastDayShiftStartTime: String = weekTimeModel.lastDayShiftStartTime
    private val lastDayShiftCompletedTime: String = weekTimeModel.lastDayShiftCompletedTime
    private val currentDayShiftStartTime: String = weekTimeModel.currentDayShiftStartTime
    private val dateFormat =DateTimeFormat.completeDateFormat
    private val currentDayShiftCompletedTime = ""
    private lateinit var job: Job


    fun startTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
            val cycleStartTimeDate = dateFormat.parse(weekCycleStartTime)
            val cycleStartTimeMillis = cycleStartTimeDate?.time ?: 0
            val cycleDuration = totalWeekCycle * 60 * 60 * 1000.toLong()

            while (isActive) {
                val currentTimeMillis = timeManager.getAdjustedTime()

                // Calculate consumedShiftTimeMillis for the current day
                val consumedShiftTimeMillis = calculateConsumedShiftTimeMillis(currentTimeMillis, cycleStartTimeMillis, cycleDuration)

                // Calculate consumedShiftTimeMillis for the last day
                val lastDayStartTimeMillis = if (lastDayShiftStartTime.isNotEmpty())
                    dateFormat.parse(lastDayShiftStartTime)?.time ?: 0
                else
                    0

                val lastDayCompletedTimeMillis = if (lastDayShiftCompletedTime.isNotEmpty())
                    dateFormat.parse(lastDayShiftCompletedTime)?.time ?: 0
                else
                    0

                val lastDayShiftStartTimeMillis = lastDayStartTimeMillis.coerceAtLeast(cycleStartTimeMillis)
                val lastDayShiftConsumedTimeMillis =
                    calculateConsumedShiftTimeMillis(lastDayCompletedTimeMillis, lastDayShiftStartTimeMillis, cycleDuration)

                // Calculate total consumedShiftTimeMillis
                val totalConsumedShiftTimeMillis = lastDayShiftConsumedTimeMillis + consumedShiftTimeMillis

                // Calculate remainingTimeMillis
                val remainingTimeMillis = cycleDuration - totalConsumedShiftTimeMillis

                // Ensure remainingTimeMillis is within the bounds of the cycleDuration
                val remainingTimeMillisBounded = remainingTimeMillis.coerceIn(0, cycleDuration)

                // Calculate remainingMinutes and remainingHours
                val remainingMinutes = remainingTimeMillisBounded / (60 * 1000)
                val remainingHours = remainingMinutes / 60
                val remainingMinutesDisplay = remainingMinutes % 60

                // Calculate consumedMinutes and consumedHours for the current day
                val consumedMinutes = totalConsumedShiftTimeMillis / (60 * 1000)
                val consumedHours = consumedMinutes / 60
                val consumedMinutesDisplay = consumedMinutes % 60

                val consumedTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    consumedHours,
                    consumedMinutesDisplay
                )

                val remainingTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    remainingHours,
                    remainingMinutesDisplay
                )

                withContext(Dispatchers.Main) {
                    val timeModel = WeekTimeModel(
                        cycleDuration,
                        totalConsumedShiftTimeMillis,
                        remainingTimeMillis,
                        currentTimeMillis,
                        cycleStartTimeMillis,
                        consumedTime,
                        remainingTime,
                        timeManager.convertLongToTime(currentTimeMillis),
                        weekCycleStartTime,
                        lastDayShiftStartTime,
                        lastDayShiftCompletedTime,
                        currentDayShiftStartTime,
                        currentDayShiftCompletedTime,
                        (totalConsumedShiftTimeMillis.toFloat() / cycleDuration.toFloat() * 100),
                        totalWeekCycle,
                        weekTimeModel.lastShiftStartTimeInMillis,
                        weekTimeModel.lastShiftCompletedTimeInMillis
                    )
                    timerCallback.onWeekCycleTick(timeModel)
                }

                delay(1000) // Update every second
            }

            withContext(Dispatchers.Main) {
                timerCallback.onWeekCycleFinish()
            }
        }
    }

    private fun calculateConsumedShiftTimeMillis(currentTimeMillis: Long, shiftStartTimeMillis: Long, cycleDuration: Long): Long {
        val dayStartTimeMillis = shiftStartTimeMillis + ((currentTimeMillis - shiftStartTimeMillis) / (24 * 60 * 60 * 1000)) * (24 * 60 * 60 * 1000)
        val consumedShiftTimeMillis = currentTimeMillis - dayStartTimeMillis

        // Ensure consumedShiftTimeMillis is within the bounds of the cycleDuration
        return consumedShiftTimeMillis.coerceIn(0, cycleDuration)
    }


    fun stopTimer() {
        job.cancel()
    }

    private fun saveWeekTimer(timeModel: WeekTimeModel) {
        this.weekTimeModel = timeModel
    }

    fun getWeekTimer(): WeekTimeModel {
        return this.weekTimeModel
    }
}
