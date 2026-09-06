package com.fantopo.metacrtl.core.data.repository

import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.HistoryEntry
import com.fantopo.metacrtl.core.model.SavedLocation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class DefaultLocationRepository(
    initialSaved: List<SavedLocation> = emptyList(),
    initialFavorites: List<FavoriteLocation> = emptyList(),
    initialHistory: List<HistoryEntry> = emptyList()
) : LocationRepository {

    private val mutex = Mutex()

    private val _savedLocations = MutableStateFlow(initialSaved)
    override fun getSavedLocations(): Flow<List<SavedLocation>> = _savedLocations.asStateFlow()

    private val _favorites = MutableStateFlow(initialFavorites)
    override fun getFavoriteLocations(): Flow<List<FavoriteLocation>> = _favorites.asStateFlow()

    private val _history = MutableStateFlow(initialHistory)
    override fun getHistory(): Flow<List<HistoryEntry>> = _history.asStateFlow()

    override suspend fun saveLocation(location: SavedLocation) {
        mutex.withLock {
            _savedLocations.update { list ->
                val filtered = list.filterNot { it.id == location.id }
                listOf(location) + filtered
            }
        }
    }

    override suspend fun deleteSavedLocation(id: String) {
        mutex.withLock {
            _savedLocations.update { list -> list.filterNot { it.id == id } }
        }
    }

    override suspend fun updateSavedLocation(location: SavedLocation) {
        mutex.withLock {
            _savedLocations.update { list ->
                list.map { if (it.id == location.id) location else it }
            }
        }
    }

    override suspend fun addFavoriteLocation(favorite: FavoriteLocation) {
        mutex.withLock {
            _favorites.update { list ->
                val filtered = list.filterNot { it.id == favorite.id }
                listOf(favorite) + filtered
            }
        }
    }

    override suspend fun deleteFavoriteLocation(id: String) {
        mutex.withLock {
            _favorites.update { list -> list.filterNot { it.id == id } }
        }
    }

    override suspend fun updateFavoriteLocation(favorite: FavoriteLocation) {
        mutex.withLock {
            _favorites.update { list ->
                list.map { if (it.id == favorite.id) favorite else it }
            }
        }
    }

    override suspend fun addHistoryEntry(entry: HistoryEntry) {
        mutex.withLock {
            _history.update { list ->
                // Keep the most recent 100 history entries
                val updated = listOf(entry) + list
                if (updated.size > 100) updated.take(100) else updated
            }
        }
    }

    override suspend fun deleteHistoryEntry(id: String) {
        mutex.withLock {
            _history.update { list -> list.filterNot { it.id == id } }
        }
    }

    override suspend fun clearHistory() {
        mutex.withLock {
            _history.update { emptyList() }
        }
    }
}
