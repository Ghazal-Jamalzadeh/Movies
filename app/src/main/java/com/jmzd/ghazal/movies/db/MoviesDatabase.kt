package com.jmzd.ghazal.movies.db

import androidx.room.Database
import androidx.room.RoomDatabase

/*
یک کلاس abstract است
* کلاس entity و version و schema را میگیرد
* از RoomDatabase ارث بری میکند
در نهایت هم Dao را میدیم بش
*
* */
@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun movieDao(): MoviesDao
}