package com.dopsi.webapp.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.extensions.TAG
import com.core.interfaces.BaseNavigator
import com.core.utils.AppLogger
import com.core.utils.navigateSafe
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.bussinesslogic.model.DriveTimeModel
import com.dopsi.webapp.bussinesslogic.model.ShiftTimeModel
import com.dopsi.webapp.bussinesslogic.model.WeekTimeModel
import com.dopsi.webapp.databinding.FragmentDashboardBinding
import com.dopsi.webapp.events.ShiftTimeEndEvent
import com.dopsi.webapp.events.ShiftTimeUpdateEvent
import com.dopsi.webapp.intefaces.DriverStatusEvent
import com.dopsi.webapp.navigator.DashboardNavigator
import com.dopsi.webapp.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode



@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate), BaseNavigator,
    DashboardNavigator {
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dashboardViewModel.setNavigator(this)
    }
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
            it.supportActionBar?.title = "Dashboard"
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDriverStatusEvent(status: DriverStatusEvent) {
        AppLogger.e("Status", status.status.toString())
        viewDataBinding.btnStatusChange.text = status.status.toString()
    }

    override fun updateShiftTime(model: ShiftTimeModel) {
        with(model) {
            with(viewDataBinding.pgShift) {
                val progress = progressPercentage.toInt()
                setPercentage(progress)
                setStepCountText(consumedTime)
            }
        }
    }

    override fun updateWeekTime(model: WeekTimeModel) {
        with(model) {
            with(viewDataBinding.pgCycle) {
                val progress = progressPercentage.toInt()
                setPercentage(progress)
                setStepCountText(consumedTime)
            }
        }
    }

    override fun updateDriveTime(model: DriveTimeModel) {
        model.let {
            viewDataBinding.apply {
                driveProgress.progress = it.progressPercentage
                txtDriveTime.text = it.consumedTime
            }
        }
    }


}