package com.jmzd.ghazal.movies.ui.home.adapters


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.jmzd.ghazal.movies.databinding.ItemHomeGenreListBinding
import com.jmzd.ghazal.movies.models.home.ResponseGenersList
import com.jmzd.ghazal.movies.models.home.ResponseGenersList.*
import javax.inject.Inject

class GenresAdapter @Inject constructor() : RecyclerView.Adapter<GenresAdapter.ViewHolder>() {

    private lateinit var binding: ItemHomeGenreListBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenresAdapter.ViewHolder {
        binding = ItemHomeGenreListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder()
    }

    override fun onBindViewHolder(holder: GenresAdapter.ViewHolder, position: Int) {
        holder.setData(differ.currentList[position])
        holder.setIsRecyclable(false)
    }

    override fun getItemCount() = differ.currentList.size

    inner class ViewHolder : RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun setData(item: ResponseGenersListItem) {
            binding.apply {
                nameTxt.text = item.name
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ResponseGenersListItem>() {
        override fun areItemsTheSame(oldItem: ResponseGenersListItem, newItem: ResponseGenersListItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ResponseGenersListItem, newItem: ResponseGenersListItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)
}