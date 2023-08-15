package com.hamza.domain.domain.usecase.product.bysearch

import com.hamza.common.common.Resource
import com.hamza.common.common.model.ProductUiModel
import kotlinx.coroutines.flow.Flow

interface GetProductBySearchUseCase {
    suspend operator fun invoke(query: String): Flow<Resource<List<ProductUiModel>>>
}