package com.dopsi.webapp.utils

import android.view.LayoutInflater

typealias Inflate<T> = (LayoutInflater) -> T
typealias TextAndToneInputListener = (inputText: String?, tones: Triple<Int, Int, Int>?) -> Unit