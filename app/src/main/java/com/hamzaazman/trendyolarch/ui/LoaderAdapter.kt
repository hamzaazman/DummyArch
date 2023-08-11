package com.hamzaazman.trendyolarch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamzaazman.trendyolarch.databinding.LoaderItemBinding
import kotlinx.coroutines.flow.collect


class LoaderAdapter(private val viewModel: MainViewModel) :
    LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(
        private val binding: LoaderItemBinding,
        private val viewModel: MainViewModel
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            with(binding) {
                launchLoaderBar.isVisible = loadState is LoadState.Loading
                launchRetryButton.isVisible = loadState is LoadState.Error
                launchErrorMessage.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                launchErrorMessage.text = "No Internet Connection"
                launchRetryButton.setOnClickListener {
                    viewModel.fetchAllProductByPaging()
                }
            }
        }
    }

    override fun onBindViewHolder(holder: LoaderViewHolder, loadState: LoadState) {
        holder.bind(loadState = loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoaderViewHolder {
        return LoaderViewHolder(
            LoaderItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            viewModel
        )
    }
}