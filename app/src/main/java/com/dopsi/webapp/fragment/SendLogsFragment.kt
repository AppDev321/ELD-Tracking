package com.dopsi.webapp.fragment

import android.annotation.SuppressLint
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.adapter.DOTListAdapter
import com.dopsi.webapp.databinding.FragmentDotInspectionBinding
import com.dopsi.webapp.databinding.FragmentSendLogsBinding
import com.dopsi.webapp.model.FragmentDTODataFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SendLogsFragment :
    BaseFragment<FragmentSendLogsBinding>(FragmentSendLogsBinding::inflate),
    ItemClickListener {

    @SuppressLint("SuspiciousIndentation")
    override fun initUserInterface(view: View?) {

        val logTypes = resources.getStringArray(R.array.log_type)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, logTypes)
        viewDataBinding.txtDataTransferType.setAdapter(arrayAdapter)
        viewDataBinding.txtDataTransferType.hint = "Select"

    }
}