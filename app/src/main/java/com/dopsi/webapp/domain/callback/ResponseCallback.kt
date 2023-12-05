package com.dopsi.webapp.domain.callback

import com.dopsi.webapp.domain.remote.BaseError

interface OptimizedResponseCallBack<T>{
    fun onApiSuccess(response: T)
    fun onApiError(error: BaseError)
}