package com.dopsi.webapp.model

import android.content.Context
import com.dopsi.webapp.R
import javax.inject.Inject

class AccountDataFactory
@Inject constructor(
    private val context: Context,
) {

    enum class ITEM_TYPE {
        TEXT,
        DROPDOWN,
    }

    fun getAccountData(): ArrayList<AccountData> {
        return arrayListOf(

            AccountData(
                context.getString(R.string.txt_email),
                "test@gmail.com",
                ITEM_TYPE.TEXT
            ),


            AccountData(
                context.getString(R.string.txt_name),
                "Name Test",
                ITEM_TYPE.TEXT
            ),
            AccountData(
                context.getString(R.string.txt_phone),
                "(920) 123-4567",
                ITEM_TYPE.TEXT
            ),
            AccountData(
                context.getString(R.string.txt_license),
                "WI, H123456799875",
                ITEM_TYPE.TEXT
            ),

            AccountData(
                context.getString(R.string.txt_carrier),
                "WEHAULLOADS LLC",
                ITEM_TYPE.TEXT
            ),

            AccountData(
                context.getString(R.string.txt_main_office),
                "3006 W Lund Dr. Franksville WI 53126",
                ITEM_TYPE.TEXT
            ),

            AccountData(
                context.getString(R.string.txt_home_terminal),
                "3006 W Lund Dr. Franksville WI 53126",
                ITEM_TYPE.TEXT
            ),

            AccountData(
                context.getString(R.string.txt_time_zone),
                "CT",
                ITEM_TYPE.TEXT
            ),

            AccountData(
                context.getString(R.string.txt_lang),
                "English",
                ITEM_TYPE.DROPDOWN
            ),

            AccountData(
                context.getString(R.string.txt_odometer),
                "MI",
                ITEM_TYPE.DROPDOWN
            ),


        )
    }
}

class AccountData(
    var title: String,
    var desc: String ,
    var viewType: AccountDataFactory.ITEM_TYPE
)
