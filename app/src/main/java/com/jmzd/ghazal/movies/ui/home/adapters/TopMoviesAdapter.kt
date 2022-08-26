package com.jmzd.ghazal.movies.ui.home.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmzd.ghazal.movies.databinding.ItemHomeMoviesTopBinding
import com.jmzd.ghazal.movies.models.home.ResponseMoviesList
import com.jmzd.ghazal.movies.models.home.ResponseMoviesList.*
import javax.inject.Inject

/*
* برای تزریق کردن این آداپتر به گراف هیلت از Inject استفاده میکنیم
*
*
* */
class TopMoviesAdapter @Inject constructor() : RecyclerView.Adapter<TopMoviesAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeMoviesTopBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopMoviesAdapter.ViewHolder {
        binding = ItemHomeMoviesTopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: TopMoviesAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        /* برای duplicate نشدن دیتا */
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = if (differ.currentList.size > 5) 5 else differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: Data) {
            binding.apply {
                movieNameTxt.text = item.title
                movieInfoTxt.text = "${item.imdbRating} | ${item.country} | ${item.year}"
                moviePosterImg.load(item.poster) {
                    crossfade(true)
                    crossfade(800)
                }
            }
        }
    }

    /*
    * چون دیتا یک کلاس هست داخل کلاس ResponseMoviesList
    * وقتی import را میزنیم به این شکل مینویدش
    * ResponseMoviesList.Data
    * برای حل این موضوع و خلاصه شدنش
    * ResponseMoviesList -> RClick -> import members from...
    *
    *
    * */
    private val differCallback = object : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}