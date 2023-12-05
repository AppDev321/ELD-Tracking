package com.dopsi.webapp.domain.usecase

interface UseCaseListener {
    fun onPreExecute()
    fun onPostExecute()
}