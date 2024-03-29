package com.dopsi.webapp.fragment

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.utils.AppLogger
import com.core.utils.navigateSafe
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.FragmentDashboardBinding
import com.dopsi.webapp.intefaces.DriverStatusEvent
import com.dopsi.webapp.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@AndroidEntryPoint
class DashboardFragment :
    BaseFragment<FragmentDashboardBinding>(FragmentDashboardBinding::inflate) {
    private val dashboardViewModel: DashboardViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initUserInterface(view: View?) {
        initViewModelObserver()

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

    private fun initViewModelObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            launch {
                dashboardViewModel.shiftTimeFlow.collect {
                    with(it) {
                        with(viewDataBinding.pgShift) {
                            val progress = progressPercentage.toInt()
                            setPercentage(progress)
                            setStepCountText(consumedTime)
                        }
                    }
                }
            }
            launch {
                dashboardViewModel.weekCycleTimeFlow.collect {
                    with(it)
                    {
                        with(viewDataBinding.pgCycle) {
                            val progress = progressPercentage.toInt()
                            setPercentage(progress)
                            setStepCountText(consumedTime)
                        }
                    }
                }
            }

            launch {
                dashboardViewModel.driveTimeFlow.collect {
                    with(it)
                    {
                        viewDataBinding.apply {
                            driveProgress.progress = progressPercentage
                            txtDriveTime.text = consumedTime
                        }
                    }

                }
            }
            launch {
                dashboardViewModel.breakTimeFlow.collect {
                    with(it) {
                        with(viewDataBinding.pgBreak) {
                            val progress = progressPercentage.toInt()
                            setPercentage(progress)
                            setStepCountText(consumedTime)
                        }
                    }
                }
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onDriverStatusEvent(status: DriverStatusEvent) {
        AppLogger.e("Status", status.status.toString())
        viewDataBinding.btnStatusChange.text = status.status.toString()
    }


}