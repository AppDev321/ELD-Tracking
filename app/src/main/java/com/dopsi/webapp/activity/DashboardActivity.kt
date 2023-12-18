package com.dopsi.webapp.activity

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.ActivityDashboardBinding
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    override fun onDeviceDisconnecting(device: BluetoothDevice) {
        super.onDeviceDisconnecting(device)
        AppLogger.e(TAG, "onDeviceDisconnecting")
    }

    override fun onDeviceDisconnected(device: BluetoothDevice, reason: Int) {
        super.onDeviceDisconnected(device, reason)

        AppLogger.e(TAG, "onDeviceDisconnected ==> $reason")
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.let {
            title = "Dashboard"
        }

        drawerLayout = binding.drawerLayout
        drawerToggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            binding.toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawers()
       return false
    }
}