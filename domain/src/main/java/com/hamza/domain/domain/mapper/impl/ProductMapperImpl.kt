package com.hamza.domain.domain.mapper.impl

import com.hamza.common.common.model.ProductUiModel
import com.hamza.data.data.model.Product
import com.hamza.domain.domain.mapper.ProductMapper
import javax.inject.Inject

class ProductMapperImpl @Inject constructor() : ProductMapper<Product, ProductUiModel> {
    override fun map(input: Product?): ProductUiModel {
        return ProductUiModel(
            id = input?.id ?: 0,
            title = input?.title ?: "",
            thumbnail = input?.thumbnail ?: "",
            price = input?.price ?: 0,
            rating = input?.rating ?: 0.0,
            description = input?.description ?: ""
        )
    }
}