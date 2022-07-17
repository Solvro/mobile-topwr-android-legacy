package com.solvro.topwr.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.solvro.topwr.di.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


class DataStoreSource @Inject constructor(
    @ApplicationContext context: Context
) {
    companion object {
        const val SEARCH_HISTORY_LIMIT = 10
    }

    private val dataStore = context.dataStore

    fun addToBuildingsSearchHistory(id: Int) {
        runBlocking {
            dataStore.edit { prefs ->
                val currentHist = ArrayList(getBuildingsSearchHistory())
                val newHist = currentHist.apply {
                    if (size >= SEARCH_HISTORY_LIMIT)
                        drop(0)
                    add(id)
                }
                prefs[DataStoreKeys.BUILDINGS_SEARCH_HISTORY] = newHist.joinToString(",")
            }
        }
    }

    fun getBuildingsSearchHistory(): List<Int> {
        return runBlocking {
            val data = dataStore.data.map { prefs ->
                prefs[DataStoreKeys.BUILDINGS_SEARCH_HISTORY] ?: ""
            }.first()
            if (data.isBlank()) return@runBlocking listOf()
            ArrayList(data.split(",").map {
                it.toInt()
            })
        }
    }
}

private object DataStoreKeys {
    val BUILDINGS_SEARCH_HISTORY = stringPreferencesKey("buildings_search_history")
}