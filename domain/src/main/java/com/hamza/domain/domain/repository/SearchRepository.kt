package com.hamza.domain.domain.repository

import com.hamza.common.common.local.RecentSearch
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun recentSearches(): Flow<List<RecentSearch>>
    suspend fun addRecentSearch(query: String)
    suspend fun clearRecentByQuery(query: String)
}