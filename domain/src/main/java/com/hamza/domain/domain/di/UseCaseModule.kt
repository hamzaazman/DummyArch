package com.hamza.domain.domain.di

import com.hamza.domain.domain.usecase.product.bypaging.GetAllProductByPagingUseCase
import com.hamza.domain.domain.usecase.product.bypaging.GetAllProductByPagingUseCaseImpl
import com.hamza.domain.domain.usecase.product.bysearch.GetProductBySearchUseCase
import com.hamza.domain.domain.usecase.product.bysearch.GetProductBySearchUseCaseImpl
import com.hamza.domain.domain.usecase.search.add.AddRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.add.AddRecentSearchUseCaseImpl
import com.hamza.domain.domain.usecase.search.clear.ClearRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.clear.ClearRecentSearchUseCaseImpl
import com.hamza.domain.domain.usecase.search.getall.GetAllRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.getall.GetAllRecentSearchUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)

abstract class UseCaseModule {

    @Binds
    @ViewModelScoped
    abstract fun bindGetAllProductByPagingUseCase(getAllProductByPagingUseCaseImpl: GetAllProductByPagingUseCaseImpl): GetAllProductByPagingUseCase


    @Binds
    @ViewModelScoped
    abstract fun bindGetProductBySearchUseCase(getProductBySearchUseCaseImpl: GetProductBySearchUseCaseImpl): GetProductBySearchUseCase

    @Binds
    @ViewModelScoped
    abstract fun bindAddRecentSearchUseCase(addRecentSearchUseCaseImpl: AddRecentSearchUseCaseImpl): AddRecentSearchUseCase


    @Binds
    @ViewModelScoped
    abstract fun bindClearRecentSearchUseCase(clearRecentSearchUseCaseImpl: ClearRecentSearchUseCaseImpl): ClearRecentSearchUseCase


    @Binds
    @ViewModelScoped
    abstract fun bindGetAllRecentSearchUseCase(getAllRecentSearchUseCaseImpl: GetAllRecentSearchUseCaseImpl): GetAllRecentSearchUseCase



}