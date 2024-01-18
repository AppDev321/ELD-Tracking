package com.dopsi.webapp.model

import android.os.Handler
import android.os.Looper
import com.dopsi.webapp.bussinesslogic.DateTimeFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone
import javax.inject.Inject

class TimeManager @Inject constructor() {
    private var serverTimeOffset: Long = 0
    private val dateFormat = DateTimeFormat.shiftTimeFormat
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
        val sdf = SimpleDateFormat("HH:mm")
        //sdf.timeZone = TimeZone.getTimeZone("UTC")
        val date = sdf.parse(timeString)
        setServerTime(date?.time ?: 0)
    }

    fun setServerTime(serverTime: Long) {
        val deviceTime = System.currentTimeMillis()
        serverTimeOffset = serverTime - deviceTime
    }

    fun getAdjustedTime(): Long {
       // return  serverTimeOffset
        return System.currentTimeMillis() + serverTimeOffset
    }

    fun convertLongToTime(time: Long): String {
        val date = Date(time)
        return dateFormat.format(date)
    }

    fun updateServerTimeOffset() {
        val deviceTime = System.currentTimeMillis()
        serverTimeOffset += (deviceTime - (getAdjustedTime() - serverTimeOffset))
    }
}
