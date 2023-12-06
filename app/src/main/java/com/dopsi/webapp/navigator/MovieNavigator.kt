package com.dopsi.webapp.navigator

import com.core.data.model.MovieResponse
import com.core.interfaces.BaseNavigator

interface MovieNavigator :BaseNavigator {
     fun setAdapter(list: ArrayList<MovieResponse.Results>) = Unit
}