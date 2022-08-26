package com.jmzd.ghazal.movies.utils

object Constants {
    const val USER_INFO_DATASTORE = "user_info_datastore"
    const val USER_TOKEN = "user_token"

    /*
    * دقت کنید url ی که استفاده میکنید حتما باید https باشد نه http
    * چون از اندروید ۵ به بعد عملا تو اجرا کردن این کد با موبایل به ارور میخورید
    * و باید در مانیفست و اینا هندل کنید کلی
    *
    * */
    const val BASE_URL = "https://moviesapi.ir/api/v1/"
    const val CONNECTION_TIME = 60L

    const val MOVIES_TABLE = "movies_table"
    const val MOVIES_DATABASE = "movies_database"
}