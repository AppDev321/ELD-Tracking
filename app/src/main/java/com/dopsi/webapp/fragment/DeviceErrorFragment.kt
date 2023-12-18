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
import com.dopsi.webapp.databinding.FragmentDeviceConnectingBinding
import com.dopsi.webapp.databinding.FragmentDeviceErrorBinding
import com.dopsi.webapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DeviceErrorFragment : BaseFragment<FragmentDeviceErrorBinding>(FragmentDeviceErrorBinding::inflate),
    ItemClickListener {


    override fun initUserInterface(view: View?) {
      viewDataBinding.btnBack.setOnSingleClickListener{
          activity?.onBackPressed()
      }

    }

}