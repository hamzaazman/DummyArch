package com.hamzaazman.dummyarch.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hamzaazman.dummyarch.R
import com.hamzaazman.dummyarch.databinding.LoaderItemBinding


class LoaderAdapter(
    private val pagingAdapter: MainPagingAdapter
) :
    LoadStateAdapter<LoaderAdapter.LoaderViewHolder>() {

    class LoaderViewHolder(
        private val binding: LoaderItemBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener {
                retryCallback()
            }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                loaderBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
                errorMessage.isVisible =
                    !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMessage.text =
                    binding.root.resources.getString(R.string.no_internet_connection)
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
        ) {
            pagingAdapter.retry()
        }
    }
}