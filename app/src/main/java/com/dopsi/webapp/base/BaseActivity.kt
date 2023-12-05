package com.dopsi.webapp.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dopsi.webapp.BaseApplication
import com.dopsi.webapp.interfaces.NetworkInterface
import com.dopsi.webapp.utils.Inflate

abstract class BaseActivity<VB : ViewBinding>(private val inflate: Inflate<VB>) :  AppCompatActivity(), NetworkInterface {

    private var _binding: ViewBinding? = null
    protected val viewDataBinding: VB
        get() = _binding as VB

    protected abstract fun initUserInterface()
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(BaseApplication.instance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInternetConnection()
        _binding = inflate.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initUserInterface()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun checkInternetConnection() {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)

        if (capabilities != null &&
            (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))
        ) {
            onNetworkConnected()
        } else {
            onNetworkDisconnected()
        }
    }

}
