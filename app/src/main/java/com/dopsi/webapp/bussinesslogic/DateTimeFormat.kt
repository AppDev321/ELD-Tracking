package com.dopsi.webapp.bussinesslogic

import java.text.SimpleDateFormat
import java.util.Locale

object DateTimeFormat {
    const val completeDateRequired = "dd/MM/yyyy HH:mm"
    const val dateRequired = "dd/MM/yyyy"
    const val timeRequired = "HH:mm"

    val timeRequiredFormat = SimpleDateFormat(timeRequired, Locale.getDefault())
    val dateRequiredFormat = SimpleDateFormat(dateRequired, Locale.getDefault())

    val completeDateFormat = SimpleDateFormat(completeDateRequired, Locale.getDefault())


    const val shiftUpdateIntervalTime :Long =  60*1000
}