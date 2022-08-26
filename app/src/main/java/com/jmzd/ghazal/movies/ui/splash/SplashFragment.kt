package com.jmzd.ghazal.movies.ui.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jmzd.ghazal.movies.R

/*
* سناریو :
* اول وارد صفحه اسپلش میشیم
* در اینجا اپلیکیشن تصمیم میگیره که وارد صفحه رجیستر بشه یا وارد صفحه هوم بشه
* چطور تصمیم میگیره؟
* میبینه آیا اطلاعات کاربر ذخیره شده یا نه
*
* برای شروع فلش های نو گراف را میکشیم
*
* بعد میریم سراغ MainActivity تا کدهای مربوط به نویگشین و باتم نویگشین را بزنیم
* و از طرفی باید بگیم که این باتم نویگیشن تو کدام فرگمنت ها باید نشون داده بشه و تو کدوم ها نه
* -> Main Activity
*
* */


import androidx.lifecycle.coroutineScope
import androidx.navigation.fragment.findNavController
import com.jmzd.ghazal.movies.databinding.FragmentSplashBinding
import com.jmzd.ghazal.movies.utils.StoreUserData
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import javax.inject.Inject
/*
* برای اینکه بفهمیم کاربر لاگین کرده یا نه از دیتا استور استفاده میکنیم
* ولی برای دیتا استور از یک کلاس دیگه ای استفاده میکنیم
* چرا؟
* چون تو مثالی که قبلا حل کرده بودیم اطلاعات را در یک صفحه مینوشتیم و در همان صفحه هم نمایش میدادیم
* ولی در این مثال اطلاعات را در صفحه ثبت نام ذخیره میکنم
* در صفحه اسپلش میخوام چک کنم آیا کارب ثبت نام کرده یا نه
* یعنی از یک چیز دو جا میخوام استفاده کنم
* پس اصولیش اینجوریه که یک کلاس جدا برای این کار داشته باشیم که از همه جای اپ در دسترس است(شی گرایی)
* -> StoreUserData
*
* گفتیم در هر صفحه ای که بخوایم از هیلت استفاده کنیم برای اینکه هیلت بفهمه از انویشن AndroidEntryPoint استفاده میکردیم
*
* */
@AndroidEntryPoint
class SplashFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentSplashBinding

    //Data store
    /*
    * از کلاس دیتا استور به صورت تزریق وابستگی استفاده میکنیم
    * تا دستی خودمون مجبور نباشیم تعریفش کنیم
    * یادتون باشه حتما lateinit باشه و val نباشه
    * گراف برقرار میهش و آیکون ها ظاهر میشن
    * */
    @Inject
    lateinit var storeUserData: StoreUserData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Set delay
        /*
        * در همه  صفحات اسپلش ما یک دیلی دو ثانیه ای این حدودا داریم
        * برای ایجاد دیلی از کروتین استفاده میکنیم
        * دیگه نمیام خودم مستقیم از کروتین استفاده کنم
        * چون اگر مستقیم بخوام استفاده کنم
        * قطعا باید توی لایف سایکل چکش کنم
        * که آیا بسته شده بسته نشده خودم بیام اساپش کنم
        * یاد گرفتیم بیایم از لایف سایکل ها استفاده کنیم
        * خیالمون راحت باشه که خودش آگاهه به لایف سایکل ما
        * و اگه بسته شد خودش کروتین ما رو میبنده
        * میگیم وقتی لانچ شد اجرا شو
        * دو ثانیه صبر کن
        * بعد کدهای مربوط به دیتا استور را اجرا کن
        * */

        /*
        * یک حالتی وجود دارد که در اجرای ایم کد به یک اروری میخوریم
        * زمانی که در register هستیم ثبت نام موفق انجام میدیم
        * طبق توضیحاتی که در همان registerFragment نوشتم بعد موفق بودن عملیات ثبت نام و ذخیره توکن
        * باید این خط کد مجدد به صورت اتوماتیک اجرا شه و ما رو ببره به home
        * ولی در عمل برنامه کرش میکنه. چرا؟
        * میگه تو الان توی مقصدی هستی به اسم registerFragment
        * action action_splashFragment_to_homeFragment can not found from the current destination
        * تو الان توی registerFragment هستی ولی داری توسط splashFragment به یه جای دیگه میری
        * برای هندل کردن این موقعیت یک تکنیکی توی navigation وجود داره
        * اونم اینکه میگه به جای اینکه بیای یه روت خاصی رو وصل کنی به یک صفحه خاص (همون اکشن ها)
        * بیا general یا global تعریفش کن
        * من میخوام بگم بیا این روت رو اجرا کن ولی محدودش نکن به اسپلش
        * برای این کار هذ اکشنی را خواستید انتخاب میکنید و از داخل تگ فرگمنت مربوط به خودش میارید بیرون
        * بعد داخل گراف navigation ظاهر این فلش عوض میشه
        * هنوز وجود داره ولی سرش به هیچ جایی وصل نیست
        * ما دیگه خیلی راحت و از هر جای اپ میتونیم اینو بفرستیم به صفحه هوم
        * پس اسم اکشن رو هم تغییر میدیم به یک اسم مرتبط تر
        * action_splashFragment_to_homeFragment -> action_to_homeFragment
        *
        * */
        lifecycle.coroutineScope.launchWhenCreated {
            delay(2000)
            //Check user token
            storeUserData.getUserToken().collect {
                // it : String
                if (it.isEmpty()) {
                    findNavController().navigate(R.id.action_splashFragment_to_registerFragment)
                } else {
                    findNavController().navigate(R.id.action_to_homeFragment)
                }
            }
        }
    }
}