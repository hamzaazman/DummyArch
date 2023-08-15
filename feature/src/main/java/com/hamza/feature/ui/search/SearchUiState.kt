package com.hamza.feature.ui.search

import com.hamza.common.common.model.ProductUiModel


sealed class SearchUiState {
    data class Loading(val isLoading: Boolean = false) : SearchUiState()
    data class Success(val data: List<ProductUiModel>) : SearchUiState()
    data class Error(val error: Throwable) : SearchUiState()
}