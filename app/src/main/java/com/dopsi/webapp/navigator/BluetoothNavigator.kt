package com.dopsi.webapp.navigator

import android.bluetooth.BluetoothDevice
import com.core.data.model.MovieResponse
import com.core.interfaces.BaseNavigator

interface BluetoothNavigator :BaseNavigator {
     fun setDeviceList(list: ArrayList<MovieResponse.Results>) = Unit
     fun showBluetoothEnableDialog() = Unit
     fun onDeviceSelected(device: BluetoothDevice, name: String) = Unit
}