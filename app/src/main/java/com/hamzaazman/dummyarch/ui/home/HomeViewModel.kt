package com.hamzaazman.dummyarch.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.data.repository.ProductRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    fun fetchAllProductByPaging(): Flow<PagingData<Product>> {
        return repository.getAllProductByPaging().cachedIn(viewModelScope)
    }
}