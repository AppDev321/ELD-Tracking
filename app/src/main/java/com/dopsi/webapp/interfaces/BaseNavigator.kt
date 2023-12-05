package com.dopsi.webapp.interfaces

import com.dopsi.webapp.extensions.empty

interface BaseNavigator {
    fun prepareAlert(title: Int, messageResourceId: Int = 0, message: String = String.empty) = Unit
    fun setProgressVisibility(visibility: Int) = Unit
}