package com.dopsi.webapp.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.data.model.MovieResponse
import com.core.extensions.TAG
import com.core.interfaces.ItemClickListener
import com.core.utils.AppLogger
import com.core.utils.getParcelableArrayListCompat
import com.core.utils.navigateSafe
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.activity.DashboardActivity
import com.dopsi.webapp.databinding.FragmentDashboardBinding
import com.dopsi.webapp.databinding.FragmentDeviceConnectingBinding
import com.dopsi.webapp.databinding.FragmentDeviceErrorBinding
import com.dopsi.webapp.databinding.FragmentInformationBinding
import com.dopsi.webapp.databinding.FragmentLoginBinding
import com.dopsi.webapp.intefaces.DriverStatusEvent
import com.dopsi.webapp.model.DriverStatus
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.util.logging.Level.OFF


@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {
    override fun initUserInterface(view: View?) {

        viewDataBinding.btnLogs.setOnClickListener {
            findNavController().navigate(R.id.move_to_eld_logs_screen)

        }

        viewDataBinding.btnStatusChange.setOnSingleClickListener {
            findNavController().navigateSafe(
                R.id.action_fragment_dashboard_to_status_change_dialog,
            )
        }
    }

    override fun onResume() {
        super.onResume()
        if (EventBus.getDefault().isRegistered(this).not())
            EventBus.getDefault().register(this)

        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.title= "Dashboard"
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        EventBus.getDefault().unregister(this)

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDriverStatusEvent(status: DriverStatusEvent) {
        AppLogger.e("Status", status.status.toString())
        viewDataBinding.btnStatusChange.text = status.status.toString()
    }

}