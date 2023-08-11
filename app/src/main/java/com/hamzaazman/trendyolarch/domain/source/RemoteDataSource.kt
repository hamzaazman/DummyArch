package com.hamzaazman.trendyolarch.domain.source

import com.hamzaazman.trendyolarch.data.model.ProductResponse

interface RemoteDataSource {
    suspend fun getProducts(): ProductResponse
    suspend fun getAllProductByPaging(
        nextPage: Int,
        limit: Int
    ): ProductResponse
}