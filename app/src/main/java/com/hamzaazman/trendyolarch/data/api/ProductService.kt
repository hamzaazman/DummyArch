package com.hamzaazman.trendyolarch.data.api

import com.hamzaazman.trendyolarch.data.model.ProductResponse
import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getAllProduct(): ProductResponse
}