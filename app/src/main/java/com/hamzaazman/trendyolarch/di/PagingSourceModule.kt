package com.hamzaazman.trendyolarch.di

import com.hamzaazman.trendyolarch.data.api.ProductService
import com.hamzaazman.trendyolarch.data.paging.ProductPagingSource
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
