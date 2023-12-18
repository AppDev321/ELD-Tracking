package com.dopsi.webapp.activity

import android.bluetooth.BluetoothDevice
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.core.utils.PermissionUtilsNew
import com.core.utils.Utils
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.ActivityMainBinding
import com.dopsi.webapp.intefaces.MessageEvent
import com.dopsi.webapp.navigator.BluetoothNavigator
import com.dopsi.webapp.viewmodel.BluetoothViewModel
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.pt.devicemanager.BleProfileService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.UUID
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : BaseActivity(),
    BluetoothNavigator {
    private val mainActivityViewModel: MovieViewModel by viewModels()
    private val bluetoothViewModel: BluetoothViewModel by viewModels()


    private lateinit var binding: ActivityMainBinding
    override fun getFilterUUID(): UUID {
        return bluetoothViewModel.getUUID()
    }

    override fun onDeviceFailedToConnect(device: BluetoothDevice, reason: Int) {
        super.onDeviceFailedToConnect(device, reason)
        AppLogger.e(TAG, "onDeviceFailedToConnect")
        unbindService(mServiceConnection)
        findNavController(R.id.fragmentContainerView).popBackStack()
        findNavController(R.id.fragmentContainerView).navigate(
            R.id.move_to_device_error_screen
        )
    }
    override fun onDeviceConnected(device: BluetoothDevice) {
        super.onDeviceConnected(device)
        AppLogger.e(TAG, "onDeviceConnected")
        CoroutineScope(Dispatchers.IO).launch {
            delay(TimeUnit.SECONDS.toMillis(5))
            bluetoothViewModel._connectionStatus.tryEmit("Connected")
            delay(TimeUnit.SECONDS.toMillis(2))
            withContext(Dispatchers.Main) {
                startActivity(Intent(this@MainActivity,DashboardActivity::class.java))
               finish()
            }
        }

    }


    override fun onCreateView(savedInstanceState: Bundle?) {
        bluetoothViewModel.setNavigator(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setSupportActionBar(binding.toolbarOverViewScreen.toolbar)
        binding.toolbarOverViewScreen.let {
            it.toolbarTitle.text = "Connect ELD"
            it.toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
        }


        checkBluetoothDevicePermission()
    }


    private fun checkBluetoothDevicePermission() {
        if (PermissionUtilsNew.hasBluetoothPermission().not()) {
            Dexter.withContext(this@MainActivity)
                .withPermissions(PermissionUtilsNew.getBluetoothPermissionList())
                .withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(multiplePermissionsReport: MultiplePermissionsReport) {
                        if (multiplePermissionsReport.areAllPermissionsGranted()) {
                            AppLogger.e(TAG, "Permissions accepted: $multiplePermissionsReport")
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
        } else {
            AppLogger.e(TAG, "Permissions Already accept")
            checkBluetoothState()
        }
    }

    fun checkBluetoothState() {
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

    override fun onDeviceSelected(device: BluetoothDevice, name: String) {
        super<BluetoothNavigator>.onDeviceSelected(device, name)
        val intent = Intent(this@MainActivity, serviceClass).apply {
            this.putExtra(BleProfileService.EXTRA_DEVICE_ADDRESS, device.address)
            this.putExtra(BleProfileService.EXTRA_DEVICE_NAME, name)
        }
        startService(intent)
        bindService(intent, mServiceConnection, 0)
        onDeviceConnecting(device)
        findNavController(R.id.fragmentContainerView).navigate(
            R.id.move_to_device_connecting_screen, bundleOf( "name" to  device.address)
        )
        bluetoothViewModel._connectionStatus.tryEmit("Connecting to ...")
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this)
        super.onDestroy()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
    }
}