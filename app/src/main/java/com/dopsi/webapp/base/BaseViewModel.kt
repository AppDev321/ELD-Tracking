package com.dopsi.webapp.base

import androidx.lifecycle.ViewModel
import com.dopsi.webapp.module.IODispatcher
import com.dopsi.webapp.module.MainDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import java.lang.ref.WeakReference
import javax.inject.Inject

abstract class BaseViewModel<N> : ViewModel() {

    @Inject
    @IODispatcher
    lateinit var defaultDispatcher: CoroutineDispatcher

    @Inject
    @IODispatcher
    lateinit var ioDispatcher: CoroutineDispatcher

    @Inject
    @MainDispatcher
    lateinit var mainDispatcher: CoroutineDispatcher

    lateinit var navigator: WeakReference<N>

    fun getNavigator(): N? {
        return navigator.get()
    }

    fun setNavigator(navigator: N?) {
        this.navigator = WeakReference(navigator)
    }
}