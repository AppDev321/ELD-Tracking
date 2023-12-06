package com.core

import android.app.Application
import com.core.utils.AppLogger
import com.core.utils.PreferenceManager
import com.core.utils.fileUtils.FileUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BaseApplication: Application() {
    companion object {
        lateinit var instance: BaseApplication
    }
    @Inject
    lateinit var preferenceManager : PreferenceManager
    override fun onCreate() {
        super.onCreate()

        instance = this
        AppLogger.initializeLogging(
            this, FileUtils.getLogsPath(),
            preferenceManager.isApplicationLogsEnabled()
        )

    }

}