package com.core.base

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.core.BaseApplication
import com.core.interfaces.NetworkInterface
import com.core.service.socket.SocketManager
import com.core.utils.Inflate
import javax.inject.Inject

abstract class BaseActivity<VB : ViewBinding>(private val inflate: Inflate<VB>) :  AppCompatActivity(),
    NetworkInterface {

    private var _binding: VB? = null
    protected val viewDataBinding: VB
        get() = _binding!!

    protected abstract fun initUserInterface()

    /*@Inject
    lateinit var socketManager: SocketManager*/

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(BaseApplication.instance)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkInternetConnection()
        _binding = inflate.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)
        initUserInterface()

     //   socketManager.connect()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }
}
