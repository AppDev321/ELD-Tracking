package com.dopsi.webapp.viewmodel

import com.core.base.BaseViewModel
import com.core.interfaces.BaseNavigator
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import com.dopsi.webapp.navigator.DashboardNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


@HiltViewModel
class DashboardViewModel @Inject constructor() :
    BaseViewModel<DashboardNavigator>() {


}



