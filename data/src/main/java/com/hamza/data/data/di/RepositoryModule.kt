package com.hamza.data.data.di

import com.hamza.data.data.repository.DummyArchRepositoryImpl
import com.hamza.domain.domain.repository.DummyArchRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindDummyArchRepository(
        dummyArchRepositoryImpl: DummyArchRepositoryImpl,
    ): DummyArchRepository

}