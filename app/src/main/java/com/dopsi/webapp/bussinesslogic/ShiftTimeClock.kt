package com.dopsi.webapp.bussinesslogic


import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.TimeManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.Serializable
import java.util.Locale



class ShiftTimeClock(
    private var timeManager: TimeManager,
    private var shiftTimeModel: ShiftTimeModel,
    private val timerCallback: TimerCallback
):Serializable {

    interface TimerCallback {
        fun onTick(timeModel: ShiftTimeModel)
        fun onFinish()
    }
    private val totalShiftHours: Int = shiftTimeModel.totalShiftHours
    private var serverCurrentTime: Long = shiftTimeModel.serverTimeInMillis
    private val shiftStartTime: String = shiftTimeModel.shiftStartTime
    private val dateFormat = DateTimeFormat.completeDateFormat
    private lateinit var job: Job
    private var consumedShiftTimeMillis: Long = 0

    fun startTimer() {
        job = CoroutineScope(Dispatchers.Main).launch {
           // val serverTimeDate = dateFormat.parse(serverCurrentTime)
            val shiftStartTimeDate = dateFormat.parse(shiftStartTime)

            val shiftStartTimeMillis = shiftStartTimeDate?.time ?: 0
            val shiftDurationMillis = totalShiftHours * 60 * 60 * 1000.toLong()
            var remainingTimeMillis =
                shiftStartTimeMillis + shiftDurationMillis - serverCurrentTime

            while (isActive && remainingTimeMillis > 0) {
                consumedShiftTimeMillis = shiftDurationMillis - remainingTimeMillis

                val remainingMinutes = remainingTimeMillis / (60 * 1000)
                val remainingHours = remainingMinutes / 60
                val remainingMinutesDisplay = remainingMinutes % 60

                val consumedHours = consumedShiftTimeMillis / 3600000
                val consumedMinutes = (consumedShiftTimeMillis % 3600000) / 60000

                val remainingTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    remainingHours,
                    remainingMinutesDisplay
                )

                val consumedTime = String.format(
                    Locale.getDefault(),
                    "%02d:%02d",
                    consumedHours,
                    consumedMinutes
                )

                withContext(Dispatchers.Main) {

                    val timeModel = ShiftTimeModel(
                        shiftDurationMillis,
                        consumedShiftTimeMillis,
                        remainingTimeMillis,
                        serverCurrentTime,
                        shiftStartTimeMillis,
                        consumedTime,
                        remainingTime,
                        "servertime",
                        shiftStartTime,
                        progressPercentage = consumedShiftTimeMillis.toFloat() /shiftDurationMillis.toFloat() * 100
                    )
                    timeModel.serverTimeInMillis = timeManager.getAdjustedTime()
                    saveShiftTimer(timeModel)
                    timerCallback.onTick(timeModel)
                }

                delay(DateTimeFormat.shiftUpdateIntervalTime) // Update every minute
               // delay( 1000) // Update every seconds
                remainingTimeMillis -= DateTimeFormat.shiftUpdateIntervalTime
            }

            withContext(Dispatchers.Main) {
                timerCallback.onFinish()
            }
        }
    }

    fun stopTimer() {
        job.cancel()
    }

    private fun saveShiftTimer(timeModel : ShiftTimeModel)
    {
        this.shiftTimeModel = timeModel
    }

    fun getShiftTimer() : ShiftTimeModel
    {
       return this.shiftTimeModel
    }
}
