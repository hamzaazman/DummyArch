package com.hamza.common.common.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.hamza.common.common.local.RecentSearchDataStore.PreferencesKeys.RECENT_SEARCHES
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

data class RecentSearch(val query: String)

class RecentSearchDataStore @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "recent_searches")

    val recentSearchesFlow: Flow<List<RecentSearch>> = context.dataStore.data
        .map { preferences ->
            val searchList = preferences[RECENT_SEARCHES] ?: ""
            searchList.split(",")
                .filter { it.isNotBlank() }
                .map { RecentSearch(it) }
        }.distinctUntilChanged()

    suspend fun addRecentSearch(query: String) {
        context.dataStore.edit { preferences ->
            val currentSearches = preferences[RECENT_SEARCHES] ?: ""
            val searchList =
                currentSearches.split(",").toMutableSet() // Convert to a set to remove duplicates
            searchList.add(query)
            preferences[RECENT_SEARCHES] = searchList.joinToString(",")
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
