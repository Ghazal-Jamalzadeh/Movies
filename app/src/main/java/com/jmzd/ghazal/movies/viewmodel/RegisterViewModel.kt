package com.jmzd.ghazal.movies.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmzd.ghazal.movies.models.register.BodyRegister
import com.jmzd.ghazal.movies.models.register.ResponseRegister
import com.jmzd.ghazal.movies.repository.RegisterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/*
* ما از ویومدل ها استفاده میکنیم برای اینکه rotate میکنیم عوض نمیشه - صفحات عوض میشن سرجاش میمونه
* همچنین دلیل خیلی مهم دیگه اینکه
* اگه شما میخواید طبق اصول معماری های برنامه نویسی اندروید و مخصوصا معماری clean برید جلو
* شما باید لاجیک اپلیکیشنتون توی ویومدل صورت بگیره
* لاجیک یعنی عملیاتی که قراره انجام بدین
* عملیات من چیه توی این صفحه؟
* ثبت نام کردن کاربر
* یعنی چی؟
* یعنی کاربر روی دکمه ثبت نام که میزنه اطلاعات را میفرسته
* ما میریم توی لودینگ
* اطلاعات فرستاده میشه میره سمت سرور
* اطلاعات که اومد لودینگ دیگه در نظر گرفته نمیشه
* و اگر ثبت نام اوکی شده باشه من میخوام بفرستمش به یک صفحه دیگه
* لاجیک اصلی این صفحه ثبت نام کردن و نشون دادن لودینگه که میریم ببینیم چطور باید داخل ویومدل پیاده  سازیش کرد
*
* @HiltViewModel :
* برای اینکه ویومدلمون رو به گراف هیلت بتونیم متصل کنیم میایم از این انوتیشن استفاده میکنیم
*
* طبیعتا از ViewModel هم که ارث بری میکند
*
* نیاز به تامین کننده اطلاعات یا همون ریپازیتوریمون هم داریم در این ویومدل
* تامین کننده اطلاعات ما ریدازیتوری بود که به عنوان پارامتر بهش پاس میدیم
* چون داخل constructor ش قرار دارم برای تامین کردنش بباید از هیلت استفاده کنم
* پس انوتیشن Inject را هم استفاده میکنیم

* */


/*
* اینجا نیاز به دو تا چیز خیلی مهم دارم
* ۱- اطلاعات رو درواقع چیزایی که سرور به من میده را دریافت کنم
* سرور چه زمانی این اطلاعات رو به من میده؟
* زمانی که من این بادی رو براش بفرستم
* حالا چرا میخوام اطلاعات رو دریافت کنم؟
* به چند دلیل
* ۱- میخوام ببینم اصلا کاربر من با موفقیت ثبت نام کرده یا نه
* یا مثلا در مثال های جلوتر مثل صفحه لیست میخوام ببینم لیست فیلم ها رو به من میده با نه
* به طور کلی اطلاعاتی که ازش میخوام رو بهم میده با نه
* ۲- لودینک رو نشون بده یا نشون نده
* نمیخوام دستی توی فرگمنت لودینگ و این ها رو خودم هندل کنم
* زمان اینجور کد زدن ها گدشته
* لاجیک کد را داخل ویومدل میزنیم و میگیم الان نشن بده یا نشون نده
* معماری یعنی همین
*  ما دیگه کاری نداریم و نمیدونیم اطلاعات داره از کجا میاد
* تامین کننده اططلاعات هم اصلا نمیدونه ما اطلاعات را برای چی میخوایم
* صرفا میدونه که ما این اطلاعات را میخوایم و بهمون میده
* اینکه چور میخوای ازش استفاده کنی این دیگه به خودت ربط داره
* از اون طرف هم ویو میگه که اقا تو چیکار داری من میخوام چه استفاده ای از این کنم
* تو بیا اطلاعات رو به من بده
* من خودم در مورد نحوه نمایشش تصمیم میگیرم
* پس به صورت کلی
* ما اون لاچیک اپلیکیشن یا اون کارکرد اصلی اپلیکیشن رو توی اون ویومدل مینویسیم
*  که هم برامون استور میکنه که از بین نره و کلی مزایا داره
*
*
* */

@HiltViewModel
class RegisterViewModel @Inject constructor(private val repository: RegisterRepository) : ViewModel() {

    /*
    * اینجا نیاز به دو تا متغیر داریم
    * میخوایم از لایو دیتا استفاده کنیم که نیاز نباشه مدام دستی آپدیتش کنیم
    * باید از Mutable:iveData ها استفاده کنیم
    * حالا این MutableLiveData میاد از ما میپرسه چیو بت بدم
    * از من انتظار داری چه نوع اطلاعاتی رو بت بدم؟
    * دیتا تایپ رو اینجا بهش میدیم
    * ResponseRegister = همون مدل ماست
    * لودینگ چون بیشتر دو حالت نداره از boolean استفاده میکنیم
    *
    * */
    val registerUser = MutableLiveData<ResponseRegister>()
    val loading = MutableLiveData<Boolean>()

    /*
    * حالا عملیاتی که قرار هست پیاده سازی کنیم چیه؟
    * اول لودینگ رو نشون بده بعد اطلاعات رو بفرس سمت سرور
    * نتیجه رو که دریافت کردی لودینگ رو از بین ببر
    *
    * پس یک متد مینویسیم و بادی را با عنوان پارامتر پاس میدیم بش
    * گقتیم ساسپند فانکشن داخل یک ساسپند فانشکن دیگه استفاده میشه یا داخل کروتین
    * من داخل ویو مدل میتونم خیلی راحت از اسکوپ های ویومدلی که برای کروتین تعریف شده و در دسترس ویومدل و کروتین هست استفاده کنم
    * با استفاده از viewModelScope
    * در جلسه اول کروتین در بخش اسلاید در بخش اسکوپ ها گفتیم
    * دو تا داریم
    * یکی لایف سایکل که کتابخونه  ش رو اضافه میکنیم
    * یکی دیگه داریم ViewModelScope که جلوتر بهتون میگم
    * این همونه
    * اسکوپ محدوده را برای ما تعیین میکنه
    * و ما بهش میگیم در محدوده ویومدل از این استفاده کنه
    * در نهایت لانچ میکنیم که بتونیم ازش استفاده کنیم
    *
    * اول از همه میگیم زمانی که فراخوانی شد با post value اطلاعتی را بفرس
    * گفتیم postValue در پس زمینه بهتر عمل میکنه
    * از setValue بیشتر زمانی که در ترد main هستیم استفاده میکنیم
    *
    *
    * */
    fun sendRegisterUser(body: BodyRegister) = viewModelScope.launch {
        //this : CoroutineScope

        /*
        * اول میگیم true را بفرس
        * تا لودینگ شروع کنه
        * */
        loading.postValue(true)

        /*
        * گفتیم از response استفاده میکنیم که ببینیم اون عملیات سمت سرور ما اوکی بوده یا نه
        * بعد api را کال میکنیم و دیتا را داخل یک متغیر میریزیم. در واقع ریسپانس را داخلش میریزیم
        *
        * */
        val response = repository.registerUser(body)

        /*
        * بعد میگیم اگه این response ما successful بود
        * بادی ریسپانس را در لایو دیتا بفرس به فرگمنت
        * یعنی همون مدل را
        * */
        if (response.isSuccessful) {
            registerUser.postValue(response.body())
        }

        /*
        * بعد پایان عملیات لودینگ را از بین ببر
        *
        * */
        loading.postValue(false)

    }

}

/*
* به طور خلاصه:
* پرفرومنس ویومدل فوق العاده بالاست
* ۱- نیاز داریم که به تامین کننده اطلاعات دسترسی داشته باشیم
* پس به RegisterRepository نیاز دازم
* ۲- میام دو تا متغیر تعریف میکنم که در واقع این ها رو انتشار بدم
* اگر یادتون باشه گفتیم لایو دیتا میاد اطلاعات رو برای  observer هایی که الاعاتشون از بین نرفته
* درواقع destroy و pause نشدن هی اطلاعات رو میفرسته
* از نوع Mutable هستن تا من بتونم اطلاعات رو ادیت کنم
* ۳- در نهایت بعد از دریافت response ها لایو دیتا ها رو شارژ میکنیم
* حتما حتما یادتون باشه اطلاعات رو هنگام تعریف apiServices داخل response برگردونین
* تا بشه success یودن و نبودن رو چک کرد
* ۴- وقتی همه چی اوکی بود بادی را میفرستیم
* بادی هم از جنس مدلی خواهد بود که بهش داده  بودیم
* خیلی راحت برامون میفرسته
*
*
*
*
* */