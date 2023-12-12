package com.dopsi.webapp.activity

import androidx.activity.viewModels
import com.core.base.BaseActivity
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val mainActivityViewModel: MovieViewModel by viewModels()

    override fun initUserInterface() {

    }
}