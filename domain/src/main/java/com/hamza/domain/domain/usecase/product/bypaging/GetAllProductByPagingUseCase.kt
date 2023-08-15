package com.hamza.domain.domain.usecase.product.bypaging

import androidx.paging.PagingData
import com.hamza.common.common.model.ProductUiModel
import kotlinx.coroutines.flow.Flow

interface GetAllProductByPagingUseCase {
     operator fun invoke(): Flow<PagingData<ProductUiModel>>
}