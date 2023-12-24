package com.dopsi.webapp.viewholder

import android.view.View
import com.core.base.BaseViewHolder
import com.core.extensions.hide
import com.core.extensions.show
import com.dopsi.webapp.databinding.ItemAccountBinding
import com.dopsi.webapp.databinding.ItemBleDeviceBinding
import com.dopsi.webapp.databinding.ItemDotInspectionFragmentBinding
import com.dopsi.webapp.databinding.ItemVehicleBinding
import com.dopsi.webapp.model.AccountData
import com.dopsi.webapp.model.ClickEvent
import com.dopsi.webapp.model.DOTData
import com.dopsi.webapp.model.VehicleData
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice

class VehicleViewHolder(
    private var item: ItemVehicleBinding,
    var event: (ClickEvent) -> Unit?,
) : BaseViewHolder<VehicleData>(item.root) {

    override fun bindItem(data: VehicleData) {
       item.txtVehicleNumber.text = data.title
        item.txtVehicleDesc.text = data.desc

    }
}