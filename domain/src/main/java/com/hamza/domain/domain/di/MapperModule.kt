package com.hamza.domain.domain.di

import com.hamza.common.common.model.ProductUiModel
import com.hamza.data.data.model.Product
import com.hamza.domain.domain.mapper.ProductListMapper
import com.hamza.domain.domain.mapper.ProductMapper
import com.hamza.domain.domain.mapper.impl.ProductListMapperImpl
import com.hamza.domain.domain.mapper.impl.ProductMapperImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class MapperModule {
    @Binds
    abstract fun bindProductListMapper(productListMapper: ProductListMapperImpl): ProductListMapper<Product, ProductUiModel>

    @Binds
    abstract fun bindProductMapper(productMapper: ProductMapperImpl): ProductMapper<Product, ProductUiModel>

}