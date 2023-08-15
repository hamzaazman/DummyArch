package com.hamza.domain.domain.usecase.search.add

interface AddRecentSearchUseCase {
    suspend operator fun invoke(query: String)
}