package com.dopsi.webapp.viewmodel

import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.view.View
import com.core.base.BaseViewModel
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.dopsi.webapp.navigator.BluetoothNavigator
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice
import com.pt.sdk.Uart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanFilter
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class BluetoothViewModel @Inject constructor() :
    BaseViewModel<BluetoothNavigator>() {

    private val _discoveredDevices = MutableSharedFlow<MutableList<ExtendedBluetoothDevice>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val discoveredDevices: MutableSharedFlow<MutableList<ExtendedBluetoothDevice>> get() = _discoveredDevices
     var scannedDevices: MutableList<ExtendedBluetoothDevice> = arrayListOf()

    private var isScanning = false

    val scanCallback = object : ScanCallback() {
        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            super.onBatchScanResults(results)
            for (result in results) {
                val deviceData = findDevice(result)?.let {
                    it.name =
                        if (result.scanRecord != null) result.scanRecord!!.deviceName else null
                    it.rssi = result.rssi
                    it!!
                } ?: kotlin.run {
                    ExtendedBluetoothDevice(result)
                }
                scannedDevices.add(deviceData)
                _discoveredDevices.tryEmit(scannedDevices.distinct().toMutableList())
            }
        }

    }

    fun startScan(mUuid: UUID) {
        if (isScanning) return

        getNavigator()?.setProgressVisibility(View.VISIBLE)

        val scanner = BluetoothLeScannerCompat.getScanner()
        val settings = ScanSettings.Builder()
            .setLegacy(false)
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .setReportDelay(3000)
            .setUseHardwareBatchingIfSupported(false)
            .build()
        val filters: MutableList<ScanFilter> = ArrayList()
        filters.add(ScanFilter.Builder().setServiceUuid(ParcelUuid(mUuid)).build())
        scanner.startScan(filters, settings, scanCallback)

        isScanning = true
        Handler(Looper.getMainLooper()).postDelayed({
            if (isScanning) {
                stopScan()
            }
            if (scannedDevices.isEmpty()) {
                getNavigator()?.setProgressVisibility(View.INVISIBLE)
                _discoveredDevices.tryEmit(arrayListOf())
            }
            isScanning = false
            AppLogger.e(TAG, "isScanning ==> ${isScanning}")
        }, 8000)

    }

    fun showBLEEnabledDialog() {
        getNavigator()?.showBluetoothEnableDialog()
    }

    fun getUUID(): UUID {
        return UUID.fromString(Uart.RX_SERVICE_UUID.toString())
    }

    private fun stopScan() {
        if (isScanning) {
            val scanner = BluetoothLeScannerCompat.getScanner()
            scanner.stopScan(scanCallback)
            isScanning = false
        }
    }

    private fun findDevice(result: ScanResult): ExtendedBluetoothDevice? {
        for (device in scannedDevices) if (device.matches(result)) return device
        return null
    }


}



