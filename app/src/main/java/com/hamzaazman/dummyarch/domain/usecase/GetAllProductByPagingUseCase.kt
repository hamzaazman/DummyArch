package com.hamzaazman.dummyarch.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.data.repository.ProductRepositoryImpl
import com.hamzaazman.dummyarch.di.IoDispatcher
import com.hamzaazman.dummyarch.domain.mapper.ProductMapper
import com.hamzaazman.dummyarch.domain.model.ProductUiModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllProductByPagingUseCase @Inject constructor(
    private val repository: ProductRepositoryImpl,
    private val mapper: ProductMapper<Product, ProductUiModel>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
) {
    operator fun invoke(): Flow<PagingData<ProductUiModel>> {
        return repository.getAllProductByPaging().map { pagingData ->
            pagingData.map { product ->
                mapper.map(product)
            }
        }.flowOn(ioDispatcher)
    }
}

