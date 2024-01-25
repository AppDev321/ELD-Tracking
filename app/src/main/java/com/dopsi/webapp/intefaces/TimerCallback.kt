package com.dopsi.webapp.intefaces

import com.dopsi.webapp.bussinesslogic.model.BreakTimeModel
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel

interface TimerCallback {
    fun onDriveTimeTick(model: DriveTimeModel) {}
    fun onShiftTimeTick(timeModel: ShiftTimeModel){}
    fun onWeekCycleTick(model: WeekTimeModel){}
    fun onWeekCycleFinish(){}
    fun onDriveTimeFinish() {}
    fun onShiftTimeFinish(){}



    //For Break Time

    fun onBreakTimeReached() {}
    fun isDriverStartedBreak() {}
    fun onBreakTimeTick(model: BreakTimeModel) {}
    fun onBreakFinish(model: DriveTimeModel) {}
}