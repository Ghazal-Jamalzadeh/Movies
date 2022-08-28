package com.jmzd.ghazal.movies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmzd.ghazal.movies.databinding.ItemHomeMoviesLastBinding
import com.jmzd.ghazal.movies.models.home.ResponseMoviesList.*
import javax.inject.Inject

/*
* این آداپتر با آداپترهای قبلی یک فرقی داره
* فرقش هم اینه که میخوایم تو چند تا صفحه ازش استفاده کنیم
* چه اتفاقی می افته؟
* لیست من از صفحات مختلف مدام آپدیت میشه
* مثلا هم در فرگمنت سرچ قراره استفاده شه هم در هوم هم در فرکمنت علاقه مندی ها
* و چه اتفاقی می افته؟
* لیست من مدام آپدیت میشه
* مثلا ممکنه کاربر یه بار فیلم گاد فادر را سرچ کنه بعد ارباب حلقه ها
* در این صورت گاد فادر باید پاک شه و ارباب حلقه ها نشون داده شه
*
* زمانی که میخواید اطلاعاتتون مدام اپدیت شه از این روش اسستفاده کنید برای آداپتر
* روش قبلی diffUtils بیشتر زمانی کاربرد دارد که اطلاعات شما تند تند آپدیت نشود
* برای آپدیت لحظه ای و آنی این روش بهتر است
*
*
*
* */

class LastMoviesAdapter @Inject constructor() : RecyclerView.Adapter<LastMoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeMoviesLastBinding
    /*
    * در این حالت اول میایم یه لیستی از مدل مد نظرمون میسازیم
    * و یک لیست خالی بهش میدیم
    * چرا خالی؟ چون میخوایم جلوتر پرش کنیم
    * و emptyList هم جزو یکی از کالکشن های کاتلین محسوب میشود
    * یک لیست خالی تعریف می کند
    *
    *
    * */
    private var moviesList = emptyList<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LastMoviesAdapter.ViewHolder {
        binding = ItemHomeMoviesLastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: LastMoviesAdapter.ViewHolder, position: Int) {
        holder.bindItems(moviesList[position])
        holder.setIsRecyclable(false)
    }

    /*
    * برای سایز لیست از همون لیستی که بالا انتخاب کردیم استفاده میکنیم
    * دیگه از دیفر استفاده نمیکنیم چون دیفری نداریم
    * */
    override fun getItemCount() = moviesList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bindItems(item: Data) {
            binding.apply {
                movieNameTxt.text = item.title
                movieRateTxt.text = item.imdbRating
                movieCountryTxt.text = item.country
                movieYearTxt.text = item.year
                moviePosterImg.load(item.poster) {
                    crossfade(true)
                    crossfade(800)
                }
                //Click
                root.setOnClickListener {
                    onItemClickListener?.let {
                        it(item)
                    }
                }
            }
        }
    }

    private var onItemClickListener: ((Data) -> Unit)? = null

    fun setOnItemClickListener(listener: (Data) -> Unit) {
        onItemClickListener = listener
    }

    /*
    * برای استفاده از diffUtils این فانکشن را مینویسیم
    * دیگه اینجا فرقی نداره که کلاس diffUtils را خارج ایننجا تعریف کردیم یا داخلش
    * نوع نوشتن و دسترسی داشتن هیچ فرقی نمیکنه
    *
    * این متد داخل فرگمنت صدا زده میشه و یک لیست پاس میده
    * قبلا تو فرگمنت differ را صدا میزدیم و مقدار میدادیم بهش
    * خودش دیتا را میگیره و آنالیز میکنه توسط diffUtils
    *
    *
    *
    * */
    fun setData(data: List<Data>) {

        /*
        * این کلاس را میخوایم استفاده کنیم و ازش یک آبجکت بسازیم
        * ورودی قدیمی و ورودی جدید را هم بهش پاس میدیم
        * لیست قدیمی میشه اون لیستی که بالا تعریف کردیم و خالی بهش دادیم
        * لیست جدید دیتایی رو بهش میدیم که قراره اینجا پر شه
        * */
        val moviesDiffUtil = MoviesDiffUtils(moviesList, data)

        /*
        * بعد میایم diffUtils را تعریف میکنیم
        * شبیه همون کاری که قبلا با differ میکردیم
        * و کال بک را بهش پاس میدادیم
        * روشش اینجا اینجوریه اونجا اونجوری بود
        * محاسبات تفاوت ها رو انجام میده
        * چی بهش پاس میدیم؟ همون کلاس diffUtils که ساختیم را
        * ورودی کلاس لیست جدید و لیست قدیمی بود
        * خروجی کلاس هم یک DiffUtil CallBack
        * */
        val diffUtils = DiffUtil.calculateDiff(moviesDiffUtil)

        /*
        * بعد لیست جدید را میریزیم تو لیست قدیمی برای آپدیت بعدی
        * */
        moviesList = data

        /*
        * توی روش قبلی در نهایت در متد AsyncListDiffer هم آداپتر را بهش پاس میدادیم هم کال بک را
        * الان اینجا کال بک را بالا بهش دادیم
        * آداپتر را اینجا بهش میدیم
        * میگه این چیزایی که دارم آپدیت میکنم رو توی کدوم آداپتر اعمال کنم؟
        * میگیم this یعنی همین آداپتر
        *
        *
        * */
        diffUtils.dispatchUpdatesTo(this)
    }

    /*
    * یک کلاس برای دیف یوتیلز تعریف میکنیم
    * این کلاس را میتونیم خارج از اداپتر هم تعریف کنیم
    * اگر احساس میکنید از این کلاس دیف یوتیلز ممکنه بخواید تو اداپترهای دیگه ای هم استفاده کنید بیرون تعریفش کنید
    *
    * باید بهش دو تا ورودی بدیم
    * هر دو ورودی هم لیستی از مدل ما هستن
    * اسم یکیش میذاریم oldItem اسسم یکی دیگه را میذاریم newItem
    *
    * بعد میاد چند تا متد را override میکنه
    *
    *
    * */
    class MoviesDiffUtils(private val oldItem: List<Data>, private val newItem: List<Data>) : DiffUtil.Callback() {

        /*
        * میگه سایز لیست قدیمیت رو به من بده
        * */
        override fun getOldListSize(): Int {
            return oldItem.size
        }

        /*
        * سایز لیست جدیدت را بده
        * */
        override fun getNewListSize(): Int {
            return newItem.size
        }

        /*
         * اینجا تقریبا مثل اون حالت قدیمیه است که ممیخواد چک کنه این چیزایی که داره میاد با هم برابرن یا نه
        * یک boolean برمیگردونه
        *
        * فرق بین دو تا مساوی و سه تا مساوی ؟
        * دو تا مساوی میاد فقط value ها را مقایسه میکنه
        * سه تا مساوی میاد value & dateType را مقایسه میکند
        * */
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        /*
        * این یکی فقط زمانی صدا زده می شود که بالایی true باشد
        * */
        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldItem[oldItemPosition] === newItem[newItemPosition]
        }

        /*
        * کل diffUtils ش همین بود
        * */
    }
}

/*
* به طور خلاصه :
* میایم یه کال بک از diffUtils را استفاده میکنیم که دو تا متد دیگه هم override میکنه که سایزها را از ما میگیره
* برای استفاده ازش میایم یه شی از کلاس میسازیم
* لیست قدیمی و جدید را  به عنوان وروردی بهش میدیم
* بعد یک کال بک تعریف میکنیم
* ورودی این کال بک میشه همون شی کلاس ما
* در نهایت میگه اینا رو روی کدام آداپتر اعمال کنم که بهش میگیم
* */