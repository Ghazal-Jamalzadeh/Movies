package com.jmzd.ghazal.movies.ui.register


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import com.google.android.material.snackbar.Snackbar
import com.jmzd.ghazal.movies.R
import com.jmzd.ghazal.movies.databinding.FragmentRegisterBinding
import com.jmzd.ghazal.movies.models.register.BodyRegister
import com.jmzd.ghazal.movies.utils.StoreUserData
import com.jmzd.ghazal.movies.utils.showInvisible
import com.jmzd.ghazal.movies.viewmodel.RegisterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
/*
* چون میخوایم از هیلت استفاده کنیم باید این فرگمنت را به گراف هیلت شناسونیم
* این کار را با @AndroidEntryPoint انجام میدیم
* بالای هر فرگمنت و هر اکتیوتی که میخوایم ازش استفاده کنیم
*
*
*
*
* */
@AndroidEntryPoint
class RegisterFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentRegisterBinding

    //dataStore
    @Inject
    lateinit var userData: StoreUserData

    //viewModel
    /*
    * بعد از نوشتن این خط کد دسترسی داریم به ویومدل
    * داخل ویومدل دسترسی دارم به ریپازیتوزی و ریپازیتوی بهم میگه که اطلاعات از کجا میاد
    * خود ریپازیتوری هم به apiServices وصل میشه
    *
    * */
    private val viewModel: RegisterViewModel by viewModels()

    //model
    /*
    * برای تعریف بادی میتونستیم آبجکت بسازیم از کلاسش و به این شکل استفاده کنیم
    *  private lateinit var body : BodyRegister
    * و بعد پایین تر مقداردهی کنیمش
    * ولی در این صورت هیلت میره زیر سوال
    * باید برای بادی هم یک ماژول بنویسیم
    * شاید بگید خب این یه کلاسه مثل بقیه قبل constructor ش یک inject قرار بدیم
    * ولی این کار را نمیکنیم
    * برای دیتا کلاس ها باید چیکار کنیم؟
    * توی قسمت اول یا دوم همین بخش گفتیم قراره یک سری روش یاد بگیریم که علاوه بر ماژول های اصلی
    * برای بعضی از صفحات یک سری ماژول هم تعریف کنید
    * یک ماژول مخصوص register درست میکنیم که فقط و فقط همین یک کلاس را برای من تامین کند
    * برای دیتا کلاس ها بهتر است از این روش استفاده کنید
    * داخل همین پکیج سازید کلاسشو
    * -> RegiserModule
    * به این ترتیب یک ماژول جدا برای این فرگمنت و تامین وابستگی هاش ساختیم و تامین کردیم این مدل رو هم
    * */
    @Inject
    lateinit var body: BodyRegister

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Click
            submitBtn.setOnClickListener { view ->
                val name = nameEdt.text.toString()
                val email = emailEdt.text.toString()
                val password = passwordEdt.text.toString()
                //Validation
                if (name.isNotEmpty() || email.isNotEmpty() || password.isNotEmpty()) {
                    body.name = name
                    body.email = email
                    body.password = password
                } else {
                    Snackbar.make(view, getString(R.string.fillAllFields), Snackbar.LENGTH_SHORT).show()
                }
                //Send data
                viewModel.sendRegisterUser(body)
                //Loading
                /*
                * در صورتی که لایف سایکل این فرگمنت در دسترس باشه
                * بیا این پارامترها که لایودیتا بودن رو observe کن
                *
                * تغییرات visibilty را با استفاده از extension function میخوایم اعمال کنیم
                * برای این کار در utils یک کلاس جدید میسازیم
                * -> extensions
                *
                * زمانی که از لایو دیتا ها استفاده میکنیم مهم نیست اول و اخر نوشتن کد
                * یعنی مهم نیست کد رو واقعا کجا مینویسیم
                * اتوماتیک آپدیت میشن
                *
                *  */
                viewModel.loading.observe(viewLifecycleOwner) { isShown ->
                    //isShown : boolean
                    if (isShown) {
                        submitLoading.showInvisible(true)
                        submitBtn.showInvisible(false)
                    } else {
                        submitLoading.showInvisible(false)
                        submitBtn.showInvisible(true)
                    }
                }
                //Register
                /*
                * در نهایت میگیم اگه لایو دیتای regsiter اینجا پست شد چه اتفاقی بیفتد؟
                * قطعا اگه چیزی پست شه یعنی عملیات من به درستی انجام شده
                * */
                viewModel.registerUser.observe(viewLifecycleOwner) { response ->
                    //response : ResponseRegister
                    /*
                    * اگه داخل کروتین ننویسیم ارور میده. چه اروری؟
                    * میگه شما از ساسپند فانکشن استفاده کردی ولی من اینجا نمیبینم اینو داخل ساسپند فانکشنی قرار داده باشی
                    * یا اینو بذار داخل یه ساسپند فانکشن دیگه یا کروتین
                    *
                    * یه ارور دیگه هم ممکنه ببینید
                    * زیر response.name خط بکشه
                    * چرا؟
                    * چون nullable تعریفش کردیم داخل مدل
                    * راه حل :
                    * ۱- بیایم toString را اضافه کنیم
                    * ۲- کلا ؟ رو برداریم از جلوش که nullable نباشه
                    *
                    * */

                    /*
                    * زمانی که این خط کد اجرا میشه و توکن یوزر در دیتا استور خیره میشه چه اتفاقی می افته؟
                    * چون داریم توسط دیتا استور ذخیره میکنیم توکن را
                    * و این saveUserToken یک ساسپند فانکشن است
                    * پس یک کروتین تعریف می کنیم که این ساسپند فانکشن رو توش اجرا کنیم
                    * حالا چه اتفاقی می افته؟
                    * میخوایم ببینیم چطور همه چیز به صورت اتوماتیک اتفاق می افته
                    * ما میایم اطلاعات رو زمانی که میگیریم و دیگه زمانمون راحت شد که اطلاعات رو گرفتیم
                    * توسط این خط کد توی دیتا استور ذخیره میکنیم
                    *  userData.saveUserToken(response.name.toString())
                    * حالا چه اتفاقی می افته؟
                    * زمانی که این ذخیره میشه کجا داره Collect میشه و ازش استفاده میشه؟
                    * داخل یک کروتین در splashFragment
                    * که چک میکنه اگه توکن داریم ما رو ببر به فرگمنت هوم و در غیر این صورت رجیستر
                    * حالا اتوماتیک این تیکه خط کد داخل اسپلش دوباره اجرا میشه
                    * { احتمالا چون از map استفاده میکردیم؟ }
                    * میاد دوباره چک میکنه که توکن خالیه یا نه
                    * اگر خالیه که هیچی اینو دوباره جرا کن
                    * اگر پره ببر به صفحه اصلی هوم
                    * و چون پره اتوماتیک ما رو هدایتت میکنه به صفحه اصلی
                    * بدون نیاز به اینکه ما بگیم حالا که ذخیره کردی منو ببر به فلان صفحه
                    * که به احتمال ۹۰ تا حالا هر کدی میزدید همین کارو میکردید
                    *
                    *
                    *
                    * */
                    lifecycle.coroutineScope.launchWhenCreated {
                        userData.saveUserToken(response.name.toString())
                    }
                }
            }
        }
    }
}