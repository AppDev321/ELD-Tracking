package com.dopsi.webapp.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.data.model.MovieResponse
import com.core.extensions.TAG
import com.core.interfaces.ItemClickListener
import com.core.utils.AppLogger
import com.core.utils.getParcelableArrayListCompat
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.activity.DashboardActivity
import com.dopsi.webapp.databinding.FragmentDeviceConnectingBinding
import com.dopsi.webapp.databinding.FragmentDeviceErrorBinding
import com.dopsi.webapp.databinding.FragmentInformationBinding
import com.dopsi.webapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class InformationFragment : BaseFragment<FragmentInformationBinding>(FragmentInformationBinding::inflate) {
    override fun initUserInterface(view: View?) {
    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.title=  "Information Packet"
        }
    }

}