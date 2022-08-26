package com.jmzd.ghazal.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.movies.models.home.ResponseMoviesList
import com.jmzd.ghazal.movies.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* به انوتیشن @HiltViewModel نیاز دارد
* از viewModel ارث بری میکند
* به repository خودش وابستگی دارد
*
* به یک لودینگ هم نیاز داریم که زمانی که هر ۳ تا api را کال کردیم و اوکی شد مینویسیمش
* چون برای هر api یک لودینگ جدا که نمینویسیم
*
*
*
* */
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    val topMoviesList = MutableLiveData<ResponseMoviesList>()
//    val genresList = MutableLiveData<ResponseGenresList>()
//    val lastMoviesList = MutableLiveData<ResponseMoviesList>()
//    val loading = MutableLiveData<Boolean>()

    fun loadTopMoviesList(id: Int) = viewModelScope.launch {
        val response = repository.topMoviesList(id)
        if (response.isSuccessful) {
            topMoviesList.postValue(response.body())
        }
    }

//    fun loadGenresList() = viewModelScope.launch {
//        val response = repository.genresList()
//        if (response.isSuccessful) {
//            genresList.postValue(response.body())
//        }
//    }
//
//    fun loadLastMoviesList() = viewModelScope.launch {
//        loading.postValue(true)
//        val response = repository.lastMoviesList()
//        if (response.isSuccessful) {
//            lastMoviesList.postValue(response.body())
//        }
//        loading.postValue(false)
//    }
}