package com.hamzaazman.dummyarch.ui.search

import com.hamzaazman.dummyarch.domain.model.ProductUiModel

sealed class SearchUiState {
    object Loading : SearchUiState()
    data class Success(val data: List<ProductUiModel>) : SearchUiState()
    data class Error(val error: Throwable) : SearchUiState()
}