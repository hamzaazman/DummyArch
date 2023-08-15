package com.hamza.data.data.usecase

import androidx.paging.PagingData
import androidx.paging.map
import com.hamza.common.common.model.ProductUiModel
import com.hamza.data.data.di.IoDispatcher
import com.hamza.data.data.model.Product
import com.hamza.domain.domain.mapper.ProductMapper
import com.hamza.domain.domain.repository.DummyArchRepository
import com.hamza.domain.domain.usecase.product.bypaging.GetAllProductByPagingUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetAllProductByPagingUseCaseImpl @Inject constructor(
    private val dummyArchRepository: DummyArchRepository,
    private val mapper: ProductMapper<Product, ProductUiModel>,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : GetAllProductByPagingUseCase {

    override  fun invoke(): Flow<PagingData<ProductUiModel>> {
        return dummyArchRepository.getAllProductByPaging().map { pagingData ->
            pagingData.map { product ->
                mapper.map(product)
            }
        }.flowOn(ioDispatcher)
    }
}

