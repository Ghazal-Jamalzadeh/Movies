package com.jmzd.ghazal.movies.ui.search


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.movies.databinding.FragmentSearchBinding
import com.jmzd.ghazal.movies.ui.home.adapters.LastMoviesAdapter
import com.jmzd.ghazal.movies.utils.initRecycler
import com.jmzd.ghazal.movies.utils.showInvisible
import com.jmzd.ghazal.movies.viewmodel.SearchViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SearchFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentSearchBinding

    //Adapter
    @Inject
    lateinit var searchAdapter: LastMoviesAdapter

    //ViewModel
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {

            //Search
            /*
            * میخوایم به ازای وارد کزدن هر یک کاراکتر سرچ برای ما صورت بگیرد
            * به صورت اتوماتیک و بدون زدن هیچ دکمه ای
            * خود editText این قابلیت را به ما میدهد
            * یک قبلیتی دارد به اسم addTextChangedListener
            *
            *
            * */
            searchEdt.addTextChangedListener {
                // it : Editable?

                /* it.toString مقدار داخل ادیت تکست را به ما میدهد*/
                val search = it.toString()
                /* به ازای کم و زیاد شدن هر کاراکتر لیست فیلم ها رو سرچ میکنه */
                if (search.isNotEmpty()) {
                    viewModel.loadSearchMovies(search)
                }
            }
            //Get movies list
            viewModel.moviesList.observe(viewLifecycleOwner) {
                // it : ResponseMoviesList
                /* با استفاده از متد لیست را میفرستیم */
                searchAdapter.setData(it.data)
                moviesRecycler.initRecycler(LinearLayoutManager(requireContext()), searchAdapter)
            }
            //Click
//            searchAdapter.setOnItemClickListener {
//                val direction = SearchFragmentDirections.actionToDetail(it.id!!.toInt())
//                findNavController().navigate(direction)
//            }
            //Loading
            viewModel.loading.observe(viewLifecycleOwner) {
                if (it) {
                    searchLoading.showInvisible(true)
                } else {
                    searchLoading.showInvisible(false)
                }
            }
            //Empty items
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    moviesRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    moviesRecycler.showInvisible(true)
                }
            }
        }
    }
}