package com.dopsi.webapp.fragment

import android.os.Parcelable
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.core.base.BaseFragment
import com.core.customviews.CustomProgressDialog
import com.core.data.model.MovieResponse
import com.core.extensions.TAG
import com.core.extensions.empty
import com.core.interfaces.ItemClickListener
import com.core.utils.AppLogger
import com.core.viewmodel.MovieViewModel
import com.dopsi.webapp.R
import com.dopsi.webapp.adapter.MovieAdapter
import com.dopsi.webapp.databinding.FragmentMovieBinding
import com.dopsi.webapp.navigator.MovieNavigator
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class BLEDeviceFragment  : BaseFragment<FragmentMovieBinding>(FragmentMovieBinding::inflate),
    MovieNavigator, ItemClickListener {

    lateinit var movieAdapter: MovieAdapter
    var progressDialog: CustomProgressDialog? = null

    override fun initUserInterface(view: View?) {
        AppLogger.e(TAG, "Fragmemt init")
        mainActivityViewModel.setNavigator(this)
        mainActivityViewModel.fetchMovies()
        movieAdapter = MovieAdapter(this)
        viewDataBinding.rvWords.apply {
            adapter = movieAdapter
        }
    }

    override fun setProgressVisibility(visibility: Int) {
        if (progressDialog == null)
            progressDialog = CustomProgressDialog()
        lifecycleScope.launch {
            if (visibility == View.VISIBLE) {
                progressDialog?.show(requireActivity(), String.empty)
            } else progressDialog?.dialog?.dismiss()
        }
    }

    override fun setAdapter(list: ArrayList<MovieResponse.Results>) {
        lifecycleScope.launch {
            movieAdapter.setItems(list)
        }
    }

    override fun onItemClick(position: Int, view: View) {
        AppLogger.e(TAG,"item clice")

        val bundle = bundleOf("dataList" to (mainActivityViewModel.itemList as ArrayList<out Parcelable>))
        findNavController().navigate(R.id.move_to_second_fragment, bundle)

        super.onItemClick(position, view)
    }


}