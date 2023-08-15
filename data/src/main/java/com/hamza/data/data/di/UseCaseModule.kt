package com.hamza.data.data.di

import com.hamza.data.data.usecase.AddRecentSearchUseCaseImpl
import com.hamza.data.data.usecase.ClearRecentSearchUseCaseImpl
import com.hamza.data.data.usecase.GetAllProductByPagingUseCaseImpl
import com.hamza.data.data.usecase.GetAllRecentSearchUseCaseImpl
import com.hamza.data.data.usecase.GetProductBySearchUseCaseImpl
import com.hamza.domain.domain.usecase.product.bypaging.GetAllProductByPagingUseCase
import com.hamza.domain.domain.usecase.product.bysearch.GetProductBySearchUseCase
import com.hamza.domain.domain.usecase.search.add.AddRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.clear.ClearRecentSearchUseCase
import com.hamza.domain.domain.usecase.search.getall.GetAllRecentSearchUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

abstract class UseCaseModule {

    @Binds
    abstract fun bindGetAllProductByPagingUseCase(getAllProductByPagingUseCaseImpl: GetAllProductByPagingUseCaseImpl): GetAllProductByPagingUseCase


    @Binds
    abstract fun bindGetProductBySearchUseCase(getProductBySearchUseCaseImpl: GetProductBySearchUseCaseImpl): GetProductBySearchUseCase

    @Binds
    abstract fun bindAddRecentSearchUseCase(addRecentSearchUseCaseImpl: AddRecentSearchUseCaseImpl): AddRecentSearchUseCase


    @Binds
    abstract fun bindClearRecentSearchUseCase(clearRecentSearchUseCaseImpl: ClearRecentSearchUseCaseImpl): ClearRecentSearchUseCase


    @Binds
    abstract fun bindGetAllRecentSearchUseCase(getAllRecentSearchUseCaseImpl: GetAllRecentSearchUseCaseImpl): GetAllRecentSearchUseCase



}