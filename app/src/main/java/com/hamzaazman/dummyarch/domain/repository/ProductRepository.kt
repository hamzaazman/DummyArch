package com.hamzaazman.dummyarch.domain.repository

import androidx.paging.PagingData
import com.hamzaazman.dummyarch.common.Resource
import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.data.model.ProductResponse
import kotlinx.coroutines.flow.Flow

interface ProductRepository {

    suspend fun getProducts(): Flow<Resource<ProductResponse>>

    fun getAllProductByPaging(): Flow<PagingData<Product>>
}