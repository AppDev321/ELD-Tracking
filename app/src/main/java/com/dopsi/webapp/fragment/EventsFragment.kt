package com.dopsi.webapp.fragment

import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.recyclerview.widget.LinearLayoutManager
import com.core.base.BaseFragment

import com.dopsi.webapp.databinding.FragmentEldLogsBinding
import com.dopsi.webapp.databinding.FragmentEventsBinding

import com.dopsi.webapp.utils.chartUtil.ELDGraph

import com.github.mikephil.charting.data.Entry
import dagger.hilt.android.AndroidEntryPoint

import javax.inject.Inject

@AndroidEntryPoint
class EventsFragment : BaseFragment<FragmentEventsBinding>(FragmentEventsBinding::inflate) {
    private val dataSet: MutableList<Entry> = mutableListOf()

    @Inject
    lateinit var eldChart: ELDGraph


    override fun initUserInterface(view: View?) {
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


}