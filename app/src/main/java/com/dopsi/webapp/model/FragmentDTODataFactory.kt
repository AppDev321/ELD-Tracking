package com.dopsi.webapp.model

import android.content.Context
import com.dopsi.webapp.R
import javax.inject.Inject

class FragmentDTODataFactory
@Inject constructor(
    private val context: Context,
) {

    enum class ITEM_TYPE {
        INSPECTION,
        LOGS,
        EMAIL,
        INFO
    }

    fun getDOTItemList(): ArrayList<DOTData> {
        return arrayListOf(

            DOTData(
                context.getString(R.string.inspect_log_text),
                context.getString(R.string.inspect_log_desc_text),
                "Start Inspection",
                ITEM_TYPE.INSPECTION
            ),
            DOTData(
                context.getString(R.string.send_log_text),
                context.getString(R.string.send_log_desc_text),
                "Send Logs",
                ITEM_TYPE.LOGS
            ),

            DOTData(
                context.getString(R.string.email_log_text),
                context.getString(R.string.email_log_desc_text),
                "Email Logs",
                ITEM_TYPE.EMAIL
            )
            ,

            DOTData(
                context.getString(R.string.packet_info_title_text),
                button="Information Packet",
                viewType= ITEM_TYPE.INFO,
            )
        )
    }
}

class DOTData(
    var title: String,
    var desc: String?=null ,
    var button: String,
    var viewType: FragmentDTODataFactory.ITEM_TYPE
)
