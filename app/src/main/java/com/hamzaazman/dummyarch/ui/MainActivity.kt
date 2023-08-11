package com.hamzaazman.dummyarch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.hamzaazman.dummyarch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val pagingAdapter by lazy { MainPagingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productRv.apply {
            adapter = with(pagingAdapter) {
                withLoadStateHeaderAndFooter(
                    header = LoaderAdapter(this), footer = LoaderAdapter(this),
                )
            }
        }

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

        lifecycleScope.launch {
            vm.fetchAllProductByPaging().collect {
                pagingAdapter.submitData(it)
            }
        }

    }
}