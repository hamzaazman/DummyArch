package com.hamza.domain.domain.usecase.product.bypaging

import androidx.paging.PagingData
import androidx.paging.map
import com.hamza.data.data.model.Product
import com.hamza.domain.domain.di.IoDispatcher
import com.hamza.domain.domain.mapper.ProductMapper
import com.hamza.domain.domain.model.ProductUiModel
import com.hamza.domain.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllProductByPagingUseCaseImpl @Inject constructor(
    private val repository: ProductRepository,
    private val mapper: ProductMapper<Product, ProductUiModel>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetAllProductByPagingUseCase {

    override  fun invoke(): Flow<PagingData<ProductUiModel>> {
        return repository.getAllProductByPaging().map { pagingData ->
            pagingData.map { product ->
                mapper.map(product)
            }
        }.flowOn(ioDispatcher)
    }
}

