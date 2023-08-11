package com.hamzaazman.trendyolarch.domain.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.hamzaazman.trendyolarch.data.model.Product
import com.hamzaazman.trendyolarch.data.paging.ProductPagingSource
import com.hamzaazman.trendyolarch.data.repository.ProductRepository
import com.hamzaazman.trendyolarch.domain.mapper.ProductMapper
import com.hamzaazman.trendyolarch.domain.model.ProductUiModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllProductByPagingUseCase @Inject constructor(
    private val repository: ProductRepository,
    private val paginDataSource: ProductPagingSource,
) {
    fun getProducts(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20, // Sayfa başına kaç ürün alınacağı
                enablePlaceholders = false // Yer tutucu kullanımını devre dışı bırakma
            ),
            pagingSourceFactory = { paginDataSource }
        ).flow
    }
}

