package com.dopsi.webapp.bussinesslogic.model

import android.os.Handler
import android.os.Looper
import com.dopsi.webapp.bussinesslogic.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class TimeManager @Inject constructor() {
    private var serverTimeOffset: Long = 0
    private val dateFormat = DateTimeFormat.completeDateFormat
    private val handler = Handler(Looper.getMainLooper())
    private val updateInterval = 60 * 1000L // Update every minute

    init {
     //  startPeriodicUpdate()
    }

    private fun startPeriodicUpdate() {
        handler.postDelayed(object : Runnable {
            override fun run() {
                updateServerTimeOffset()
                handler.postDelayed(this, updateInterval)
            }
        }, updateInterval)
    }
    fun setServerTime(timeString: String) {
        val sdf = SimpleDateFormat(DateTimeFormat.completeDateRequired)
        //sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(timeString)
        setServerTime(date?.time ?: 0)
    }

    private fun setServerTime(serverTime: Long) {
        val deviceTime = System.currentTimeMillis()
        serverTimeOffset = serverTime - deviceTime
    }

    fun getAdjustedTime(): Long {
        return System.currentTimeMillis() + serverTimeOffset
    }


    fun convertLongToDate(time: Long): String {
        val date = Date(time)
        return DateTimeFormat.dateRequiredFormat.format(date)
    }

    fun convertLongToCompleteTime(time: Long): String {
        val date = Date(time)
        return dateFormat.format(date)
    }

    fun updateServerTimeOffset() {
        val deviceTime = System.currentTimeMillis()
        serverTimeOffset += (deviceTime - (getAdjustedTime() - serverTimeOffset))
    }



     fun convertMillisToTime(millis: Long): String {
        val hours = TimeUnit.MILLISECONDS.toHours(millis)
        val minutes = (TimeUnit.MILLISECONDS.toMinutes(millis) % 60).toInt()
        return String.format("%02d:%02d", hours, minutes)
    }

    fun convertLongToTime(timeString: String): Long {
        val components = timeString.split(":")
        val hours = components[0].toLong()
        val minutes = components[1].toLong()
        return hours * 60 * 60 * 1000 + minutes * 60 * 1000
    }
}
