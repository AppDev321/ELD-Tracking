package com.dopsi.webapp.interfaces

import android.bluetooth.BluetoothDevice
import no.nordicsemi.android.ble.annotation.DisconnectionReason

interface ServiceInterface {
    fun onDeviceConnecting(device: BluetoothDevice) = Unit
    fun onDeviceConnected(device: BluetoothDevice) = Unit
    fun onDeviceFailedToConnect(
        device: BluetoothDevice,
        @DisconnectionReason reason: Int
    ) = Unit

    fun onDeviceReady(device: BluetoothDevice) = Unit

    fun onDeviceDisconnecting(device: BluetoothDevice) = Unit
    fun onDeviceDisconnected(
        device: BluetoothDevice,
        @DisconnectionReason reason: Int
    ) = Unit
}