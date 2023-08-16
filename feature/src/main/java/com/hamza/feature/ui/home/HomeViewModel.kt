package com.hamza.feature.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hamza.common.common.model.ProductUiModel
import com.hamza.domain.domain.usecase.product.bypaging.GetAllProductByPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    getAllProductByPagingUseCase: GetAllProductByPagingUseCase
) : ViewModel() {

    val state = getAllProductByPagingUseCase.invoke()
        .cachedIn(viewModelScope)
        .map { data ->
            UiState(data)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(
                stopTimeoutMillis = SharingStarted.DEFAULT_STOP_TIMEOUT
            ),
            initialValue = UiState()
        )

    companion object {
        val SharingStarted.Companion.DEFAULT_STOP_TIMEOUT: Long
            get() = 5_000L
    }
}

data class UiState(val data: PagingData<ProductUiModel>? = null)