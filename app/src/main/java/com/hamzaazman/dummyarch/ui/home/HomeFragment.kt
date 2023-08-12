package com.hamzaazman.dummyarch.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.hamzaazman.dummyarch.R
import com.hamzaazman.dummyarch.common.viewBinding
import com.hamzaazman.dummyarch.databinding.FragmentHomeBinding
import com.hamzaazman.dummyarch.ui.home.adapter.LoaderAdapter
import com.hamzaazman.dummyarch.ui.home.adapter.MainPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val vm: HomeViewModel by viewModels()
    private val binding by viewBinding(FragmentHomeBinding::bind)
    private val pagingAdapter by lazy { MainPagingAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()

        binding.productRv.apply {
            adapter = with(pagingAdapter) {
                withLoadStateHeaderAndFooter(
                    header = LoaderAdapter(this), footer = LoaderAdapter(this),
                )
            }
        }
/*
        lifecycleScope.launch {
            pagingAdapter.loadStateFlow.collectLatest { loadState ->
                binding.swipeRefresh.isRefreshing = loadState.refresh is LoadState.Loading
            }
        }

        binding.swipeRefresh.setOnRefreshListener {
            lifecycleScope.launch {
                vm.fetchAllProductByPaging().collect {
                    pagingAdapter.submitData(it)
                }
            }
            binding.swipeRefresh.isRefreshing = false
        }

 */

        lifecycleScope.launch {
            vm.fetchAllProductByPaging().collect {
                pagingAdapter.submitData(it)
            }
        }

    }


}