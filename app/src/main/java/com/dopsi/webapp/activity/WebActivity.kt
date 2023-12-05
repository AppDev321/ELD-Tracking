package com.dopsi.webapp.activity

import android.Manifest
import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.dopsi.webapp.databinding.ActivityWebViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WebActivity: AppCompatActivity(){

    private  val MY_CAMERA_PERMISSION_CODE = 100
    private  val REQUEST_FILE_PICKER = 1
    private val URL = "https://mis.dopasi.org/transportation/"
    private var isAlreadyCreated = false
    private  lateinit var binding: ActivityWebViewBinding
    private var filePathCallback: ValueCallback<Array<Uri>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), MY_CAMERA_PERMISSION_CODE)
        } else {
            setupWebView()
        }
    }

    override fun onResume() {
        super.onResume()

        if (isAlreadyCreated && !isNetworkAvailable()) {
            isAlreadyCreated = false
            showErrorDialog("Error", "No internet connection. Please check your connection.",
                this@WebActivity)
        }
    }
    private fun setupWebView() {
        startLoaderAnimate()
        binding.apply {

            webView.settings.javaScriptEnabled = true
            webView.settings.domStorageEnabled = true
            webView.settings.databaseEnabled = true
            webView.settings.loadsImagesAutomatically = true
            webView.settings.allowContentAccess = true
            webView.settings.allowFileAccess = true
            webView.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    endLoaderAnimate()
                }


                override fun onReceivedError(
                    view: WebView?,
                    request: WebResourceRequest?,
                    error: WebResourceError?
                ) {
                    super.onReceivedError(view, request, error)
                    endLoaderAnimate()
                    showErrorDialog(
                        "Error",
                        "No internet connection. Please check your connection.",
                        this@WebActivity
                    )
                }
            }
            webView.webChromeClient = object:WebChromeClient(){
                override fun onShowFileChooser(
                    webView: WebView?,
                    filePathCallback: ValueCallback<Array<Uri>>?,
                    fileChooserParams: FileChooserParams
                ): Boolean {
                    this@WebActivity.filePathCallback = filePathCallback

                    // Launch the file picker activity using the activity result API
                    val intent = fileChooserParams.createIntent()
                    startActivityForResult(intent, REQUEST_FILE_PICKER)

                    return true
                }

            }

            webView.loadUrl(URL)
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectionManager =
            this@WebActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectionManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnectedOrConnecting
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        binding.apply {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack()
            return true
        }
            }
        return super.onKeyDown(keyCode, event)
    }

    private fun showErrorDialog(title: String, message: String, context: Context) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(message)
        dialog.setNegativeButton("Cancel", { _, _ ->
            this@WebActivity.finish()
        })
        dialog.setNeutralButton("Settings", {_, _ ->
            startActivity(Intent(Settings.ACTION_SETTINGS))
        })
        dialog.setPositiveButton("Retry", { _, _ ->
            this@WebActivity.recreate()
        })
        dialog.create().show()
    }

    private fun endLoaderAnimate() {
        binding.apply {
            loaderImage.clearAnimation()
            loaderImage.visibility = View.GONE
        }
    }

    private fun startLoaderAnimate() {
        val objectAnimator = object : Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                val startHeight = 170
                val newHeight = (startHeight + (startHeight + 40) * interpolatedTime).toInt()
                binding.apply {
                    loaderImage.layoutParams.height = newHeight
                    loaderImage.requestLayout()
                }
            }

            override fun initialize(width: Int, height: Int, parentWidth: Int, parentHeight: Int) {
                super.initialize(width, height, parentWidth, parentHeight)
            }

            override fun willChangeBounds(): Boolean {
                return true
            }
        }

        objectAnimator.repeatCount = -1
        objectAnimator.repeatMode = ValueAnimator.REVERSE
        objectAnimator.duration = 1000
        binding.apply {
            loaderImage.startAnimation(objectAnimator)
        }
    }

    // Handle the result of the permission request
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        when (requestCode) {
            MY_CAMERA_PERMISSION_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission granted, setup WebView
                    setupWebView()
                } else {
                    // Permission denied, handle accordingly
                    // You might want to inform the user that the camera won't work without the required permission
                }
            }
            else -> {
                // Handle other permission requests if needed
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    // Handle the result of the file picker activity
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_FILE_PICKER) {
            if (resultCode == Activity.RESULT_OK) {
                handleFilePickerResult(resultCode,data)
            } else {
                // Cancelled or failed, notify the WebView
                filePathCallback?.onReceiveValue(null)
            }

            // Reset the callback
            filePathCallback = null
        }
    }

    // Handle the result of the file picker activity and notify the WebView
    private fun handleFilePickerResult(resultCode: Int,data: Intent?) {
         val result = WebChromeClient.FileChooserParams.parseResult(resultCode, data)

        if (result != null) {
            filePathCallback?.onReceiveValue(result)
        } else {
            filePathCallback?.onReceiveValue(null)
        }
    }
}