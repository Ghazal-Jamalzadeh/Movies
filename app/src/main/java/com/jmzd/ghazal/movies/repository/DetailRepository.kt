package com.jmzd.ghazal.movies.repository

import com.jmzd.ghazal.movies.api.ApiServices
import com.jmzd.ghazal.movies.db.MovieEntity
import com.jmzd.ghazal.movies.db.MoviesDao
import javax.inject.Inject


class DetailRepository @Inject constructor(private val api: ApiServices, private val dao: MoviesDao) {
    //Api
    suspend fun detailMovie(id: Int) = api.detailMovie(id)

    //Database
    suspend fun insertMovie(entity: MovieEntity) = dao.insertMovie(entity)
    suspend fun deleteMovie(entity: MovieEntity) = dao.deleteMovie(entity)
    suspend fun existsMovie(id: Int) = dao.existsMovie(id)
}