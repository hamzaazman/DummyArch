package com.hamza.data.data.usecase

import com.hamza.domain.domain.repository.DummyArchRepository
import com.hamza.domain.domain.usecase.search.add.AddRecentSearchUseCase
import javax.inject.Inject

class AddRecentSearchUseCaseImpl @Inject constructor(
    private val dummyArchRepository: DummyArchRepository
): AddRecentSearchUseCase {
    override suspend operator fun invoke(query: String) {
        dummyArchRepository.addRecentSearch(query)
    }
}