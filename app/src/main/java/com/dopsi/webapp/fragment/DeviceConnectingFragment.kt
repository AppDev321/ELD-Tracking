package com.dopsi.webapp.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.data.model.MovieResponse
import com.core.extensions.TAG
import com.core.extensions.hide
import com.core.extensions.show
import com.core.interfaces.ItemClickListener
import com.core.utils.AppLogger
import com.core.utils.getParcelableArrayListCompat
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.FragmentDeviceConnectingBinding
import com.dopsi.webapp.databinding.FragmentLoginBinding
import com.dopsi.webapp.viewmodel.BluetoothViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext


@AndroidEntryPoint
class DeviceConnectingFragment : BaseFragment<FragmentDeviceConnectingBinding>(FragmentDeviceConnectingBinding::inflate),
    ItemClickListener {
    private val bluetoothViewModel: BluetoothViewModel by activityViewModels()


    override fun initUserInterface(view: View?) {
        lifecycleScope.launchWhenStarted {
            bluetoothViewModel.connectionStatus.onEach { results ->
                withContext(Dispatchers.Main)
                {
                    viewDataBinding.txtConnectionStatus.text = results
                }
            }.launchIn(this)
        }

        arguments?.let {
            val data = it.getString("name")
            data?.let{
                viewDataBinding.txtDeviceAddress.text = data

            }
        }

    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.hide()
        }

    }

    override fun onStop() {
        super.onStop()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.show()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.show()
        }
    }

}