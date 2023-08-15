package com.hamzaazman.dummyarch.data.api

import com.hamza.data.data.model.ProductResponse
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

    ///products/search?q=Laptop
    @GET("products/search")
    suspend fun getProductBySearch(
        @Query("q") query: String
    ): ProductResponse
}