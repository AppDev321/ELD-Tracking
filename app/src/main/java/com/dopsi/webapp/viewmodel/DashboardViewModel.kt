package com.dopsi.webapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.core.base.BaseViewModel
import com.core.interfaces.BaseNavigator
import com.dopsi.webapp.bussinesslogic.model.BreakTimeModel
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
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

    private val _breakTimeFlow = MutableStateFlow<BreakTimeModel>(BreakTimeModel())
    val breakTimeFlow = _breakTimeFlow.asStateFlow()



    fun updateShiftTimeData(newValue: ShiftTimeModel) {
        _shiftTimeFlow.value = newValue
    }

    fun updateWeekCycleTimeData(newValue: WeekTimeModel) {
        _weekCycleTimeFlow.value = newValue
    }
    fun updateDriveTimeData(newValue: DriveTimeModel) {
        _driveTimeFlow.value = newValue
    }

    fun updateBreakTimeData(newValue: BreakTimeModel) {
        _breakTimeFlow.value = newValue
    }

}



