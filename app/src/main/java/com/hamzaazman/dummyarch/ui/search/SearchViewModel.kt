package com.hamzaazman.dummyarch.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamzaazman.dummyarch.common.Resource
import com.hamzaazman.dummyarch.data.local.RecentSearchDataStore
import com.hamzaazman.dummyarch.domain.usecase.GetProductBySearchUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getProductBySearchUseCase: GetProductBySearchUseCase,
    private val searchDataStore: RecentSearchDataStore
) : ViewModel() {

    private val _searchProductState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Loading)
    val searchProductState: StateFlow<SearchUiState> get() = _searchProductState.asStateFlow()

    suspend fun addRecentSearch(query: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            searchDataStore.addRecentSearch(query)
        }
    }

    suspend fun clearRecentBySearch(query: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            searchDataStore.clearRecentByQuery(query)
        }
    }

    val readRecentSearch = searchDataStore.recentSearchesFlow


    suspend fun getProductsBySearch(query: String) {
        getProductBySearchUseCase.invoke(query).onEach {
            when (it) {
                is Resource.Error -> {
                    _searchProductState.value = SearchUiState.Error(it.exception)
                }

                is Resource.Loading -> {
                    _searchProductState.value = SearchUiState.Loading
                }

                is Resource.Success -> {
                    _searchProductState.value = SearchUiState.Success(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }

}