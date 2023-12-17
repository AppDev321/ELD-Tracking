package com.dopsi.webapp.activity

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.core.extensions.TAG
import com.core.extensions.showErrorToast
import com.core.utils.AppLogger
import com.core.utils.PermissionUtilsNew
import com.core.utils.Utils
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.databinding.ActivityMainBinding
import com.dopsi.webapp.navigator.BluetoothNavigator
import com.dopsi.webapp.viewmodel.BluetoothViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pt.devicemanager.BleProfileService
import com.pt.devicemanager.BleProfileServiceReadyActivity
import com.pt.devicemanager.TrackerService
import com.pt.devicemanager.TrackerService.TrackerBinder
import com.pt.sdk.Uart
import dagger.hilt.android.AndroidEntryPoint
import java.util.UUID

@AndroidEntryPoint
class MainActivity : BleProfileServiceReadyActivity<TrackerService.TrackerBinder>(),BluetoothNavigator {
    private val mainActivityViewModel: MovieViewModel by viewModels()
    private val bluetoothViewModel: BluetoothViewModel by viewModels()


    lateinit var mTrackerBinder: TrackerBinder
    private lateinit var binding: ActivityMainBinding
    override fun onCreateView(savedInstanceState: Bundle?) {
        bluetoothViewModel.setNavigator(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarOverViewScreen.toolbar)
        binding.toolbarOverViewScreen.let {
            it.toolbarTitle.text = "Connect ELD"
            it.toolbar.setNavigationOnClickListener{
                onBackPressed()
            }
        }
      //  onConnectClicked(view)

        checkBluetoothDevicePermission()
    }


    override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {
        AppLogger.e(TAG, "Device Failed  ....")
    }

    override fun onServiceUnbound() {
    }

    override fun getServiceClass(): Class<out BleProfileService> {
        return TrackerService::class.java
    }

    override fun getConnectionToggleResourceId(): Int {
        return 0;
    }

    override fun setDefaultUI() {
    }

    override fun getFilterUUID(): UUID {
        return bluetoothViewModel.getUUID()
    }

    override fun onServiceBound(binder: TrackerService.TrackerBinder) {
        mTrackerBinder = binder
        val broadcast = Intent("SVC-BOUND-REFRESH")
        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcast)
        AppLogger.i(TAG, "A:Tracker service bounded.")
        AppLogger.i(TAG, "Status Track = ${mTrackerBinder.tracker.isUpdating}")
    }

    override fun onDeviceConnecting(device: BluetoothDevice) {
        super.onDeviceConnecting(device)
        AppLogger.i(TAG, "A: Tracker connecting  ....")
    }

    override fun onDeviceDisconnected(device: UsbDevice?) {
        super.onDeviceDisconnected(device)
        AppLogger.i(TAG, "A: Tracker Disconnecting  ....")
    }

    override fun onLinkLossOccurred(device: BluetoothDevice?) {
        super.onLinkLossOccurred(device)
        AppLogger.i(TAG, "A: Tracker lost link ...");
    }

    override fun onDeviceConnected(device: BluetoothDevice) {
        super.onDeviceConnected(device)
        AppLogger.i(TAG, "A: Tracker Connected")
    }

    private fun checkBluetoothDevicePermission()
    {
        if(PermissionUtilsNew.hasBluetoothPermission().not())
        {
            Dexter.withContext(this@MainActivity)
                .withPermissions(PermissionUtilsNew.getBluetoothPermissionList())
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            AppLogger.e(TAG,"Permissions accepted: $multiplePermissionsReport")
                            checkBluetoothState()
                        } else if (multiplePermissionsReport.isAnyPermissionPermanentlyDenied) {
                            PermissionUtilsNew.showPermissionSettingsDialog(this@MainActivity)
                        }
                    }
                    override fun onPermissionRationaleShouldBeShown(
                        list: List<PermissionRequest>,
                        permissionToken: PermissionToken,
                    ) {
                        permissionToken.continuePermissionRequest()
                    }
                }).check()
        }else{
            AppLogger.e(TAG,"Permissions Already accept")
            checkBluetoothState()
        }
    }

    fun checkBluetoothState()
    {
        if (Utils.isBluetoothSupported(this).not()) {
            showToast("This Device doest not support Bluetooth")
            finish()
        } else {
            if (Utils.isBluetoothEnabled(this).not()) {
                showBLEDialog()
            }
        }
    }

    override fun showBluetoothEnableDialog() {
        showBLEDialog()
    }


}