package com.dopsi.webapp.interfaces

import android.view.View


interface ItemClickListener {
    fun onItemClick(position: Int, view: View)
    fun onItemLongClick(position: Int, view: View)
}

interface CheckChangeListenerItem {
    fun onCheckChangedListener(
        view: View,
        checkedState: Boolean
    )
}


