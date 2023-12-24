package com.dopsi.webapp.fragment

import android.app.Activity
import android.content.Context
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import androidx.core.content.ContextCompat
import com.core.base.BaseFragment
import com.dopsi.webapp.R

import com.dopsi.webapp.databinding.FragmentEldLogsBinding


class ELDLogFragment : BaseFragment<FragmentEldLogsBinding>(FragmentEldLogsBinding::inflate) {
    override fun initUserInterface(view: View?) {


        val webSettings: WebSettings = viewDataBinding.graphWebView.settings
        webSettings.javaScriptEnabled = true
        viewDataBinding.graphWebView.webChromeClient = WebChromeClient()
        viewDataBinding.graphWebView.loadUrl("file:///android_asset/index.html")

    }


}