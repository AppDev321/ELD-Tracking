package com.dopsi.webapp.intefaces

import com.dopsi.webapp.model.DriverStatus

data class MessageEvent(val message: String)
class DriverStatusEvent(val status: DriverStatus)

