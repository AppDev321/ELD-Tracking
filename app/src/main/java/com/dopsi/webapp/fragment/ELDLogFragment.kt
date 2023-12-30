package com.dopsi.webapp.fragment

import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import com.core.base.BaseFragment
import com.core.extensions.TAG
import com.core.utils.AppLogger
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.FragmentEldLogsBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ELDLogFragment : BaseFragment<FragmentEldLogsBinding>(FragmentEldLogsBinding::inflate) {
    override fun initUserInterface(view: View?) {

        //   setupActionBarWithNavController(activity as DashboardActivity, navController)
        setupSmoothBottomMenu()
    }

    private fun setupSmoothBottomMenu() {

        viewDataBinding.bottomBar.setOnItemSelectedListener {
            val navController = findNavController(viewDataBinding.mainFragment)
            navController.popBackStack()
            when (it) {
                0 -> {
                    navController.navigate(R.id.event_fragment)
                }
                1 -> {
                    navController.navigate(R.id.forms_fragment)
                }
                else -> navController.navigate(R.id.certify_fragment)
            }
        }
    }

}