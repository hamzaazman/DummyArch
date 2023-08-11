package com.hamzaazman.trendyolarch.data.source.remote

import com.hamzaazman.trendyolarch.data.api.ProductService
import com.hamzaazman.trendyolarch.data.model.ProductResponse
import com.hamzaazman.trendyolarch.domain.source.RemoteDataSource
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(
    private val productService: ProductService
) : RemoteDataSource {

    override suspend fun getProducts(): ProductResponse {
        return productService.getAllProduct()
    }

    override suspend fun getAllProductByPaging(
        nextPage: Int,
        limit: Int
    ): ProductResponse {
        return productService.getAllProductByPaging(
            nextPage,
            limit
        )
    }

}