package com.dopsi.webapp.interfaces

interface NetworkInterface {
    fun onNetworkConnected() =Unit
    fun onNetworkDisconnected() =Unit
}