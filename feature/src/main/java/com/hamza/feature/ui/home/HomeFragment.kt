package com.hamza.feature.ui.home

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.hamza.common.common.viewBinding
import com.hamza.feature.R
import com.hamza.feature.databinding.FragmentHomeBinding
import com.hamza.feature.ui.home.adapter.LoaderAdapter
import com.hamza.feature.ui.home.adapter.MainPagingAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {
    private val vm: HomeViewModel by viewModels()
    private val binding by viewBinding (FragmentHomeBinding::bind)
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

        viewLifecycleOwner.lifecycleScope.launch {
            pagingAdapter.loadStateFlow.flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
                .collect { loadStates ->
                    binding.productRv.isVisible = loadStates.refresh !is LoadState.Loading
                binding.loadingBar.isVisible = loadStates.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.fetchAllProductByPaging().collectLatest {
                pagingAdapter.submitData(it)
            }
        }

    }


}