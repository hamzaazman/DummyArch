package com.hamzaazman.dummyarch.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hamzaazman.dummyarch.data.local.RecentSearch
import com.hamzaazman.dummyarch.databinding.RecentSearchRowItemBinding


class RecentSearchAdapter(
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<RecentSearchAdapter.MyViewHolder>() {

    class MyViewHolder(
        private val binding: RecentSearchRowItemBinding,
        private val listener: OnItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(recent: RecentSearch) = with(binding) {
            recentText.text = recent.query

            root.setOnClickListener {
                listener.onRecentSearchItem(recent)
            }

            clearIcon.setOnClickListener {
                listener.deleteSearchItem(recent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            RecentSearchRowItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            listener
        )
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentList = differ.currentList[position]
        holder.bind(currentList)
    }

    private val diffUtilCallback = object : DiffUtil.ItemCallback<RecentSearch>() {
        override fun areItemsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
            return oldItem.query == newItem.query
        }

        override fun areContentsTheSame(oldItem: RecentSearch, newItem: RecentSearch): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, diffUtilCallback)

    interface OnItemClickListener {
        fun onRecentSearchItem(recent: RecentSearch)
        fun deleteSearchItem(recent: RecentSearch)
    }
}