package com.core

import android.util.Log
import com.core.utils.AppLogger
import com.core.utils.PreferenceManager
import com.core.utils.fileUtils.FileUtils
import com.pt.devicemanager.App
import com.scichart.charting.visuals.SciChartSurface
import com.scichart.extensions.builders.SciChartBuilder
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject


@HiltAndroidApp
class BaseApplication : App() /*, androidx.lifecycle.DefaultLifecycleObserver */ {
    companion object {
        lateinit var instance: BaseApplication
    }

    @Inject
    lateinit var preferenceManager: PreferenceManager
    override fun onCreate() {
        super<App>.onCreate()

        instance = this
        AppLogger.initializeLogging(
            this, FileUtils.getLogsPath(), preferenceManager.isApplicationLogsEnabled()
        )
        try {
            // Set this code once in MainActivity or application startup
            SciChartSurface.setRuntimeLicenseKey("5sw+FIqAYn2IirIlQG+h2tMNl+43axoVrruXorQF0Sr/YGP7v/CRuxDgIPLog47IXHzc3E1AU8YIost4qEcvWPXMn6OuBBSACR6ZKjUGWnXxOoAXge4zXthtyRBR4+AisUs1GVDuq/FUmpyauIlv1vFQ/ytZFZv8LxAqDm9XG+98IV8JDzA2SeniFxSQ54qCRbdouDRTJwmxWAvtYq0Ei3RJobU/BpXUoicHAmYZLwK0KALHHc6YiXkluhZfOapk81UG2DArw0Rgf1xhYlg6gOwhbjZkTg4ylQ6uhtXwLe4qfnHDwPV+ws0Aid/97hfzF/JY9m654RKBdVCQTdSpJ9bw3+dhpg3gTAwzUv5/YYMvwo9+sdkQjDDq+Uw/VA/oeDHBgfiH30ORbnH7/P+60PIws7qZhmq2eNkvQzlnJrWv/0hST0hZCjhGuVZMEq71x4NVvKBsA9kyd8IU8HodLbLbfd5UvWAlavbe0xlsMCPejExJtfyKJ+g7GUQtZx5FaPULuyo0fxQGhWpRtqC/HaYPwiuNvq02+bWcDg==");
            SciChartBuilder.init(this)
        } catch (e: Exception) {
            Log.e("SciChart", "Error when setting the license", e)
        }

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