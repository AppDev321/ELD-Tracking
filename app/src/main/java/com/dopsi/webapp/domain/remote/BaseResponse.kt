package com.dopsi.webapp.domain.remote

import com.dopsi.webapp.extensions.empty
import com.google.gson.annotations.SerializedName

open class BaseResponse {
    @SerializedName("status")
    val status: Boolean = false

    @SerializedName("version")
    val version: String = String.empty

    @SerializedName("message")
    var message: String = String.empty
}