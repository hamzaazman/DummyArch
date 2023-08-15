package com.hamza.domain.domain.usecase.search.add

import com.hamza.domain.domain.repository.SearchRepository
import com.hamza.domain.domain.usecase.search.add.AddRecentSearchUseCase
import javax.inject.Inject

class AddRecentSearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
): AddRecentSearchUseCase {
    override suspend operator fun invoke(query: String) {
        searchRepository.addRecentSearch(query)
    }
}