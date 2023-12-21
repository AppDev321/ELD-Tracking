package com.dopsi.webapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.base.BaseRecyclerAdapter
import com.core.data.model.MovieResponse
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.databinding.ItemBleDeviceBinding
import com.dopsi.webapp.databinding.ItemDotInspectionFragmentBinding
import com.dopsi.webapp.databinding.ItemMovieBinding
import com.dopsi.webapp.model.DOTData
import com.dopsi.webapp.viewholder.DOTViewHolder
import com.dopsi.webapp.viewholder.DevicesViewHolder
import com.dopsi.webapp.viewholder.MovieViewHolder
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice

class DOTListAdapter(private var itemClickListener: ItemClickListener) :
    BaseRecyclerAdapter<DOTData>(itemClickListener) {

    override fun createBaseViewHolder(parent: ViewGroup, viewType: Int) =
        DOTViewHolder(
            ItemDotInspectionFragmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}