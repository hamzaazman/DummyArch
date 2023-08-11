package com.hamzaazman.dummyarch.di

import com.hamzaazman.dummyarch.data.model.Product
import com.hamzaazman.dummyarch.domain.mapper.ProductListMapper
import com.hamzaazman.dummyarch.domain.mapper.ProductMapper
import com.hamzaazman.dummyarch.domain.mapper.impl.ProductListMapperImpl
import com.hamzaazman.dummyarch.domain.mapper.impl.ProductMapperImpl
import com.hamzaazman.dummyarch.domain.model.ProductUiModel
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