package com.hamzaazman.dummyarch.domain.usecase.product

import com.hamzaazman.dummyarch.common.Resource
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.di.IoDispatcher
import com.hamzaazman.dummyarch.domain.mapper.ProductListMapper
import com.hamzaazman.dummyarch.domain.model.ProductUiModel
import com.hamzaazman.dummyarch.domain.repository.ProductRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetProductBySearchUseCase @Inject constructor(
    private val repository: ProductRepository,
    private val mapper: ProductListMapper<Product, ProductUiModel>,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    suspend operator fun invoke(query: String): Flow<Resource<List<ProductUiModel>>> {
        return repository.getProductBySearch(query).map {
            it.mapData { listProduct ->
                mapper.map(listProduct.products)
            }
        }.flowOn(ioDispatcher)
    }


}