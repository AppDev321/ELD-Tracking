package com.dopsi.webapp.model

sealed class ClickEvent {

    data class ItemClick(var pos: Int) : ClickEvent()

}