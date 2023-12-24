package com.dopsi.webapp.model

import android.content.Context
import com.dopsi.webapp.R
import javax.inject.Inject

class VehicleDataFactory
@Inject constructor(
    private val context: Context,
) {

    fun getVehicleData(): ArrayList<VehicleData> {
        return arrayListOf(

            VehicleData(
                "802",
                "2023 Kenworth T680",
            ),

            VehicleData(
                "805",
                "2021 Toyota Classic",
            ),


        )
    }
}

class VehicleData(
    var title: String,
    var desc: String ,

)
