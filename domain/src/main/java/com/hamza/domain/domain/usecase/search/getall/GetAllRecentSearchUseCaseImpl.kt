package com.hamza.domain.domain.usecase.search.getall

import com.hamza.common.common.local.RecentSearch
import com.hamza.domain.domain.di.IoDispatcher
import com.hamza.domain.domain.repository.SearchRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecentSearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
):GetAllRecentSearchUseCase {
    override operator fun invoke(): Flow<List<RecentSearch>> {
        return searchRepository.recentSearches().flowOn(ioDispatcher)
    }
}