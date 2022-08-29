package com.jmzd.ghazal.movies.repository

import com.jmzd.ghazal.movies.db.MovieEntity
import com.jmzd.ghazal.movies.db.MoviesDao
import javax.inject.Inject

/*
* هر سری اینجا apiServices میدادیم
* ولی این بار میخوایم از دیتابیس بخونیم
* پس Dao را بهش میدیم
* در صفحه details هم dao خواهیم داشت هم apiServices
* از هر نظر Dao معادل apiServices است
* در این صفحه فقط با گرفتن لیست فیلم ها سرکار داریم
*
*
* */
class FavoriteRepository @Inject constructor(private val dao: MoviesDao) {

    fun allFavoriteList() = dao.getAllMovies()
}