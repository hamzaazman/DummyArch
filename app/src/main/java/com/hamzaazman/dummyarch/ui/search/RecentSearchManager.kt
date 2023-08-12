package com.hamzaazman.dummyarch.ui.search

import android.content.Context

class RecentSearchManager(private val context: Context) {

    fun addSearchQuery(query: String) {
        // Veritabanına veya SharedPreferences'e ekleme işlemi
    }

    fun getRecentSearches(): List<String> {
        // Veritabanından veya SharedPreferences'ten geçmiş aramaları al
        return listOf()
    }

    fun clearRecentSearches() {
        // Geçmiş aramaları temizleme işlemi
    }
}