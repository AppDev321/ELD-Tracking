package com.dopsi.webapp.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.core.utils.DialogManager
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.FragmentCoDriverBinding
import com.dopsi.webapp.databinding.FragmentFormBinding
import com.dopsi.webapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FormFragment : BaseFragment<FragmentFormBinding>(FragmentFormBinding::inflate),
    ItemClickListener {

    override fun initUserInterface(view: View?) {




    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.title = "Form"
        }

    }


}