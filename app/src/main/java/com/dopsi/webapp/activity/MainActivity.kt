package com.dopsi.webapp.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavInflater
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.core.base.BaseActivity
import com.core.extensions.TAG
import com.core.interfaces.BaseNavigator
import com.core.utils.AppLogger
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.R
import com.dopsi.webapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity :BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val mainActivityViewModel: MovieViewModel by viewModels()

    override fun initUserInterface() {

    }
}