package com.hamza.feature.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hamza.common.common.Resource
import com.hamza.domain.domain.usecase.product.bysearch.GetProductBySearchUseCase
import com.hamza.domain.domain.usecase.search.add.AddRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.clear.ClearRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.clear.ClearRecentSearchUseCaseImpl
import com.hamza.domain.domain.usecase.search.getall.GetAllRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.getall.GetAllRecentSearchUseCaseImpl
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
    private val getAllRecentSearchUseCase: GetAllRecentSearchUseCase,
    private val addRecentSearchUseCase: AddRecentSearchUseCase,
    private val clearRecentSearchUseCase: ClearRecentSearchUseCase
) : ViewModel() {

    private val _searchProductState: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.Loading(isLoading = false))
    val searchProductState: StateFlow<SearchUiState> get() = _searchProductState.asStateFlow()

    suspend fun addRecentSearch(query: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            addRecentSearchUseCase.invoke(query)
        }
    }

    suspend fun clearRecentBySearch(query: String) = viewModelScope.launch {
        withContext(Dispatchers.IO) {
            clearRecentSearchUseCase.invoke(query)
        }
    }

    val readRecentSearch = getAllRecentSearchUseCase.invoke()


    suspend fun getProductsBySearch(query: String) {
        getProductBySearchUseCase.invoke(query).onEach {
            when (it) {
                is Resource.Error -> {
                    _searchProductState.value = SearchUiState.Error(it.exception)
                }

                is Resource.Loading -> {
                    _searchProductState.value = SearchUiState.Loading(isLoading = true)
                }

                is Resource.Success -> {
                    _searchProductState.value = SearchUiState.Success(data = it.data)
                }

                else -> {}
            }
        }.launchIn(viewModelScope)
    }

}