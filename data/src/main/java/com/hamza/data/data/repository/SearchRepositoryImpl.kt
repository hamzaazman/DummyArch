package com.hamza.data.data.repository

import com.hamza.common.common.local.RecentSearch
import com.hamza.common.common.local.RecentSearchDataStore
import com.hamza.domain.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val searchDataStore: RecentSearchDataStore
) : SearchRepository {
    override fun recentSearches(): Flow<List<RecentSearch>> {
        return searchDataStore.recentSearchesFlow
    }

    override suspend fun addRecentSearch(query: String) {
        searchDataStore.addRecentSearch(query)
    }

    override suspend fun clearRecentByQuery(query: String) {
        searchDataStore.clearRecentByQuery(query)
    }
}