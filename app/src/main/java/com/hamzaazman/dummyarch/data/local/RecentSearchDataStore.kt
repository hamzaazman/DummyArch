package com.hamzaazman.dummyarch.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hamzaazman.dummyarch.data.local.RecentSearchDataStore.PreferencesKeys.RECENT_SEARCHES
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class RecentSearch(val query: String)

class RecentSearchDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recent_searches")

    val recentSearchesFlow: Flow<List<RecentSearch>> = context.dataStore.data
        .map { preferences ->
            val searchList = preferences[RECENT_SEARCHES] ?: ""
            searchList.split(",")
                .filter { it.isNotBlank() }
                .map { RecentSearch(it) }
        }

    suspend fun addRecentSearch(query: String) {
        context.dataStore.edit { preferences ->
            val currentSearches = preferences[RECENT_SEARCHES] ?: ""
            preferences[RECENT_SEARCHES] = "$query,$currentSearches"
        }
    }

    suspend fun clearRecentSearches() {
        context.dataStore.edit { preferences ->
            preferences[RECENT_SEARCHES] = ""
        }
    }


    suspend fun clearRecentByQuery(query: String) {
        context.dataStore.edit { preferences ->
            val currentSearches = preferences[RECENT_SEARCHES] ?: ""
            val updatedSearches = currentSearches.split(",").filter { it != query }
            preferences[RECENT_SEARCHES] = updatedSearches.joinToString(",")
        }
    }


    private object PreferencesKeys {
        val RECENT_SEARCHES = stringPreferencesKey("recent_searches")
    }
}
