package com.jmzd.ghazal.movies.ui.favorite


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.jmzd.ghazal.movies.databinding.FragmentFavoriteBinding
import com.jmzd.ghazal.movies.utils.initRecycler
import com.jmzd.ghazal.movies.utils.showInvisible
import com.jmzd.ghazal.movies.viewmodel.FavoriteViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/*
* چون از دیتابیس میخونیم کلا لودینگ نداریم توی این صفحه
*
*
* */

@AndroidEntryPoint
class FavoriteFragment : Fragment() {
    //Binding
    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var favoriteAdapter: FavoriteAdapter

    //Other
    private val viewModel: FavoriteViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //InitViews
        binding.apply {
            //Show all favorite
            viewModel.loadFavoriteList()
            //List
            viewModel.favoriteList.observe(viewLifecycleOwner) {
                // it : MutableList<MovieEntity>!
                favoriteAdapter.setData(it)
                favoriteRecycler.initRecycler(LinearLayoutManager(requireContext()), favoriteAdapter)
            }
            //Click
            favoriteAdapter.setOnItemClickListener {
                val direction = FavoriteFragmentDirections.actionToDetailFragment(it.id)
                findNavController().navigate(direction)
            }
            //Empty
            viewModel.empty.observe(viewLifecycleOwner) {
                if (it) {
                    emptyItemsLay.showInvisible(true)
                    favoriteRecycler.showInvisible(false)
                } else {
                    emptyItemsLay.showInvisible(false)
                    favoriteRecycler.showInvisible(true)
                }
            }
        }
    }
}