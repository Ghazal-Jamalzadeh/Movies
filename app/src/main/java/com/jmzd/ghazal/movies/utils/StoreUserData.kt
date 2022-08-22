package com.jmzd.ghazal.movies.utils


import android.content.Context
import android.provider.SyncStateContract
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/*
* ما اینجا نیاز داریم از هیلت استفاه کنیم
* چون این کلاس قرار هست تو فرگمنت های متنوعی استفاده شود
* پس یک وابستگی به وجود میاد اینجا
* چطوری میومدیم کلاس را به عنوان وابستگی تزریق میکردیم؟
* با قرار دادن @Inject قببل از سازنده پرایمری کلاس
* گقتیم برای استفاده از هیلت نیاز به کلاس اپلیکیشن داریم. پس
* -> MyApp
*
* اگر یادتون باشه دیتا استور برای اینکه بتونه کار کنه نیاز به کانتکس داشت
* طبیعتا داخل این کلاس دسترسی به کانتکس نداریم
* زمانی که میخواستیم از کانتکس استفاده کنیم گفتیم که اصلا اصولی نیست که کانتکس را دستی به اون کلاس بدیم
* با استفاده از @ApplicationContext از خود هیلت کانتکس را میگرفتیم

* */
class StoreUserData @Inject constructor(@ApplicationContext val context: Context) {

    /*
    * حالا نیاز به تعریف آبجکت دیتا استورمون داریم
    * این را در قالب  companion object تعریف میکنیم
    * برای اینکه دسترسی داشته باشیم و از جاهای دیگه هم بتونیم ازش استفاده کنیم
    *
    * اگه یادتون باشه دفعات قبلی که آبجکت دیتا استور را میساختیم از کانتکس استفاده نکردیم برای ساختش
    * به شکل زیر تعریفش کرده بودیم
    * private val dataStore: DataStore<Preferences> by preferencesDataStore(name)
    * چون توی اکتیویتی بودیم و اون صفحه خودش کانتکس رو داشت
    * ولی الان توی یک non activity class هستیم و به صورت اتوماتیک دسترسی به کانتکس نداریم
    * حواستون باشه از خود کلاس Context باید استفاده کنید نه آبجکتش
    * در نهایت هم یک اسم میدادیم به دیتا استور داخل پرانتز
    * برای اینکه اصولی باشه باید از constants استفاده کنیم
    * بعد از اون نیاز داریم که اون مقدار را ذخیره کنیم
    * و گفتیم که دیتا تایپ را اینجا باید مشخص کنیم
    * و داخل پرانتز هم از ما یک کلید میخواست
    * */
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
           Constants.USER_INFO_DATASTORE)

        val userToken = stringPreferencesKey(Constants.USER_TOKEN)
    }

    /*
    * اینجا از آبجکت کاانتکس استفاده میکنیم
    * به ما یک it برمیگرداند که ما اون کلیدمون را بهش میدیم
    * در سمت دیگه مساوی هم که value را بهش پاس مدیم
    * */
    suspend fun saveUserToken(token: String) {
        context.dataStore.edit {
            //it: MutablePreferences
            it[userToken] = token
        }
    }

    /*
    * برای زمانی که میخوایم توکن رو get کنیم و ازش استفاده کنیم
    * با elvis condition هم بهش میگیم که اگه خالی بود توکن یه استرینگ خالی برگردون
    *
    * */
    fun getUserToken() = context.dataStore.data.map {
        //it : Preferences
        it[userToken] ?: ""
    }

}

// -> SplashFragment