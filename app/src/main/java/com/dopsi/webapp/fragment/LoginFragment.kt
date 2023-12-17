package com.dopsi.webapp.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate),
    ItemClickListener {

    override fun initUserInterface(view: View?) {
        viewDataBinding.btnLogin.setOnSingleClickListener{
            findNavController().navigate(R.id.move_to_bluetooth_listing_screen)
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