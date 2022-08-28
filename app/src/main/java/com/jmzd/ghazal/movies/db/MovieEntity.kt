package com.jmzd.ghazal.movies.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jmzd.ghazal.movies.utils.Constants

/*
* برای شروع مدل را میساختیم با اسم entity
* داخل پرانتز اسم table را میگرفت
* */
@Entity(tableName = Constants.MOVIES_TABLE)
data class MovieEntity(
    @PrimaryKey
    /*
    * دیگه auto generate نمیذاریم. چرا؟
    * چون آیدی ها از سمت سرور میان
    * دیگه نیاز نیست خودمون تولید کنیم
    * و از طرفی نباید این id ها عوض شوند
    * */
    var id: Int = 0,
    var poster: String = "",
    var title: String = "",
    var rate: String = "",
    var country: String = "",
    var year: String = ""
)
