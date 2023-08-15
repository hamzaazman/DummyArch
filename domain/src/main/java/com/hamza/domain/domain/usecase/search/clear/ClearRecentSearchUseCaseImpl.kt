package com.hamza.domain.domain.usecase.search.clear

import com.hamza.domain.domain.repository.SearchRepository
import javax.inject.Inject

class ClearRecentSearchUseCaseImpl @Inject constructor(
    private val searchRepository: SearchRepository
):ClearRecentSearchUseCase {
    override suspend operator fun invoke(query: String){
        searchRepository.clearRecentByQuery(query)
    }
}