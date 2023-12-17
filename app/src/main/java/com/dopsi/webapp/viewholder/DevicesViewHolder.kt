package com.dopsi.webapp.viewholder

import com.core.base.BaseViewHolder
import com.core.extensions.hide
import com.core.extensions.show
import com.dopsi.webapp.databinding.ItemBleDeviceBinding
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice

class DevicesViewHolder(
    private var itemBleDeviceBinding: ItemBleDeviceBinding
) : BaseViewHolder<ExtendedBluetoothDevice>(itemBleDeviceBinding.root) {

    override fun bindItem(item: ExtendedBluetoothDevice) {

        itemBleDeviceBinding.txtName.text = item.name
        itemBleDeviceBinding.txtDeviceAddress.text = item.device?.address

        if (item.isBonded)
            itemBleDeviceBinding.imgSelectedDevice.show()
        else
            itemBleDeviceBinding.imgSelectedDevice.hide()


        val context = itemBleDeviceBinding.root.context


    }
}