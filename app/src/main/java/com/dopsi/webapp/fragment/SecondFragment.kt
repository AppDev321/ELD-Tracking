package com.dopsi.webapp.fragment

import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.core.base.BaseFragment
import com.core.customviews.CustomProgressDialog
import com.core.data.model.MovieResponse
import com.core.extensions.TAG
import com.core.extensions.empty
import com.core.interfaces.ItemClickListener
import com.core.utils.AppLogger
import com.core.utils.getParcelableArrayListCompat
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.adapter.MovieAdapter
import com.dopsi.webapp.databinding.FragmentMovieBinding
import com.dopsi.webapp.navigator.MovieNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SecondFragment : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate),
    MovieNavigator, ItemClickListener {


    lateinit var movieAdapter: MovieAdapter

    override fun initUserInterface(view: View?) {
        AppLogger.e(TAG, "Fragmemt22 init")
        movieAdapter = MovieAdapter(this)
        viewDataBinding.rvWords.apply {
            adapter = movieAdapter
            arguments?.let {
                val data = it.getParcelableArrayListCompat<MovieResponse.Results>("dataList")!!
                movieAdapter.setItems(data)
                AppLogger.e(TAG,data.toString())
            }

        }


    }


}