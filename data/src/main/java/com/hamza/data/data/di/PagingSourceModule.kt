package com.hamza.data.data.di

import com.hamza.data.data.paging.ProductPagingSource
import com.hamzaazman.dummyarch.data.api.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object PagingSourceModule {

    @Provides
    fun provideProductPagingSource(productService: ProductService): ProductPagingSource {
        return ProductPagingSource(productService)
    }
}
