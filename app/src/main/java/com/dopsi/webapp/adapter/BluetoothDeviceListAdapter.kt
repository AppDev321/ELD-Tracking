package com.dopsi.webapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.base.BaseRecyclerAdapter
import com.core.data.model.MovieResponse
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.databinding.ItemBleDeviceBinding
import com.dopsi.webapp.databinding.ItemMovieBinding
import com.dopsi.webapp.viewholder.DevicesViewHolder
import com.dopsi.webapp.viewholder.MovieViewHolder
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice

class BluetoothDeviceListAdapter(private var itemClickListener: ItemClickListener) :
    BaseRecyclerAdapter<ExtendedBluetoothDevice>(itemClickListener) {

    override fun createBaseViewHolder(parent: ViewGroup, viewType: Int) =
        DevicesViewHolder(
            ItemBleDeviceBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
}