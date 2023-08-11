package com.hamzaazman.dummyarch.domain.mapper.impl

import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.domain.mapper.ProductMapper
import com.hamzaazman.dummyarch.domain.model.ProductUiModel
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