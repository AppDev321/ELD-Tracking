package com.dopsi.webapp

import android.app.Application
import com.dopsi.webapp.utils.AppLogger
import com.dopsi.webapp.utils.PreferenceManager
import com.dopsi.webapp.utils.fileUtils.FileUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BaseApplication: Application() {
    companion object {
        lateinit var instance: BaseApplication
    }
    @Inject
    lateinit var preferenceManager :PreferenceManager
    override fun onCreate() {
        super.onCreate()

        instance = this
        AppLogger.initializeLogging(
            this, FileUtils.getLogsPath(),
            preferenceManager.isApplicationLogsEnabled()
        )

    }

}