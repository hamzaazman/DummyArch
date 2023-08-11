package com.hamzaazman.trendyolarch.domain.mapper.impl

import androidx.paging.PagingData
import androidx.paging.map
import com.hamzaazman.trendyolarch.data.model.Product
import com.hamzaazman.trendyolarch.domain.mapper.ProductMapper
import com.hamzaazman.trendyolarch.domain.model.ProductUiModel

class ProductPagingMapper :
    ProductMapper<PagingData<Product>, PagingData<ProductUiModel>> {
    override fun map(input: PagingData<Product>?): PagingData<ProductUiModel> {
        return input?.map { product ->
            ProductUiModel(
                id = product.id ?: 0,
                title = product.title ?: "",
                thumbnail = product.thumbnail ?: "",
                price = product.price ?: 0,
                rating = product.rating ?: 0.0,
                description = product.description ?: ""

            )
        } ?: PagingData.empty()
    }

}