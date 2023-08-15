package com.hamza.domain.domain.usecase.product.bysearch

import com.hamza.common.common.Resource
import com.hamza.data.data.model.Product
import com.hamza.domain.domain.di.IoDispatcher
import com.hamza.domain.domain.mapper.ProductListMapper
import com.hamza.domain.domain.model.ProductUiModel
import com.hamza.domain.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductBySearchUseCaseImpl @Inject constructor(
    private val repository: ProductRepository,
    private val mapper: ProductListMapper<Product, ProductUiModel>,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetProductBySearchUseCase {
    override suspend operator fun invoke(query: String): Flow<Resource<List<ProductUiModel>>> {
        return repository.getProductBySearch(query).map {
            it.mapData { listProduct ->
                mapper.map(listProduct.products)
            }
        }.flowOn(ioDispatcher)
    }


}