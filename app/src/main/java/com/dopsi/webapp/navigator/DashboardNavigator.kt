package com.dopsi.webapp.navigator

import com.core.data.model.MovieResponse
import com.core.interfaces.BaseNavigator
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel

interface DashboardNavigator :BaseNavigator {
     fun updateShiftTime(model:ShiftTimeModel)
     fun updateWeekTime(model:WeekTimeModel)
     fun updateDriveTime(model:DriveTimeModel)
}