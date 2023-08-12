package com.hamzaazman.dummyarch.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hamzaazman.dummyarch.R
import com.hamzaazman.dummyarch.common.viewBinding
import com.hamzaazman.dummyarch.data.local.RecentSearch
import com.hamzaazman.dummyarch.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch


@AndroidEntryPoint
class SearchFragment : Fragment(R.layout.fragment_search), RecentSearchAdapter.OnItemClickListener {
    private val vm: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val searchAdapter by lazy { SearchAdapter() }
    private val recentAdapter by lazy { RecentSearchAdapter(this) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()

        setView()
        collectRecentSearch()

        with(binding) {
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

        collectSearchQueryState()

    }

    private fun collectRecentSearch() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.readRecentSearch.distinctUntilChanged().collect {
                recentAdapter.differ.submitList(it)
            }
        }
    }

    private fun collectSearchQueryState() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.searchProductState
                .collectLatest {
                    when (it) {
                        is SearchUiState.Error -> {
                            binding.loadingBarSearch.visibility = View.GONE
                            Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT)
                                .show()
                            binding.searchRv.visibility = View.GONE
                        }

                        is SearchUiState.Loading -> {
                            if (it.isLoading) {
                                binding.searchRv.visibility = View.GONE
                                binding.loadingBarSearch.visibility = View.VISIBLE
                            }
                        }

                        is SearchUiState.Success -> {
                            binding.loadingBarSearch.visibility = View.GONE
                            binding.searchRv.visibility = View.VISIBLE
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

fun EditText.observeTextChanges(): Flow<String> {
    return callbackFlow {
        val textWatcher = object : AbstractTextWatcher() {
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                super.onTextChanged(s, start, before, count)
                trySend(s.toString())
            }
        }
        addTextChangedListener(textWatcher)

        awaitClose {
            removeTextChangedListener(textWatcher)
        }
    }.onStart {
        text?.let {
            emit(it.toString())
        }
    }
}

abstract class AbstractTextWatcher : TextWatcher {
    override fun afterTextChanged(s: Editable?) {}
    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
}

fun String.addSearchPrefix(prefix: String): String {
    return prefix.plus(this)
}

fun Int.addSearchPrefix(prefix: Int): Int {
    return prefix.plus(this)
}


infix fun String.okWith(bound: Int) = length > bound

