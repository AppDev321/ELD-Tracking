package com.dopsi.webapp.activity

import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.core.base.BaseActivity
import com.core.extensions.TAG
import com.core.interfaces.BaseNavigator
import com.core.utils.AppLogger
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val mainActivityViewModel: MovieViewModel by viewModels()
    override fun initUserInterface() {

    }
}