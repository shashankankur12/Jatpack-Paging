package com.example.jatpackpaging.ui.lumperList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.jatpackpaging.data.responses.Data
import com.example.jatpackpaging.databinding.AdapterItemBinding
import com.example.jatpackpaging.utils.UiUtils

class FoodAdapter(private val clicked: (String) -> Unit) :
    PagingDataAdapter<Data, FoodAdapter.PlayersViewHolder>(
        PlayersDiffCallback()
    ) {


    override fun onBindViewHolder(holder: PlayersViewHolder, position: Int) {
        val data = getItem(position)

        holder.bind(data)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayersViewHolder {
        return PlayersViewHolder(
            AdapterItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    }

    inner class PlayersViewHolder(
        private val binding: AdapterItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(data: Data?) {

            binding.let {
                it.root.setOnClickListener {
                    data?.title?.let { it1 -> clicked.invoke(it1) }
                }
                it.title.text = data?.title
                it.rating.text = data?.rating.toString()

                Glide.with(it.imageView.context).load(data?.featuredImage)
                    .placeholder(UiUtils.getShimmerDrawable()).into(it.imageView)

            }

        }
    }

    private class PlayersDiffCallback : DiffUtil.ItemCallback<Data>() {
        override fun areItemsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem.pk == newItem.pk
        }

        override fun areContentsTheSame(oldItem: Data, newItem: Data): Boolean {
            return oldItem == newItem
        }
    }

}