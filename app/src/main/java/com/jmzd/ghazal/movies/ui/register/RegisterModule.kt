package com.jmzd.ghazal.movies.ui.register

import com.jmzd.ghazal.movies.models.register.BodyRegister
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
* میگیم ماژوله و محدوده عملکردشو مشخص میکنیم چون یه بار اجرا میشه
* */
@Module
@InstallIn(SingletonComponent::class)
object RegisterModule {

    /*
    * به این ترتیب وابستگی مرببوط به این مدل را هم تامین میکنیم
    * ولی یه اروری به ما نشون میده که این کلاس دیتا مدل ننیاز به یک سری ورودی داره
    * name & email & password
    * اکه نخوایم موقع ساخت دیتا کلاس default value تعریف کنیم برای پارامترها با این ارور رو به رو میشیم
    * و حتما باید اینجا مقدار میدادیم بهشون
    * حتما هم از نوع var تعریف کنید که بشه تغییر داد
    * توضیحات بیشتر در فرگمنت رجیستر آورده شده
    *
    * */
    @Provides
    @Singleton
    fun provideUserBody() = BodyRegister()

}