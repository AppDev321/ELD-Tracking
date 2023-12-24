package com.dopsi.webapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.base.BaseRecyclerAdapter
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.databinding.ItemAccountBinding
import com.dopsi.webapp.databinding.ItemDotInspectionFragmentBinding
import com.dopsi.webapp.databinding.ItemVehicleBinding
import com.dopsi.webapp.model.AccountData
import com.dopsi.webapp.model.ClickEvent
import com.dopsi.webapp.model.DOTData
import com.dopsi.webapp.model.VehicleData
import com.dopsi.webapp.viewholder.AccountViewHolder
import com.dopsi.webapp.viewholder.DOTViewHolder
import com.dopsi.webapp.viewholder.VehicleViewHolder

class VehicleAdapter(
    private var itemClickListener: ItemClickListener,
    var event: (ClickEvent) -> Unit?
) :
    BaseRecyclerAdapter<VehicleData>(itemClickListener) {

    override fun createBaseViewHolder(parent: ViewGroup, viewType: Int) =
        VehicleViewHolder(
            ItemVehicleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),event
        )
}