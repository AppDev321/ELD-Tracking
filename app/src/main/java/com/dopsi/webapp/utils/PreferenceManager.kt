package com.dopsi.webapp.utils

import android.content.Context
import com.dopsi.webapp.BuildConfig


class PreferenceManager constructor(val context: Context) {

    private val sharedPreferences by lazy {
        val preferences =
            context.getSharedPreferences(APP_PREFERENCES_FILE, Context.MODE_PRIVATE)
        preferences
    }

    // region COMPANION OBJECT
    companion object {
        private const val APP_PREFERENCES_FILE = "app_pref_file"
        private const val SETTINGS_APPLICATION_LOGGER = "app_log_status"

    }
    // endregion

    fun setApplicationLogsEnable(enable: Boolean) {
        setBooleanPolicy(SETTINGS_APPLICATION_LOGGER, enable)
    }

    fun isApplicationLogsEnabled() = (getBooleanPolicyWithDefaultTrue(SETTINGS_APPLICATION_LOGGER)
            && BuildConfig.ENABLE_APPLICATION_LOGGER)


    private fun setBooleanPolicy(key: String, value: Boolean) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    private fun getBooleanPolicyWithDefaultTrue(key: String): Boolean {
        return sharedPreferences.getBoolean(key, true)
    }

    private fun getBooleanPolicy(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

}