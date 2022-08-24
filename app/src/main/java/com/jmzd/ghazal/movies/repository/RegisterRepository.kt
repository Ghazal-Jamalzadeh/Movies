package com.jmzd.ghazal.movies.repository

import com.jmzd.ghazal.movies.api.ApiServices
import com.jmzd.ghazal.movies.models.register.BodyRegister
import javax.inject.Inject

/*
* اطلاعات من برای رجیستر از دیتابیس میاد یا از api ؟
* از api
* پس نیاز به apiServices دارم دیگه
* چون قراره فقط توی همین کلاس استفاده کنیم private هم میزنم
* حالا اومدیم داخل پرانتز این ApiServices را پاس دادیم
* اینکه صرفا داخل پرانتز بنویسیمش تو این لول تزریقش نکرده هنوز
* و صرفا به عنواو ورودی دادتش
* زمانی که بخوایم توی فرگمنت ازش استفاده کنیم یا هرجای دیگه
* باید این apiServices را بهش بدیم
* این کار کاملا اصول هیلت را زیر پا میذاره
* باید از هیلت استفاده کنیم. چطور؟
* گقتیم اول برای هر کلاس قبل از primary constructor ش میایم از @Inject استفاده میکنیم
* بعد از اضافه کردن این انوتیشن هست که آیکون وابستگی ظاهر میشه
* و زمانی که روش کلیک کنیم میبردمون روی متد provideRetrofit در ApiModule
* چون توسط این متد هست که ApiService به ما برگردانده میشود
* در همین متدد هم توسط دستور create هست که initialize میشود به کمک رتروفیت
*
* حالا باید بیایم یه متد بنویسیم براش که اطلاعات را بره از api مورد نظر بخونه
* چون متد کال api مون از نوع ساسپند فانکشن هس پس اینم باید از نوع ساسپند فانکشن باشه
* ساسپند فانکشن یا داخل ساسپند فانکشن دیگری مورد استفاده قرار میگیرد یا داخل کروتین
*
* پس تا اینجا تامین کننده اطلاعاتم را معرفی کردم
* توسط چی تامین میکنه؟ توسط apiServices
* بعد این میریم سراغ ساخت کلاس viewModel
* -> RegisterViewModel
*
* */

class RegisterRepository @Inject constructor(private val api: ApiServices) {
    suspend fun registerUser(body: BodyRegister) = api.registerUser(body)
}