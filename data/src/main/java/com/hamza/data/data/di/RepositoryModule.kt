package com.hamza.data.data.di

import com.hamza.data.data.repository.ProductRepositoryImpl
import com.hamza.data.data.repository.SearchRepositoryImpl
import com.hamza.domain.domain.repository.ProductRepository
import com.hamza.domain.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl,
    ): ProductRepository

    @Binds
    abstract fun bindSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}