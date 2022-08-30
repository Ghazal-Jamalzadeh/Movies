package com.jmzd.ghazal.movies.api

import com.jmzd.ghazal.movies.models.detail.ResponseDetail
import com.jmzd.ghazal.movies.models.home.ResponseGenersList
import com.jmzd.ghazal.movies.models.home.ResponseMoviesList
import com.jmzd.ghazal.movies.models.register.BodyRegister
import com.jmzd.ghazal.movies.models.register.ResponseRegister
import retrofit2.Response
import retrofit2.http.*

interface ApiServices {
    /*
    * این دفعه  به جای call از کروتین ها برای کال کردن api استفاده میکنیم
    * برای این منظور فانکشن را از نوع suspend fun تعریف میکنیم
    * Response:
    * از خود کلاس ریسپانس که در رترپفیت۲ است اسستفاده میکنیم برای خروجی گرفتن
    *
    * */
    @POST("register")
    suspend fun registerUser(@Body body: BodyRegister): Response<ResponseRegister>

    @GET("genres/{genre_id}/movies")
    suspend fun moviesTopList(@Path("genre_id") id: Int): Response<ResponseMoviesList>

    @GET("genres")
    suspend fun genresList(): Response<ResponseGenersList>

    @GET("movies")
    suspend fun moviesLastList(): Response<ResponseMoviesList>

    @GET("movies")
    suspend fun searchMovie(@Query("q") name: String): Response<ResponseMoviesList>

    @GET("movies/{movie_id}")
    suspend fun detailMovie(@Path("movie_id") id: Int): Response<ResponseDetail>
}