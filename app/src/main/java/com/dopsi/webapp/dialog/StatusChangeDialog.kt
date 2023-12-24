package com.dopsi.webapp.dialog

import android.os.Bundle
import android.view.View
import com.core.base.BaseDialogFragment
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.DialogStatusChangeBinding
import com.dopsi.webapp.intefaces.DriverStatusEvent
import com.dopsi.webapp.model.DriverStatus
import dagger.hilt.android.AndroidEntryPoint
import org.greenrobot.eventbus.EventBus

@AndroidEntryPoint
class StatusChangeDialog :
    BaseDialogFragment<DialogStatusChangeBinding>(DialogStatusChangeBinding::inflate),
    View.OnClickListener {
    private var statusSelect = DriverStatus.OFFDUTY
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.DialogThemeCustom)
    }

    override fun initUserInterface(view: View?) {
        viewDataBinding.tgPc.setOnClickListener(this)
        viewDataBinding.tgYard.setOnClickListener(this)
        viewDataBinding.tgDriving.setOnClickListener(this)
        viewDataBinding.tgOffDuty.setOnClickListener(this)
        viewDataBinding.tgOnDuty.setOnClickListener(this)
        viewDataBinding.tgSleeper.setOnClickListener(this)
        viewDataBinding.txtCancel.setOnClickListener {
            dismiss()
        }
        viewDataBinding.txtConfirm.setOnClickListener {
            EventBus.getDefault().post(DriverStatusEvent(statusSelect))
            dismiss()
        }
    }

    override fun onClick(v: View) {
        statusSelect = when (v.id) {
            viewDataBinding.tgPc.id -> {
                DriverStatus.PC
            }

            viewDataBinding.tgYard.id -> {
                DriverStatus.YARD
            }

            viewDataBinding.tgDriving.id -> {
                DriverStatus.DRIVING
            }

            viewDataBinding.tgOnDuty.id -> {
                DriverStatus.ONDUTY
            }

            viewDataBinding.tgOffDuty.id -> {
                DriverStatus.OFFDUTY
            }

            else -> {
                DriverStatus.SLEEPER
            }

        }
        handleUISelection(statusSelect)

        /* lifecycleScope.launch(Dispatchers.Main) {
             delay(100)
             dismiss()
         }*/
    }

    private fun handleUISelection(status: DriverStatus) {
        when (status) {
            DriverStatus.DRIVING -> {
                viewDataBinding.tgOnDuty.isChecked = false
                viewDataBinding.tgYard.isChecked = false
                viewDataBinding.tgPc.isChecked = false
                viewDataBinding.tgOffDuty.isChecked = false
                viewDataBinding.tgDriving.isChecked = true
                viewDataBinding.tgSleeper.isChecked = false
            }

            DriverStatus.OFFDUTY -> {
                viewDataBinding.tgOnDuty.isChecked = false
                viewDataBinding.tgYard.isChecked = false
                viewDataBinding.tgPc.isChecked = false
                viewDataBinding.tgOffDuty.isChecked = true
                viewDataBinding.tgDriving.isChecked = false
                viewDataBinding.tgSleeper.isChecked = false
            }

            DriverStatus.PC -> {
                viewDataBinding.tgOnDuty.isChecked = false
                viewDataBinding.tgYard.isChecked = false
                viewDataBinding.tgPc.isChecked = true
                viewDataBinding.tgOffDuty.isChecked = false
                viewDataBinding.tgDriving.isChecked = false
                viewDataBinding.tgSleeper.isChecked = false
            }

            DriverStatus.YARD -> {
                viewDataBinding.tgOnDuty.isChecked = false
                viewDataBinding.tgYard.isChecked = true
                viewDataBinding.tgPc.isChecked = false
                viewDataBinding.tgOffDuty.isChecked = false
                viewDataBinding.tgDriving.isChecked = false
                viewDataBinding.tgSleeper.isChecked = false
            }

            DriverStatus.ONDUTY -> {
                viewDataBinding.tgOnDuty.isChecked = true
                viewDataBinding.tgYard.isChecked = false
                viewDataBinding.tgPc.isChecked = false
                viewDataBinding.tgOffDuty.isChecked = false
                viewDataBinding.tgDriving.isChecked = false
                viewDataBinding.tgSleeper.isChecked = false
            }

            else -> {
                viewDataBinding.tgOnDuty.isChecked = false
                viewDataBinding.tgYard.isChecked = false
                viewDataBinding.tgPc.isChecked = false
                viewDataBinding.tgOffDuty.isChecked = false
                viewDataBinding.tgDriving.isChecked = false
                viewDataBinding.tgSleeper.isChecked = true
            }
        }
    }

}