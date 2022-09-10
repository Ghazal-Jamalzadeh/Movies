package com.jmzd.ghazal.movies.db

import androidx.room.*
import com.jmzd.ghazal.movies.utils.Constants


@Dao
interface MoviesDao {

    /*
    * حتما از ساسپند فانکشن ها استفاده کنید چون میخوایم از کروتین ها برای عملیات دیتابیس استفاده کنیم
    * همون طور که تمام api هامون را داخل ساسپند فانکشن نوشتیم
    * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(entity: MovieEntity)

    @Delete
    suspend fun deleteMovie(entity: MovieEntity)

    /*
    * ولی برای سایر عملیات ها مثل دریافت لیست از  ساسپند فانکشن ها استفاده نمیکنیم
    * در واقع نیاز نیست
    * ولی اگر بنویسید هم اشکالی ندارد
    *
    * شاید بپرسید چرا همینجا نریختیش تو liveData ؟
    * این بخش را میخوایم داخل viewModel هندل کنیم
    * اینجا لیست معمولی قرار میدیم همون طور که در رتروفیت هم این کارو کردیم
    * */
    @Query("SELECT * FROM ${Constants.MOVIES_TABLE}")
    fun getAllMovies(): MutableList<MovieEntity>

    /*
    * میخوایم چک کنیم فیلم مورد نظر ما داخل دیتابیس هست یا  نه؟
    * با آیدی چک میکنیم
    *
    * درمورد این کوءری:
    * در SQL یک کدی هست به اسم EXISTS که برای چک کردن موجودی به کار میرود
    * دستور select که سلکت میکنه
    * دستور exist چک میکنه موجود هست یا نه
    * ولی چه چیزی موجود هست یا نه؟
    * داخل پرانتز خودش یک کوءری میگیره
    *
    * این رو هم حتما ساسپند فانکشن قرار بدید
    * خروجی هم یک boolean هست
    *
    * */
    @Query("SELECT EXISTS (SELECT 1 FROM ${Constants.MOVIES_TABLE} WHERE id = :movieId)")
    suspend fun existsMovie(movieId: Int): Boolean
}