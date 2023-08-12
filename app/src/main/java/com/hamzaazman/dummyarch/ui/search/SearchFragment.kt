package com.hamzaazman.dummyarch.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.hamzaazman.dummyarch.R
import com.hamzaazman.dummyarch.common.viewBinding
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
class SearchFragment : Fragment(R.layout.fragment_search) {
    private val vm: SearchViewModel by viewModels()
    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val searchAdapter by lazy { SearchAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.actionBar?.hide()

        with(binding) {

            searchRv.adapter = searchAdapter

            searchView.editText.setOnEditorActionListener { v, actionId, event ->
                searchBar.text = searchView.text
                searchView.hide()
                searchAdapter.currentList.clear()
                searchAdapter.submitList(emptyList())
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

        viewLifecycleOwner.lifecycleScope.launch {
            vm.searchProductState
                .collectLatest {
                    when (it) {
                        is SearchUiState.Error -> {
                            Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                        SearchUiState.Loading -> {

                        }

                        is SearchUiState.Success -> {
                            Log.d("Arama", "onViewCreated: ${it.data}")
                            searchAdapter.submitList(it.data)
                        }
                    }
                }
        }


    }

    companion object {
        private const val MINIMUM_SEARCH_LENGTH = 1
        private const val SEARCH_DEBOUNCE_TIME_IN_MILLISECONDS = 300L

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

