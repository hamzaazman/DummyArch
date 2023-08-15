package com.hamza.feature.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamza.common.common.model.ProductUiModel
import com.hamza.feature.R
import com.hamza.feature.databinding.ProductRowItemBinding

class MainPagingAdapter() :
    PagingDataAdapter<ProductUiModel, MainPagingAdapter.MainPagingViewHolder>(DiffCallBackProduct) {
    class MainPagingViewHolder(private val binding: ProductRowItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: ProductUiModel) = with(binding) {
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

    object DiffCallBackProduct : DiffUtil.ItemCallback<ProductUiModel>() {
        override fun areItemsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel): Boolean {
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