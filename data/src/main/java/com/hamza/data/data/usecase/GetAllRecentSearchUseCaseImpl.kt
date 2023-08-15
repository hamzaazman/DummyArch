package com.hamza.data.data.usecase

import com.hamza.common.common.local.RecentSearch
import com.hamza.data.data.di.IoDispatcher
import com.hamza.domain.domain.repository.DummyArchRepository
import com.hamza.domain.domain.usecase.search.getall.GetAllRecentSearchUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetAllRecentSearchUseCaseImpl @Inject constructor(
    private val dummyArchRepository: DummyArchRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): GetAllRecentSearchUseCase {
    override operator fun invoke(): Flow<List<RecentSearch>> {
        return dummyArchRepository.recentSearches().flowOn(ioDispatcher)
    }
}