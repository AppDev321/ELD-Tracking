package com.dopsi.webapp.viewmodel

import com.core.base.BaseViewModel
import com.core.interfaces.BaseNavigator
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor() :
    BaseViewModel<BaseNavigator>() {

    private val _weekCycleTimeFlow = MutableStateFlow<WeekTimeModel>(WeekTimeModel())
    val weekCycleTimeFlow = _weekCycleTimeFlow.asStateFlow()


    private val _shiftTimeFlow = MutableStateFlow<ShiftTimeModel>(ShiftTimeModel())
    val shiftTimeFlow = _shiftTimeFlow.asStateFlow()


    private val _driveTimeFlow = MutableStateFlow<DriveTimeModel>(DriveTimeModel())
    val driveTimeFlow = _driveTimeFlow.asStateFlow()


    fun updateShiftTimeData(newValue: ShiftTimeModel) {
        _shiftTimeFlow.value = newValue
    }

    fun updateWeekCycleTimeData(newValue: WeekTimeModel) {
        _weekCycleTimeFlow.value = newValue
    }
    fun updateDriveTimeData(newValue: DriveTimeModel) {
        _driveTimeFlow.value = newValue
    }
}



