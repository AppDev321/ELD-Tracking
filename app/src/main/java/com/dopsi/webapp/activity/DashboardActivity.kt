package com.dopsi.webapp.activity

import android.bluetooth.BluetoothDevice
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.core.utils.DialogManager
import com.dopsi.webapp.R
import com.dopsi.webapp.bussinesslogic.DriveTimeClock
import com.dopsi.webapp.bussinesslogic.ShiftTimeClock
import com.dopsi.webapp.bussinesslogic.WeekTimeClock
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.TimeManager
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import com.dopsi.webapp.databinding.ActivityDashboardBinding
import com.dopsi.webapp.intefaces.TimerCallback
import com.dopsi.webapp.viewmodel.DashboardViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DashboardActivity : BaseActivity(), NavigationView.OnNavigationItemSelectedListener,
    TimerCallback {

    companion object {
        const val SAVE_SHIFT_TIME = "shift_time"
        const val SAVE_WEEK_TIME = "week_time"
        const val SAVE_DRIVE_TIME = "drive_time"
    }

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val dashboardViewModel: DashboardViewModel by viewModels()

    private lateinit var shiftTimerUtility: ShiftTimeClock
    private lateinit var weekTimerUtility: WeekTimeClock
    private lateinit var driveTimerUtility: DriveTimeClock


    @Inject
    lateinit var timeManager: TimeManager
    override fun onInitialize(savedInstanceState: Bundle?) {
        super.onInitialize(savedInstanceState)
        setupTimeModels(savedInstanceState)
        setupTimers()
    }

    private fun setupTimeModels(savedInstanceState: Bundle?) {
        val serverTime = "21/01/2024 15:01"
        timeManager.setServerTime(serverTime)

        val defaultShiftTimeModel = ShiftTimeModel(
            totalShiftHours = 8,
            serverTimeInMillis = timeManager.getAdjustedTime(),
            shiftStartTime = "21/01/2024 08:00:00"
        )
        val savedShiftTimeModel = savedInstanceState?.getSerializable(SAVE_SHIFT_TIME) as? ShiftTimeModel

        val defaultWeekTimeModel = WeekTimeModel(
            totalWeekCycleHours = 70,
            serverTimeInMillis = timeManager.getAdjustedTime(),
            weekCycleStartTime = "19/01/2024 08:00",
            lastSavedCycleTime = "35:00"
        )
        val savedWeekTimeModel = savedInstanceState?.getSerializable(SAVE_WEEK_TIME) as? WeekTimeModel

        val defaultDriveTimeModel = DriveTimeModel(
            totalDriveHours = 14,
            serverTimeInMillis = timeManager.getAdjustedTime(),
            driveStartTime = "19/01/2024 08:00",
            lastSavedDriveTime = "01:00"
        )
        val savedDriveTimeModel = savedInstanceState?.getSerializable(SAVE_DRIVE_TIME) as? DriveTimeModel

        shiftTimerUtility = ShiftTimeClock(timeManager, savedShiftTimeModel ?: defaultShiftTimeModel, this)
        weekTimerUtility = WeekTimeClock(timeManager, savedWeekTimeModel ?: defaultWeekTimeModel, this)
        driveTimerUtility = DriveTimeClock(timeManager, savedDriveTimeModel ?: defaultDriveTimeModel, this)
    }

    private fun setupTimers() {
        shiftTimerUtility.startTimer()
        weekTimerUtility.startCycle()
        driveTimerUtility.startDriveTimer()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putSerializable(SAVE_SHIFT_TIME, shiftTimerUtility.getShiftTimer())
        outState.putSerializable(SAVE_WEEK_TIME, weekTimerUtility.getCycleTimer())
        outState.putSerializable(SAVE_DRIVE_TIME, driveTimerUtility.getDriveTimer())

        super.onSaveInstanceState(outState)
    }

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

            R.id.menu_dot_inspection -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_dot_screen)
                true
            }

            R.id.menu_account -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_account_screen)
                true
            }

            R.id.menu_vehicle -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_vehicle_screen)
                true
            }

            R.id.menu_co_driver -> {
                findNavController(R.id.nav_host_fragment).navigate(R.id.move_to_co_driver_screen)
                true
            }

            else ->
                false
        }

    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawers()
        else {
            val navHost = findNavController(R.id.nav_host_fragment)
            navHost.currentDestination?.let {
                when (it.id) {
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
            } ?: kotlin.run {
                super.onBackPressed()
            }

        }
    }

    private fun showExitConfirmationDialog() {
        dialogManager.twoButtonDialog(this,
            "Exit Confirmation",
            "Are you sure you want to exit?",
            positiveButtonText = "Yes",
            alertDialogListener = object : DialogManager.AlertDialogListener {
                override fun onPositiveButtonClicked() {
                    finish()
                    super.onPositiveButtonClicked()
                }
            }
        ).show()
    }

    private fun handleBackAndHamburgIcon() {
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

    override fun onShiftTimeTick(timeModel: ShiftTimeModel) {
      dashboardViewModel.updateShiftTimeData(timeModel)
       /* AppLogger.e(TAG, "Consumed = ${timeModel.consumedTime}")
        AppLogger.e(TAG, "remaing = ${timeModel.remainingTime}")
        AppLogger.e(
            TAG,
            "Server current Time = ${TimeManager().convertLongToCompleteTime(timeModel.serverTimeInMillis)}"
        )*/
    }

    override fun onWeekCycleTick(timeModel: WeekTimeModel) {
        dashboardViewModel.updateWeekCycleTimeData(timeModel)
    }

    override fun onDriveTimeTick(model: DriveTimeModel) {
        dashboardViewModel.updateDriveTimeData(model)
    }


}