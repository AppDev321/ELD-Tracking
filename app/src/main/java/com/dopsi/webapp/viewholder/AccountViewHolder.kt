package com.dopsi.webapp.viewholder

import android.view.View
import com.core.base.BaseViewHolder
import com.core.extensions.hide
import com.core.extensions.show
import com.dopsi.webapp.databinding.ItemAccountBinding
import com.dopsi.webapp.databinding.ItemBleDeviceBinding
import com.dopsi.webapp.databinding.ItemDotInspectionFragmentBinding
import com.dopsi.webapp.model.AccountData
import com.dopsi.webapp.model.ClickEvent
import com.dopsi.webapp.model.DOTData
import com.pt.devicemanager.scanner.ExtendedBluetoothDevice

class AccountViewHolder(
    private var item: ItemAccountBinding,
    var event: (ClickEvent) -> Unit?,
) : BaseViewHolder<AccountData>(item.root) {

    override fun bindItem(data: AccountData) {
       item.txtLabel.text = data.title
        item.txtData.text = data.desc

    }
}