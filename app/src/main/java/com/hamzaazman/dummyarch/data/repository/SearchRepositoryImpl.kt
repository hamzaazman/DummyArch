package com.hamzaazman.dummyarch.data.repository

import com.hamzaazman.dummyarch.data.local.RecentSearch
import com.hamzaazman.dummyarch.data.local.RecentSearchDataStore
import com.hamzaazman.dummyarch.domain.repository.SearchRepository
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