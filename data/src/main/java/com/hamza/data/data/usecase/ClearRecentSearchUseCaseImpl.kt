package com.hamza.data.data.usecase

import com.hamza.domain.domain.repository.DummyArchRepository
import com.hamza.domain.domain.usecase.search.clear.ClearRecentSearchUseCase
import javax.inject.Inject

class ClearRecentSearchUseCaseImpl @Inject constructor(
    private val dummyArchRepository: DummyArchRepository
): ClearRecentSearchUseCase {
    override suspend operator fun invoke(query: String){
        dummyArchRepository.clearRecentByQuery(query)
    }
}