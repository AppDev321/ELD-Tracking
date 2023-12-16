package com.core

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.startup.AppInitializer
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.core.extensions.TAG
import com.core.service.BackgroundService
import com.core.service.ConnectionWorker
import com.core.service.FirebaseStartup
import com.core.utils.AppLogger
import com.core.utils.PreferenceManager
import com.core.utils.fileUtils.FileUtils
import com.pt.devicemanager.App
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltAndroidApp
class BaseApplication: App() /*, androidx.lifecycle.DefaultLifecycleObserver */{
    companion object {
        lateinit var instance: BaseApplication
    }
    @Inject
    lateinit var preferenceManager : PreferenceManager
    override fun onCreate() {
        super<App>.onCreate()

        instance = this
        AppLogger.initializeLogging(
            this, FileUtils.getLogsPath(),
            preferenceManager.isApplicationLogsEnabled()
        )

      /*  ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        CoroutineScope(Dispatchers.IO).launch {
            AppInitializer.getInstance(this@BaseApplication).apply {
                initializeComponent(FirebaseStartup::class.java)
            }
        }
       startService()*/


    }

/*
    private fun startService() {
        val intent = Intent(this, BackgroundService::class.java)
        try {
            startService(intent)
        } catch (e: IllegalStateException) {
            Log.e(
                javaClass.simpleName,
                "unable to start service from " + javaClass.simpleName
            )
        }
    }

    fun enqueueOnetimeConnectionWorker() {
        val workManager = WorkManager.getInstance(this)
        workManager.pruneWork()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).build()
        val connectionWorker =
            OneTimeWorkRequest.Builder(ConnectionWorker::class.java).addTag(ConnectionWorker.TAG)
                .setConstraints(constraints).build()
        AppLogger.e(TAG, "enqueue OneTime ConnectionWorker " + connectionWorker.id)
        workManager.enqueueUniqueWork(
            ConnectionWorker.NAME_ONETIME, ExistingWorkPolicy.REPLACE, connectionWorker
        )
    }

    override fun onStop(owner: LifecycleOwner) {
        super.onStop(owner)
        startService()
        AppLogger.e(TAG, "************* backgrounded")
    }*/

}