package com.hamza.domain.domain.repository

import androidx.paging.PagingData
import com.hamza.common.common.Resource
import com.hamza.common.common.local.RecentSearch
import com.hamza.data.data.model.Product
import com.hamza.data.data.model.ProductResponse
import kotlinx.coroutines.flow.Flow

interface DummyArchRepository {

    suspend fun getProducts(): Flow<Resource<ProductResponse>>

    fun getAllProductByPaging(): Flow<PagingData<Product>>

    suspend fun getProductBySearch(query: String): Flow<Resource<ProductResponse>>

    fun recentSearches(): Flow<List<RecentSearch>>
    suspend fun addRecentSearch(query: String)
    suspend fun clearRecentByQuery(query: String)
}