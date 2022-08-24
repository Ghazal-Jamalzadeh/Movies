package com.jmzd.ghazal.movies.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jmzd.ghazal.movies.api.ApiServices
import com.jmzd.ghazal.movies.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
/*
* اول با استفاده از @Module بهش میگفتیم که این ماژوله
* بعد با استفاده از @InstallIn محدوده کاریش (اسکوپ) رو مشخص میکردیم
* */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /*
    * تحلیل وابستگی ها:
    * من نیاز دارم که از رتروفیت استفاده کنم
    * رتروفیت خودش به ۳ تا چیز نیاز داره:
    * ۱- به baseUrl برای وصل شدن
    * ۲- به gson نیاز داره برای تبدیل کردن json ها
    * ۳- به client برای نوشتن تنظیمات
    *
    * حالا خود کلاینت به چه چیزهای نیاز داشت:
    * ۱- زمان را بهش میدادیم برای تایم اوت شدن
    * ۲- میومدیم interceptor ست میکردیم
    *  که من این api که دارم میزنم اصلا نتیجه ش درسته درست نیست چطوریه
    * اگه ارور میده چه اروری میده؟ از سمت منه یا از سمت سرور
    *
    *
    *
    * */
    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun provideConnectionTine() = Constants.CONNECTION_TIME

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideInterceptor() = HttpLoggingInterceptor().apply {
        //this: HttpLoggingInterceptor
        /*
        * اون که مال هدر بود رو اینجا نمینویسیم چون نیاز نداریم اینجا
        * */
        level = HttpLoggingInterceptor.Level.BODY
    }

    /*
    * کلاینت برای کار کردن نیاز به زمان تایم اوت و interceptor داره
    * این ها رو که قبلا به صورت وابستگی تعریف کردیم در سازنده بهش پاس میدیم
    * */
    @Provides
    @Singleton
    fun provideClient(time: Long, interceptor: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .writeTimeout(time, TimeUnit.SECONDS)
        .readTimeout(time, TimeUnit.SECONDS)
        .connectTimeout(time, TimeUnit.SECONDS)
        .build()

    /*
    * در نهایت هم میمونه تعریف خود رتروفیت که وابسستگی های مورد نیازش رو در سازنده بهش میدیم
    * */
    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String, gson: Gson, client: OkHttpClient): ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)
}