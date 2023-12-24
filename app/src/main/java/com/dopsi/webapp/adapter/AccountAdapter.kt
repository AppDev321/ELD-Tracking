package com.dopsi.webapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.core.base.BaseRecyclerAdapter
import com.core.interfaces.ItemClickListener
import com.dopsi.webapp.databinding.ItemAccountBinding
import com.dopsi.webapp.databinding.ItemDotInspectionFragmentBinding
import com.dopsi.webapp.model.AccountData
import com.dopsi.webapp.model.ClickEvent
import com.dopsi.webapp.model.DOTData
import com.dopsi.webapp.viewholder.AccountViewHolder
import com.dopsi.webapp.viewholder.DOTViewHolder

class AccountAdapter(
    private var itemClickListener: ItemClickListener,
    var event: (ClickEvent) -> Unit?
) :
    BaseRecyclerAdapter<AccountData>(itemClickListener) {

    override fun createBaseViewHolder(parent: ViewGroup, viewType: Int) =
        AccountViewHolder(
            ItemAccountBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),event
        )
}