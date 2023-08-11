package com.hamzaazman.trendyolarch.di

import com.hamzaazman.trendyolarch.data.source.remote.RemoteDataSourceImpl
import com.hamzaazman.trendyolarch.domain.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource


}