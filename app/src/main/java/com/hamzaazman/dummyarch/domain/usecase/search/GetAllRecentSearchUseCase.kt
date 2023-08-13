package com.hamzaazman.dummyarch.domain.usecase.search

import com.hamzaazman.dummyarch.data.local.RecentSearch
import com.hamzaazman.dummyarch.di.IoDispatcher
import com.hamzaazman.dummyarch.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    operator fun invoke(): Flow<List<RecentSearch>> {
        return searchRepository.recentSearches().flowOn(ioDispatcher)
    }
}