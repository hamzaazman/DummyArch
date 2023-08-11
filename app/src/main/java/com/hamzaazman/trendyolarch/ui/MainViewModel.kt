package com.hamzaazman.trendyolarch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.hamzaazman.trendyolarch.common.StatusViewState
import com.hamzaazman.trendyolarch.data.model.Product
import com.hamzaazman.trendyolarch.data.repository.ProductRepository
import com.hamzaazman.trendyolarch.domain.model.ProductUiModel
import com.hamzaazman.trendyolarch.domain.usecase.GetAllProductByPagingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getAllProductByPagingUseCase: GetAllProductByPagingUseCase,
    private val repository: ProductRepository
) : ViewModel() {

    private val _mainFeedListing = MutableLiveData<List<ProductUiModel>>()
    val mainFeedListing: LiveData<List<ProductUiModel>> = _mainFeedListing

    private val _productsFlow =
        MutableStateFlow<PagingData<List<ProductUiModel>>>(PagingData.empty())
    val productsFlow: StateFlow<PagingData<List<ProductUiModel>>> get() = _productsFlow


    val products: Flow<PagingData<ProductUiModel>> =
        repository.getAllProductByPaging()
            .map { pagingData ->
                pagingData.map { it.toProductUiModel() }
            }
            .cachedIn(viewModelScope)
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
