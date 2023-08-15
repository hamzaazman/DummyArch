package com.hamza.feature.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hamza.common.common.local.RecentSearch
import com.hamza.common.common.observeTextChanges
import com.hamza.common.common.okWith
import com.hamza.common.common.viewBinding
import com.hamza.feature.R
import com.hamza.feature.databinding.FragmentSearchBinding
import com.hamza.feature.ui.search.adapter.RecentSearchAdapter
import com.hamza.feature.ui.search.adapter.SearchAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), RecentSearchAdapter.OnItemClickListener {
    private val vm: SearchViewModel by viewModels()
    private val binding by viewBinding  (FragmentSearchBinding::bind)
    private val searchAdapter by lazy { SearchAdapter() }
    private val recentAdapter by lazy { RecentSearchAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()

        setView()
        collectRecentSearch()
        searchQuery()
        collectSearchQueryState()

    }

    private fun searchQuery() = with(binding) {
        searchView.editText.setOnEditorActionListener { v, actionId, event ->
            searchBar.text = searchView.text
            viewLifecycleOwner.lifecycleScope.launch {
                searchView.text?.let { vm.addRecentSearch(it.toString()) }
            }
            searchView.hide()
            searchView.editText.text.clear()
            false
        }

        searchView
            .editText.observeTextChanges()
            .filter { it okWith MINIMUM_SEARCH_LENGTH }
            .debounce(SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS)
            .distinctUntilChanged()
            .onEach {
                if (it.isNotBlank()) {
                    vm.getProductsBySearch(it)
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun collectRecentSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.readRecentSearch.distinctUntilChanged().collect {
                recentAdapter.differ.submitList(it)
            }
        }
    }

    private fun collectSearchQueryState() = with(binding) {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.searchProductState
                .collectLatest {
                    when (it) {
                        is SearchUiState.Error -> {
                            loadingBarSearch.visibility = View.GONE
                            Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT)
                                .show()
                            searchRv.visibility = View.GONE
                        }

                        is SearchUiState.Loading -> {
                            if (it.isLoading) {
                                searchRv.visibility = View.GONE
                                loadingBarSearch.visibility = View.VISIBLE
                            }
                        }

                        is SearchUiState.Success -> {
                            loadingBarSearch.visibility = View.GONE
                            searchRv.visibility = View.VISIBLE
                            searchAdapter.submitList(it.data)
                        }
                    }
                }
        }
    }

    private fun setView() = with(binding) {
        searchRv.adapter = searchAdapter
        recentSearchRv.adapter = recentAdapter
    }

    companion object {
        private const val MINIMUM_SEARCH_LENGTH = 1
        private const val SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS = 300L

    }

    override fun onRecentSearchItem(recent: RecentSearch) {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.getProductsBySearch(recent.query)
        }
        binding.searchBar.text = recent.query
        binding.searchView.hide()
    }

    override fun deleteSearchItem(recent: RecentSearch) {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.clearRecentBySearch(recent.query)
        }
    }
}