package com.hamza.feature.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hamza.domain.domain.model.ProductUiModel
import com.hamza.domain.domain.usecase.product.bypaging.GetAllProductByPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getAllProductByPagingUseCase: GetAllProductByPagingUseCase
) : ViewModel() {

    fun fetchAllProductByPaging(): Flow<PagingData<ProductUiModel>> {
        return getAllProductByPagingUseCase.invoke().cachedIn(viewModelScope)
    }
}