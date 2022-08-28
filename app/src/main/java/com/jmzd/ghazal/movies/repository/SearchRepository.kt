package com.jmzd.ghazal.movies.repository

import com.jmzd.ghazal.movies.api.ApiServices
import javax.inject.Inject

class SearchRepository @Inject constructor(private val api: ApiServices){
    suspend fun searchMovie(name:String) = api.searchMovie(name)
}