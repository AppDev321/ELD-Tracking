package com.dopsi.webapp.activity

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.os.Bundle
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.core.utils.DialogManager
import com.dopsi.webapp.interfaces.ServiceInterface
import com.pt.devicemanager.BleProfileService
import com.pt.devicemanager.BleProfileServiceReadyActivity
import com.pt.devicemanager.TrackerService
import com.pt.sdk.Uart
import java.util.UUID
import javax.inject.Inject

abstract class BaseActivity : BleProfileServiceReadyActivity<TrackerService.TrackerBinder>(),
    ServiceInterface {

    lateinit var mTrackerBinder: TrackerService.TrackerBinder

    @Inject
    lateinit var dialogManager: DialogManager
    override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {
        AppLogger.e(TAG, "Device Failed  ....")

        super.onDeviceFailedToConnect(device, reason)
    }

    override fun getFilterUUID(): UUID {
        return UUID.fromString(Uart.RX_SERVICE_UUID.toString())
    }

    override fun onServiceUnbound() {
    }

    override fun onDeviceDisconnecting(device: BluetoothDevice) {

    }

    override fun onDeviceReady(device: BluetoothDevice) {

    }

    override fun getServiceClass(): Class<out BleProfileService> {
        return TrackerService::class.java
    }

    override fun getConnectionToggleResourceId(): Int {
        return 0
    }

    override fun setDefaultUI() {
    }


    override fun onServiceBound(binder: TrackerService.TrackerBinder) {
        mTrackerBinder = binder
        val broadcast = Intent("SVC-BOUND-REFRESH")
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast)
        AppLogger.i(TAG, "A:Tracker service bounded.")
        AppLogger.i(TAG, "Status Track = ${mTrackerBinder.tracker.isUpdating}")
    }

    override fun onDeviceConnecting(device: BluetoothDevice) {
        AppLogger.i(TAG, "A: Tracker connecting  ....")
        super<ServiceInterface>.onDeviceConnecting(device)

    }

    override fun onDeviceDisconnected(device: BluetoothDevice, reason: Int) {
        super<ServiceInterface>.onDeviceDisconnected(device, reason)
    }

    override fun onDeviceDisconnected(device: UsbDevice?) {
        AppLogger.i(TAG, "A: Tracker Disconnecting  ....")
    }

    override fun onLinkLossOccurred(device: BluetoothDevice?) {
        super.onLinkLossOccurred(device)
        AppLogger.i(TAG, "A: Tracker lost link ...")
    }

    override fun onDeviceConnected(device: BluetoothDevice) {
        super<ServiceInterface>.onDeviceConnected(device)
        AppLogger.i(TAG, "A: Tracker Connected")
    }


}