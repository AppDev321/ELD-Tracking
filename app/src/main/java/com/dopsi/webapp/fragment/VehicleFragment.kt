package com.dopsi.webapp.fragment

import android.annotation.SuppressLint
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.R
import com.dopsi.webapp.activity.DashboardActivity
import com.dopsi.webapp.adapter.AccountAdapter
import com.dopsi.webapp.adapter.DOTListAdapter
import com.dopsi.webapp.adapter.VehicleAdapter

import com.dopsi.webapp.databinding.FragmentRecyclerViewBinding
import com.dopsi.webapp.model.AccountData
import com.dopsi.webapp.model.AccountDataFactory
import com.dopsi.webapp.model.ClickEvent
import com.dopsi.webapp.model.DOTData
import com.dopsi.webapp.model.FragmentDTODataFactory
import com.dopsi.webapp.model.VehicleData
import com.dopsi.webapp.model.VehicleDataFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class VehicleFragment :
    BaseFragment<FragmentRecyclerViewBinding>(FragmentRecyclerViewBinding::inflate),
    ItemClickListener {
    @Inject
    lateinit var vehicleDataFactory: VehicleDataFactory
    private lateinit var vehicleAdapter: VehicleAdapter
    private lateinit var vehicleData: ArrayList<VehicleData>

    @SuppressLint("SuspiciousIndentation")
    override fun initUserInterface(view: View?) {

        vehicleAdapter = VehicleAdapter(this, events)
        viewDataBinding.recylerView.apply {
            adapter = vehicleAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        vehicleData = vehicleDataFactory.getVehicleData()
        vehicleAdapter.setItems(vehicleData)
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
        val item = vehicleData[position];

    }

    override fun onResume() {
        super.onResume()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.title= "Vehicle"
        }

    }

}