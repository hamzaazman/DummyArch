package com.hamzaazman.dummyarch.di

import android.content.Context
import com.hamzaazman.dummyarch.data.local.RecentSearchDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatastoreModule {

    @Provides
    @Singleton
    fun provideSearchDataStore(@ApplicationContext context: Context): RecentSearchDataStore {
        return RecentSearchDataStore(context)
    }
}