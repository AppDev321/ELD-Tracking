package com.dopsi.webapp.fragment

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.core.utils.DialogManager
import com.core.utils.setOnSingleClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.FragmentCoDriverBinding
import com.dopsi.webapp.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class CoDriverFragment : BaseFragment<FragmentCoDriverBinding>(FragmentCoDriverBinding::inflate),
    ItemClickListener {

    override fun initUserInterface(view: View?) {

        viewDataBinding.edCoDriverName.setOnSingleClickListener {
            val checkedItem = 0
            val items = arrayOf(
                "Driver 1",
                "Driver 2",
            )
            dialogManager.singleChoiceListItemDialog(
                it.context,
                "Select Driver",
                "Select Driver",
                items,
                checkedItem,
                object : DialogManager.TypeAlertDialogItemClickListener {
                    override fun onItemTypeClicked(which: Int, type: String) {
                        viewDataBinding.edCoDriverName.text = items[which]
                    }
                }
            )
        }


    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.title = "Co-Driver"
        }

    }


}