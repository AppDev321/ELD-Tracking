package com.dopsi.webapp.utils.chartUtil

import java.text.SimpleDateFormat
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale
import java.util.TimeZone


class StepLineFormatDate(paramDate: Date, paramTimeZone: TimeZone) : Date(paramDate.time) {
    private val carrierTimeZone: TimeZone
    private val dateFormat: SimpleDateFormat = SimpleDateFormat("h", Locale.US)

    init {
        carrierTimeZone = paramTimeZone
        dateFormat.timeZone = paramTimeZone
    }

    val isMid: Boolean
        get() {
            val bool: Boolean
            val gregorianCalendar = GregorianCalendar(carrierTimeZone)
            gregorianCalendar.timeInMillis = time
            val i: Int = gregorianCalendar.get(11)
            val j: Int = gregorianCalendar.get(12)
            val k: Int = gregorianCalendar.get(13)
            bool = i == 0 && j == 0 && k == 0
            return bool
        }
    val isNoon: Boolean
        get() {
            val bool: Boolean
            val gregorianCalendar = GregorianCalendar(carrierTimeZone)
            gregorianCalendar.timeInMillis = time
            val i: Int = gregorianCalendar.get(11)
            val j: Int = gregorianCalendar.get(12)
            val k: Int = gregorianCalendar.get(13)
            bool = i == 12 && j == 0 && k == 0
            return bool
        }

    fun toCarrierString(): String {
        val l: Long = time
        val gregorianCalendar = GregorianCalendar(carrierTimeZone)
        gregorianCalendar.timeInMillis = l
        gregorianCalendar.set(13, 0)
        gregorianCalendar.set(14, 0)
        return dateFormat.format(gregorianCalendar.time)
    }
}

