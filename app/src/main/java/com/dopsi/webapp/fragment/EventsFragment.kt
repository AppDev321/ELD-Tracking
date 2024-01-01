package com.dopsi.webapp.fragment

import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment
import com.core.interfaces.MenuItemClickListener
import com.dopsi.webapp.R

import com.dopsi.webapp.databinding.FragmentEldLogsBinding
import com.dopsi.webapp.databinding.FragmentEventsBinding

import com.dopsi.webapp.utils.chartUtil.ELDGraph

import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>(FragmentEventsBinding::inflate),
    MenuItemClickListener {
    private val dataSet: MutableList<Entry> = mutableListOf()

    @Inject
    lateinit var eldChart: ELDGraph


    override fun initUserInterface(view: View?) {

        setFragmentMenu(R.menu.event_frament_menu,this)
        viewDataBinding.recLogs.apply {
            layoutManager = LinearLayoutManager(requireContext())
        }

        dataSet.clear()
        dataSet.add(0, Entry(0f, -0.55f))
        dataSet.add(1, Entry(12f, 0.8f))
        dataSet.add(2, Entry(15f, 1.8f))
        dataSet.add(3, Entry(23f, 2.8f))


        eldChart.showChart(viewDataBinding.chart,dataSet)

    }


    override fun onResume() {
        super.onResume()
        activity?.let {
            it as AppCompatActivity
            it.supportActionBar?.title = "Events"
        }

    }

    override fun setMenuItemListener(menuItem: MenuItem) {
        when(menuItem.itemId)
        {
            R.id.event_detail-> showToast("Menu 1")
            R.id.event_add-> showToast("Menu 2")
        }
        super.setMenuItemListener(menuItem)
    }



}