package com.hamzaazman.trendyolarch.di

import com.hamzaazman.trendyolarch.data.model.Product
import com.hamzaazman.trendyolarch.domain.mapper.ProductListMapper
import com.hamzaazman.trendyolarch.domain.mapper.ProductMapper
import com.hamzaazman.trendyolarch.domain.mapper.impl.ProductListMapperImpl
import com.hamzaazman.trendyolarch.domain.mapper.impl.ProductMapperImpl
import com.hamzaazman.trendyolarch.domain.model.ProductUiModel
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun bindProductListMapper(productListMapper: ProductListMapperImpl): ProductListMapper<Product, ProductUiModel>

    @Binds
    abstract fun bindProductMapper(productMapper: ProductMapperImpl): ProductMapper<Product, ProductUiModel>

    /*
    @Binds
    abstract fun bindProductPagingMapper(mapper: ProductPagingMapper): ProductMapper<PagingData<Product>, PagingData<ProductUiModel>>


     */
}