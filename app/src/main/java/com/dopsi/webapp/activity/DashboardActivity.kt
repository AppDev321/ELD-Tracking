package com.dopsi.webapp.activity

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.core.utils.DialogManager
import com.core.utils.navigateSafe
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
        handleBackAndHamburgIcon()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawers()
        findNavController(R.id.nav_host_fragment).popBackStack()
        return when (item.itemId) {
            R.id.menu_status -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_dashboard_screen)
                true
            }
            R.id.menu_info -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_information_screen)
                true
            }
            R.id.menu_dot_inspection ->
            {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_dot_screen)
                true
            }
            R.id.menu_account ->
            {
                findNavController(R.id.nav_host_fragment).navigate( R.id.move_to_account_screen)
                true
            }
            R.id.menu_vehicle ->
            {
                findNavController(R.id.nav_host_fragment).navigate( R.id.move_to_vehicle_screen)
                true
            }
            R.id.menu_co_driver ->
            {
                findNavController(R.id.nav_host_fragment).navigate( R.id.move_to_co_driver_screen)
                true
            }
            else ->
                false
        }

    }

    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers()
        else {
            val navHost = findNavController(R.id.nav_host_fragment)
            navHost.currentDestination?.let {
                when (it.id){
                    R.id.move_to_information_screen,
                    R.id.move_to_co_driver_screen,
                    R.id.move_to_vehicle_screen,
                    R.id.move_to_account_screen,
                    R.id.move_to_dot_screen,
                    R.id.move_to_information_screen ->
                        navHost.navigate(R.id.move_to_dashboard_screen)
                    R.id.move_to_dashboard_screen ->
                        showExitConfirmationDialog()
                    else -> super.onBackPressed()

                }
            }?:kotlin.run {
                super.onBackPressed()
            }

        }
    }

    private fun showExitConfirmationDialog() {
        dialogManager.twoButtonDialog(this,
            "Exit Confirmation",
            "Are you sure you want to exit?",
            positiveButtonText = "Yes",
            alertDialogListener = object:DialogManager.AlertDialogListener{
                override fun onPositiveButtonClicked() {
                    finish()
                    super.onPositiveButtonClicked()
                }
            }
            ).show()
    }

    fun handleBackAndHamburgIcon() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount > 0) {
                supportActionBar?.setDisplayHomeAsUpEnabled(true); // show back button
                binding.toolbar.setNavigationOnClickListener(View.OnClickListener { onBackPressed() })
            } else {
                supportActionBar?.setDisplayHomeAsUpEnabled(false);
                drawerToggle.syncState();
                binding.toolbar.setNavigationOnClickListener(View.OnClickListener {
                    drawerLayout.openDrawer(GravityCompat.START);
                })
                title = resources.getString(R.string.app_name)
            }
        }
    }
}