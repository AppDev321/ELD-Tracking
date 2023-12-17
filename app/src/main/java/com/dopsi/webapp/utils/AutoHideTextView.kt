package com.dopsi.webapp.utils
import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class AutoHideTextView(context: Context, attrs: AttributeSet) : AppCompatTextView(context, attrs) {
    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        updateVisibility()
    }

    private fun updateVisibility() {
        visibility = if (text.isNullOrEmpty()) {
            GONE
        } else {
            VISIBLE
        }
    }
}
