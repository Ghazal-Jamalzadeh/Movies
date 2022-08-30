package com.jmzd.ghazal.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.movies.db.MovieEntity
import com.jmzd.ghazal.movies.models.detail.ResponseDetail
import com.jmzd.ghazal.movies.repository.DetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: DetailRepository) : ViewModel() {

    //Api
    val detailMovie = MutableLiveData<ResponseDetail>()
    val loading = MutableLiveData<Boolean>()

    fun loadDetailMovie(id: Int) = viewModelScope.launch {
        loading.postValue(true)
        val response = repository.detailMovie(id)
        if (response.isSuccessful) {
            detailMovie.postValue(response.body())
        }
        loading.postValue(false)
    }

    //Database
    val isFavorite = MutableLiveData<Boolean>()
    /*
    * قبلا api ها ار که میخواستیم توی کروتین کال کنیم از دستور لانچ استفاده میکردیم
    * اگر خاطرتون باشه توی کروتین گفتیم که لانچ هیچ مقداری را برای ما بر نمیگردونه
    * درواقع فقط میتونیم ازش استفاده کنیم
    * پس چیزی را برنمیگردوند. ما فقط میگفتیم true شد این کار را بکن false شد یه کار دیگه
    * ولی ما اینجا میخوایم یه true false برای ما برگرده که بر اساس اون کارهایی انجام بدیم
    * یعنی باید از ASync استفاده کنیم
    * قبلا گفتیم ASync یه متدی داره به اسم await که اون چیز بازگشتی را به ما میده
    * من اینجا میتونم از ASync هم استفاده کنم ولی چون مستقیم اون مورد بازگشتی را نمیخوام استفاده کنم
    * کجا میخوام استفاده کنم؟
    * داخل فرگمنت
    * پس نیاز به await ندارم
    * گفتیم زمانی که میخوایم از ASync استفاده کنیم ولی همون لحظه نیاز به await نداریم
    * میتونیم از withContext استفاده کنیم
    * گفتیم withContext میاد برای ما همون ASync را پیاده سازی می کند
    * با این تفاوت که همون لحظه من نیاز ندارم از await آن استفاده کنم
    *
    * یه کاربرد دیگه هم داشت
    * این بود که همون لحظه میومد برای من تردم را عوض میکرد
    *
    * این repository.existsMovie(id) برای ما یک boolean برمیگرداند

    * اینجا میشد از ASync هم استفاده کرد ولی بنویسید هم خودش withContext را پیشنهاد میده
    *
    * */
    suspend fun existsMovie(id: Int) = withContext(viewModelScope.coroutineContext) { repository.existsMovie(id) }

    /*
    * این متد هم یک آیدی از ما میگیرد چون باید با آیدی چک کند که آیا توی دیتابیس هست یا نه
    * یک entity هم از ما میخواهد برای عملیات insert یا delete
    *
    * الان اینجا دوباره نمیخوام چیزی را برای من برگردونه و صرفا یه سری عملیات هست که باید انجام شه
    * پس از لانچ استفاده میکنیم
    * */
    fun favoriteMovie(id: Int, entity: MovieEntity) = viewModelScope.launch {
        val exists = repository.existsMovie(id)
        if (exists) {
            isFavorite.postValue(false)
            repository.deleteMovie(entity)
        } else {
            isFavorite.postValue(true)
            repository.insertMovie(entity)
        }
    }
}