package com.hamzaazman.trendyolarch.data.api

import com.hamzaazman.trendyolarch.data.model.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("products")
    suspend fun getAllProduct(): ProductResponse

    @GET("products")
    suspend fun getAllProductByPaging(
        @Query("skip") nextPage: Int,
        @Query("limit") limit: Int
    ): ProductResponse
}