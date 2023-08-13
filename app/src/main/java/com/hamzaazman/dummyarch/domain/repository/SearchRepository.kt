package com.hamzaazman.dummyarch.domain.repository

import com.hamzaazman.dummyarch.data.local.RecentSearch
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun recentSearches(): Flow<List<RecentSearch>>
    suspend fun addRecentSearch(query: String)
    suspend fun clearRecentByQuery(query: String)
}