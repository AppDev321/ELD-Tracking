package com.dopsi.webapp.navigator

import com.core.data.model.MovieResponse
import com.core.interfaces.BaseNavigator

interface BluetoothNavigator :BaseNavigator {
     fun setDeviceList(list: ArrayList<MovieResponse.Results>) = Unit
     fun showBluetoothEnableDialog()
}