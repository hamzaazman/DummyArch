package com.hamza.domain.domain.usecase.product.bysearch

import androidx.paging.PagingData
import com.hamza.common.common.Resource
import com.hamza.domain.domain.model.ProductUiModel
import kotlinx.coroutines.flow.Flow

interface GetProductBySearchUseCase {
    suspend operator fun invoke(query: String): Flow<Resource<List<ProductUiModel>>>
}