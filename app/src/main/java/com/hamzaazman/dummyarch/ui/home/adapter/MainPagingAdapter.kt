package com.hamzaazman.dummyarch.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamzaazman.dummyarch.R
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.databinding.ProductRowItemBinding

class MainPagingAdapter() :
    PagingDataAdapter<Product, MainPagingAdapter.MainPagingViewHolder>(DiffCallBackProduct) {
    class MainPagingViewHolder(private val binding: ProductRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            println(product)

            productTitle.text = bindProductTitle(product.title)
            productDesc.text = product.description

            Glide.with(binding.root.context)
                .load(product.thumbnail)
                .placeholder(R.drawable.placeholder)
                .into(productImage)
        }

        private fun bindProductTitle(text: String?): String? {
            return text?.let {
                it.replaceFirstChar { char ->
                    char.uppercaseChar()
                }
            }
        }
    }

    object DiffCallBackProduct : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: MainPagingViewHolder, position: Int) {
        val currentProduct = getItem(position)
        holder.bind(currentProduct!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainPagingViewHolder {
        return MainPagingViewHolder(
            ProductRowItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}