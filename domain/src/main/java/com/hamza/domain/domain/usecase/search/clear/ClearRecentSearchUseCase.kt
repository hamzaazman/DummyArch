package com.hamza.domain.domain.usecase.search.clear

interface ClearRecentSearchUseCase {
    suspend operator fun invoke(query: String)
}