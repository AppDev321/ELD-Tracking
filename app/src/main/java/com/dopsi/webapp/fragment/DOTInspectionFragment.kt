package com.dopsi.webapp.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.adapter.DOTListAdapter
import com.dopsi.webapp.databinding.FragmentDotInspectionBinding
import com.dopsi.webapp.model.ClickEvent
import com.dopsi.webapp.model.DOTData
import com.dopsi.webapp.model.FragmentDTODataFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class DOTInspectionFragment :
    BaseFragment<FragmentDotInspectionBinding>(FragmentDotInspectionBinding::inflate),
    ItemClickListener {
    @Inject
    lateinit var fragmentDOTData: FragmentDTODataFactory
    private lateinit var dotListAdapter: DOTListAdapter
    private lateinit var dotDataList: ArrayList<DOTData>

    @SuppressLint("SuspiciousIndentation")
    override fun initUserInterface(view: View?) {

        dotListAdapter = DOTListAdapter(this, events)
        viewDataBinding.recDotScreen.apply {
            adapter = dotListAdapter
            layoutManager = LinearLayoutManager(requireContext())
            this.addItemDecoration(
                DividerItemDecoration(
                    this.context, 1
                )
            )
        }
        dotDataList = fragmentDOTData.getDOTItemList()
        dotListAdapter.setItems(dotDataList)
    }

    var events: (ClickEvent) -> Unit?
        get() = { event ->
            when (event) {
                is ClickEvent.ItemClick -> {
                    onItemClick(event.pos)
                }
            }
        }
        set(value) {}

    private fun onItemClick(position: Int) {
        val item = dotDataList[position];
        when (item.viewType) {
            FragmentDTODataFactory.ITEM_TYPE.LOGS -> {
                findNavController().navigate(R.id.move_to_send_logs_screen)
            }

            else -> findNavController().navigate(R.id.move_to_send_logs_screen)

        }
    }

}