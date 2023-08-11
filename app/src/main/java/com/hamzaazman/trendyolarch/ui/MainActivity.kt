package com.hamzaazman.trendyolarch.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.hamzaazman.trendyolarch.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val vm: MainViewModel by viewModels()
    private val mainAdapter by lazy { MainAdapter() }
    private val pagingMainAdapter by lazy { MainPagingAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.productRv.apply {
            adapter = pagingMainAdapter.withLoadStateFooter(
                footer = LoaderAdapter(vm)
            )
        }



        binding.swipeRefresh.setOnRefreshListener {
            // vm.fetchAllProduct()
            lifecycleScope.launch {
                vm.fetchAllProductByPaging().collect()
            }
            binding.swipeRefresh.isRefreshing = false
        }

        /*vm.mainFeedListing.observe(this@MainActivity) {
             mainAdapter.submitList(it)
         }
         */

        /*
        lifecycleScope.launch {
            vm.products.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collectLatest {
                pagingMainAdapter.submitData(it)
            }
        }

         */

        lifecycleScope.launch {
            vm.fetchAllProductByPaging().collect {
                pagingMainAdapter.submitData(it)
            }
        }

        /*
        vm.status.observe(this@MainActivity) {
            binding.stateLayout.showState(it.getStateInfo(this@MainActivity))
        }

        binding.stateLayout.infoButtonListener {
            //vm.fetchAllProduct()
        }

         */

    }
}