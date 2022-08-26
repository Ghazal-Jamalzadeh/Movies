package com.jmzd.ghazal.movies.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.jmzd.ghazal.movies.databinding.FragmentHomeBinding
import com.jmzd.ghazal.movies.ui.home.adapters.GenresAdapter
import com.jmzd.ghazal.movies.ui.home.adapters.TopMoviesAdapter
import com.jmzd.ghazal.movies.utils.initRecycler
import com.jmzd.ghazal.movies.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
* سناریو :
* قراره ۳ تا api کال شه توی این صفحه
* 1- top movies یا همون ترندها
* 2- generes
* 3- last movies
* قراره این ها را با حداقل خط کد کال کنیم
* همچنین اینو یاد میگیریم که در هر اسکرول ریسایکلر ویو فقط یک آیتم نشون بدیم
*
* مراحل ساخت فرگمنت home
* 1- اضافه کردن فرگمنت به گراف هیلت با استفاده از @AndroidEntryPoint
* 2- تعریف و تزریق آداپترها با استفاده از inject
* 3- تعریف viewModel
*
*
* */
@AndroidEntryPoint
class HomeFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentHomeBinding

    @Inject
    lateinit var topMoviesAdapter: TopMoviesAdapter

    @Inject
    lateinit var genresAdapter: GenresAdapter

//    @Inject
//    lateinit var lastMoviesAdapter: LastMoviesAdapter

    //ViewModel
    private val viewModel: HomeViewModel by viewModels()
    //Other
    /*
    * برای این نوع اسکرول کردن که هر بار فقط یک آیتم در صفحه قرار بگیره اندروید یک قابلیتی را به ما میده به اسم pagerSnapHelper
    * */
    private val pagerHelper: PagerSnapHelper by lazy { PagerSnapHelper() }

    /*
    * چرا api ها را در onCreate صدا میزنیم و نه در onCreateView ؟
    * چون onCreate زمانی که فرگمنت ساخته میشه فقط یک بار صدا زده میشه
    * حتی وقتی بین فرگمنت ها سوییچ میکنیم و از فرگمنت a میریم به b و سپس c و بعد برمیگردیم به a
    * در این حالت onCreateView یک بار دیگه صدا زده میشه
    * ما برای اینکه اضافه کال نکنیم api را در onCreate میسازیم
    * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Call api
        viewModel.loadTopMoviesList(3)
        viewModel.loadGenresList()
//        viewModel.loadLastMoviesList()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Get top movies
            viewModel.topMoviesList.observe(viewLifecycleOwner) {
                // it : ResponseMoviesList

                topMoviesAdapter.differ.submitList(it.data)

                //RecyclerView
                /*
                * میخوایم برای ریسایکلر ویو یک اکستنشن فانکشن بنویسیم چون قراره خیلی ازش استفاده کنیم تو پروژه
                * حتی ممکنه آپشن های خاص بخوایم براش بذاریم
                * یک بار توی اکستنشن فانکشن تعریف میکنیم و بعد استفاده میکنیم
                *
                * */
                topMoviesRecycler.initRecycler(
                    /*
                    * برای اپ های فارسی reverseLayout را true بذارید که RTL شه
                    * */
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    topMoviesAdapter
                )
                //Indicator & pagerSnapHelper
                pagerHelper.attachToRecyclerView(topMoviesRecycler)
                /*
                * نحوه وصل کردن recyclerView به indicator
                * یک پارامتر دوم هم میگیره
                * ولی پاس دادن این پارامتر دومی الزامی نیست
                * */
                topMoviesIndicator.attachToRecyclerView(topMoviesRecycler, pagerHelper)
            }
            //Get genres
            viewModel.genresList.observe(viewLifecycleOwner) {
                //it : ResponseGenresList
                genresAdapter.differ.submitList(it)
                genresRecycler.initRecycler(
                    LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false),
                    genresAdapter
                )
            }
            //Get last movies
//            viewModel.lastMoviesList.observe(viewLifecycleOwner) {
//                lastMoviesAdapter.setData(it.data)
//                //RecyclerView
//                lastMoviesRecycler.initRecycler(LinearLayoutManager(requireContext()), lastMoviesAdapter)
//            }
            //Click
//            lastMoviesAdapter.setOnItemClickListener {
//                val direction = HomeFragmentDirections.actionToDetail(it.id!!.toInt())
//                findNavController().navigate(direction)
//            }

            //Loading
//            viewModel.loading.observe(viewLifecycleOwner) {
//                if (it) {
//                    moviesLoading.showInvisible(true)
//                    moviesScrollLay.showInvisible(false)
//                } else {
//                    moviesLoading.showInvisible(false)
//                    moviesScrollLay.showInvisible(true)
//                }
//            }
        }
    }
}