package com.dopsi.webapp.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.core.base.BaseActivity
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.ActivitySplashBinding
import com.pt.devicemanager.TrackerManagerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    private val activityScope = CoroutineScope(Dispatchers.Main)
    override fun initUserInterface() {
        activityScope.launch(Dispatchers.Default) {
            delay(2000)
            withContext(Dispatchers.Main)
            {
                val intent = Intent(this@SplashActivity, DashboardActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    override fun onPause() {
        activityScope.cancel()
        super.onPause()
    }

}