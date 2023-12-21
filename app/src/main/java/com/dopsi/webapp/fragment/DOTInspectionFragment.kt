package com.dopsi.webapp.fragment

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.Orientation
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.adapter.DOTListAdapter
import com.dopsi.webapp.databinding.FragmentDotInspectionBinding
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
    override fun initUserInterface(view: View?) {

        dotListAdapter = DOTListAdapter(this)
        viewDataBinding.recDotScreen.apply {
            adapter = dotListAdapter
            layoutManager = LinearLayoutManager(requireContext())
                addItemDecoration(
                    DividerItemDecoration(
                        this.context,1
                    )
                )
        }
        dotListAdapter.setItems(fragmentDOTData.getDOTItemList())
    }
}