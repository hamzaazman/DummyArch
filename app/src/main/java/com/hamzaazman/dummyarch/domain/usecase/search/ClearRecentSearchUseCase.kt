package com.hamzaazman.dummyarch.domain.usecase.search

import com.hamzaazman.dummyarch.domain.repository.SearchRepository
import javax.inject.Inject

class ClearRecentSearchUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String){
        searchRepository.clearRecentByQuery(query)
    }
}