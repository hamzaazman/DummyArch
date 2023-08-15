package com.hamza.domain.domain.usecase.search.getall

import com.hamza.common.common.local.RecentSearch
import kotlinx.coroutines.flow.Flow

interface GetAllRecentSearchUseCase {
    operator fun invoke(): Flow<List<RecentSearch>>
}