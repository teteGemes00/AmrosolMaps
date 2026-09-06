package com.fantopo.metacrtl.core.data.repository

import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.HistoryEntry
import com.fantopo.metacrtl.core.model.SavedLocation
import kotlinx.coroutines.flow.Flow

interface LocationRepository {
    fun getSavedLocations(): Flow<List<SavedLocation>>
    suspend fun saveLocation(location: SavedLocation)
    suspend fun deleteSavedLocation(id: String)
    suspend fun updateSavedLocation(location: SavedLocation)

    fun getFavoriteLocations(): Flow<List<FavoriteLocation>>
    suspend fun addFavoriteLocation(favorite: FavoriteLocation)
    suspend fun deleteFavoriteLocation(id: String)
    suspend fun updateFavoriteLocation(favorite: FavoriteLocation)

    fun getHistory(): Flow<List<HistoryEntry>>
    suspend fun addHistoryEntry(entry: HistoryEntry)
    suspend fun deleteHistoryEntry(id: String)
    suspend fun clearHistory()
}
