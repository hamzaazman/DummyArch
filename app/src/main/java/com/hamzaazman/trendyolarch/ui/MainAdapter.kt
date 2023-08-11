package com.hamzaazman.trendyolarch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hamzaazman.trendyolarch.R
import com.hamzaazman.trendyolarch.databinding.ProductRowItemBinding
import com.hamzaazman.trendyolarch.domain.model.ProductUiModel


class MainAdapter : ListAdapter<ProductUiModel, MainAdapter.ViewHolder>(DiffCallback()) {

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

        fun bind(productUiModel: ProductUiModel) = with(binding) {
            productTitle.text = bindProductTitle(productUiModel.title)
            productDesc.text = productUiModel.description

            Glide.with(binding.root.context)
                .load(productUiModel.thumbnail)
                .placeholder(R.drawable.ic_launcher_background)
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