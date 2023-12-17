package com.dopsi.webapp.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment
import com.core.extensions.TAG
import com.core.extensions.hide
import com.core.extensions.invisible
import com.core.extensions.show
import com.core.interfaces.ItemClickListener
import com.core.utils.AppLogger
import com.core.utils.Utils
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.adapter.BluetoothDeviceListAdapter
import com.dopsi.webapp.databinding.FragmentBluetoothDeviceBinding
import com.dopsi.webapp.navigator.MovieNavigator
import com.dopsi.webapp.viewmodel.BluetoothViewModel
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.notify

@AndroidEntryPoint
class BLEDeviceFragment :
    BaseFragment<FragmentBluetoothDeviceBinding>(FragmentBluetoothDeviceBinding::inflate),
    MovieNavigator, ItemClickListener {
    private lateinit var bluetoothDeviceListAdapter: BluetoothDeviceListAdapter
    private val bluetoothViewModel: BluetoothViewModel by activityViewModels()


    override fun initUserInterface(view: View?) {

        initViewModelFlowCollectors()

        bluetoothDeviceListAdapter = BluetoothDeviceListAdapter(this)
        viewDataBinding.recDevices.apply {
            adapter = bluetoothDeviceListAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }


        if (Utils.isBluetoothSupported(requireContext()).not()) {
            showToast("This Device doest not support Bluetooth")
        } else {
            if (Utils.isBluetoothEnabled(requireContext()).not()) {
                activity?.let {
                    bluetoothViewModel.showBLEEnabledDialog()
                    showToast("Bluetooth is not enabled")
                }
            } else {
                  bluetoothViewModel.startScan(bluetoothViewModel.getUUID())
            }
        }

        viewDataBinding.btnScan.setOnSingleClickListener{
            viewDataBinding.btnScan.isEnabled = false
            setProgressVisibility(View.VISIBLE)
            bluetoothViewModel.startScan(bluetoothViewModel.getUUID())
        }

        viewDataBinding.btnContinue.isEnabled = false
        viewDataBinding.btnScan.isEnabled = false

    }

    private fun initViewModelFlowCollectors() {
        lifecycleScope.launchWhenStarted {
            bluetoothViewModel.discoveredDevices.onEach { results ->
                if (results.isNotEmpty()) {
                    updateDevicesList(results)
                    setProgressVisibility(View.INVISIBLE)
                    viewDataBinding.txtNoFound.hide()
                }
                else{
                    setProgressVisibility(View.INVISIBLE)
                    viewDataBinding.txtNoFound.show()
                }
                viewDataBinding.btnScan.isEnabled = true
                AppLogger.e(TAG, "data" + results)
            }.launchIn(this)
        }
    }

    private fun updateDevicesList(searchedList: List<ExtendedBluetoothDevice>) {
        lifecycleScope.launch {
            withContext(mainDispatcher) {
                val list = arrayListOf<ExtendedBluetoothDevice>()
                list.addAll(searchedList)
                bluetoothDeviceListAdapter.setItems(list)
            }
        }
    }

    override fun setProgressVisibility(visibility: Int) {
        viewDataBinding.progressIndicator.visibility = visibility
        if (visibility == View.VISIBLE) {
            viewDataBinding.txtNoFound.hide()
            viewDataBinding.recDevices.invisible()
        } else {
            viewDataBinding.recDevices.show()
        }
    }



    override fun onItemClick(position: Int, view: View) {
        //  val bundle = bundleOf("dataList" to (mainActivityViewModel.itemList as ArrayList<out Parcelable>))
        //  findNavController().navigate(R.id.move_to_second_fragment, bundle)
        bluetoothViewModel.scannedDevices.get(position).isBonded = true
        viewDataBinding.btnContinue.isEnabled = true
        bluetoothDeviceListAdapter.notifyDataSetChanged()
        super.onItemClick(position, view)
    }


}