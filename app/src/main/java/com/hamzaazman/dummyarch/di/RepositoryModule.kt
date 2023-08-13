package com.hamzaazman.dummyarch.di

import com.hamzaazman.dummyarch.data.repository.ProductRepositoryImpl
import com.hamzaazman.dummyarch.data.repository.SearchRepositoryImpl
import com.hamzaazman.dummyarch.domain.repository.ProductRepository
import com.hamzaazman.dummyarch.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
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