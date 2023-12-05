package com.dopsi.webapp.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import com.dopsi.webapp.databinding.ProgressDialogViewBinding
import com.dopsi.webapp.utils.Utils

class CustomProgressDialog {

    var dialog: CustomDialog? = null

    fun show(activity: Activity, title: CharSequence?): Dialog? {
        val viewBinding = ProgressDialogViewBinding.inflate(activity.layoutInflater)
        viewBinding.textViewProgressDescription.apply {
            title?.let { text = it }
            setTextColor(Color.WHITE)
        }
        dialog = CustomDialog(activity)
        dialog?.setContentView(viewBinding.root)
        dialog?.show()
        return dialog
    }

    private fun setColorFilter(drawable: Drawable, color: Int) {
        if (Utils.androidQAndAbove) {
            drawable.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_ATOP)
        } else {
            drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        }
    }

    class CustomDialog(context: Context) : Dialog(context) {
        init {
            window?.decorView?.setOnApplyWindowInsetsListener { _, insets ->
                insets.consumeSystemWindowInsets()
            }
            setCancelable(false)
        }
    }
}