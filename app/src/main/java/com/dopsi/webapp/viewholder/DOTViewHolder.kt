package com.dopsi.webapp.viewholder

import android.view.View
import com.core.base.BaseViewHolder
import com.core.extensions.hide
import com.core.extensions.show
import com.dopsi.webapp.databinding.ItemBleDeviceBinding
import com.dopsi.webapp.databinding.ItemDotInspectionFragmentBinding
import com.dopsi.webapp.model.DOTData
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice

class DOTViewHolder(
    private var item: ItemDotInspectionFragmentBinding
) : BaseViewHolder<DOTData>(item.root) {

    override fun bindItem(data: DOTData) {
       item.txtTitle.text = data.title
        item.txtDesc.visibility = View.GONE
        item.btnAction.text = data.button
        data.desc?.let{
            item.txtDesc.visibility = View.VISIBLE
            item.txtDesc.text = data.desc
        }




    }
}