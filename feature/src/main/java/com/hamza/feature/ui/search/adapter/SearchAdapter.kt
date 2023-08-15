package com.hamza.feature.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamza.common.common.model.ProductUiModel
import com.hamza.feature.R
import com.hamza.feature.databinding.ProductRowItemBinding

class SearchAdapter : ListAdapter<ProductUiModel, SearchAdapter.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ProductRowItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class ViewHolder(private val binding: ProductRowItemBinding) :
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

    class DiffCallback : DiffUtil.ItemCallback<ProductUiModel>() {
        override fun areItemsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ProductUiModel, newItem: ProductUiModel) =
            oldItem == newItem
    }
}