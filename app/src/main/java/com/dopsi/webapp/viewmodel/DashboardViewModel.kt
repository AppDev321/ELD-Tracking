package com.dopsi.webapp.viewmodel

import android.os.Handler
import android.os.Looper
import android.os.ParcelUuid
import android.view.View
import com.core.base.BaseViewModel
import com.core.extensions.TAG
import com.core.interfaces.BaseNavigator
import com.core.utils.AppLogger
import com.dopsi.webapp.model.ShiftTimeModel
import com.dopsi.webapp.navigator.BluetoothNavigator
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice
import com.pt.sdk.Uart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import no.nordicsemi.android.support.v18.scanner.BluetoothLeScannerCompat
import no.nordicsemi.android.support.v18.scanner.ScanCallback
import no.nordicsemi.android.support.v18.scanner.ScanFilter
import no.nordicsemi.android.support.v18.scanner.ScanResult
import no.nordicsemi.android.support.v18.scanner.ScanSettings
import java.util.UUID
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor() :
    BaseViewModel<BaseNavigator>() {

    private val _shiftTimeFlow = MutableStateFlow<ShiftTimeModel>(ShiftTimeModel())
    val shiftTimeFlow = _shiftTimeFlow.asStateFlow()

    fun updateShiftTimeData(newValue: ShiftTimeModel) {
        _shiftTimeFlow.value = newValue
    }

}



