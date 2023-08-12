package com.hamzaazman.dummyarch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.hamzaazman.dummyarch.common.StatusViewState
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.data.repository.ProductRepositoryImpl
import com.hamzaazman.dummyarch.domain.model.ProductUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: ProductRepositoryImpl
) : ViewModel() {

    private val _mainFeedListing = MutableLiveData<List<ProductUiModel>>()
    val mainFeedListing: LiveData<List<ProductUiModel>> = _mainFeedListing

    private val statusState = MutableLiveData<StatusViewState>()
    val status: LiveData<StatusViewState> = statusState

    init {
        fetchAllProductByPaging()
    }
    /*
        fun fetchAllProduct() {
            getAllProductUseCase.invoke()
                .doOnSuccess { data ->
                    statusState.value = StatusViewState(status = Status.Content)
                    _mainFeedListing.value = data
                }
                .doOnStatusChanged { status ->
                    statusState.value = StatusViewState(status)
                }
                .launchIn(viewModelScope)
        }

     */

    fun fetchAllProductByPaging(): Flow<PagingData<Product>> {
        return repository.getAllProductByPaging().cachedIn(viewModelScope)
    }

}

fun Product.toProductUiModel(): ProductUiModel {
    return ProductUiModel(
        id = this.id ?: 0,
        title = this.title ?: "",
        thumbnail = this.thumbnail ?: "",
        price = this.price ?: 0,
        rating = this.rating ?: 0.0,
        description = this.description ?: ""

    )
}
