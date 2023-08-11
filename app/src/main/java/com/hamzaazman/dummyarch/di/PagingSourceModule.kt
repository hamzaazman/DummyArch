package com.hamzaazman.dummyarch.di

import com.hamzaazman.dummyarch.data.api.ProductService
import com.hamzaazman.dummyarch.data.paging.ProductPagingSource
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
