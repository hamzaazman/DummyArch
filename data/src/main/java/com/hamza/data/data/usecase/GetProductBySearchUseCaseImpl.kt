package com.hamza.data.data.usecase

import com.hamza.common.common.Resource
import com.hamza.common.common.model.ProductUiModel
import com.hamza.data.data.di.IoDispatcher
import com.hamza.data.data.model.Product
import com.hamza.domain.domain.mapper.ProductListMapper
import com.hamza.domain.domain.repository.DummyArchRepository
import com.hamza.domain.domain.usecase.product.bysearch.GetProductBySearchUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductBySearchUseCaseImpl @Inject constructor(
    private val dummyArchRepository: DummyArchRepository,
    private val mapper: ProductListMapper<Product, ProductUiModel>,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetProductBySearchUseCase {
    override suspend operator fun invoke(query: String): Flow<Resource<List<ProductUiModel>>> {
        return dummyArchRepository.getProductBySearch(query).map {
            it.mapData { listProduct ->
                mapper.map(listProduct.products)
            }
        }.flowOn(ioDispatcher)
    }


}