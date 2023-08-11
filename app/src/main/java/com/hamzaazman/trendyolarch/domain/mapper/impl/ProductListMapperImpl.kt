package com.hamzaazman.trendyolarch.domain.mapper.impl

import com.hamzaazman.trendyolarch.data.model.Product
import com.hamzaazman.trendyolarch.domain.mapper.ProductListMapper
import com.hamzaazman.trendyolarch.domain.model.ProductUiModel
import javax.inject.Inject

class ProductListMapperImpl @Inject constructor() : ProductListMapper<Product, ProductUiModel> {
    override fun map(input: List<Product>?): List<ProductUiModel> {
        return input?.map {
            ProductUiModel(
                id = it.id ?: 0,
                title = it.title ?: "",
                thumbnail = it.thumbnail ?: "",
                price = it.price ?: 0,
                rating = it.rating ?: 0.0,
                description = it.description ?: ""
            )
        } ?: emptyList()
    }
}