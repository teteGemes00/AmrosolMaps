package com.fantopo.metacrtl.core.domain.usecase

import com.fantopo.metacrtl.core.data.repository.LocationRepository
import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.HistoryEntry
import com.fantopo.metacrtl.core.model.SavedLocation
import kotlinx.coroutines.flow.Flow

class GetSavedLocationsUseCase(private val repository: LocationRepository) {
    operator fun invoke(): Flow<List<SavedLocation>> = repository.getSavedLocations()
}

class SaveLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(name: String, latitude: Double, longitude: Double) {
        val entry = SavedLocation(
            name = name.ifBlank { "Location ${latitude}, ${longitude}" },
            latitude = latitude,
            longitude = longitude
        )
        repository.saveLocation(entry)
    }

    suspend operator fun invoke(location: SavedLocation) {
        repository.saveLocation(location)
    }
}

class DeleteSavedLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(id: String) = repository.deleteSavedLocation(id)
}

class UpdateSavedLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(location: SavedLocation) = repository.updateSavedLocation(location)
}

class GetFavoriteLocationsUseCase(private val repository: LocationRepository) {
    operator fun invoke(): Flow<List<FavoriteLocation>> = repository.getFavoriteLocations()
}

class AddFavoriteLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(name: String, latitude: Double, longitude: Double) {
        val favorite = FavoriteLocation(
            name = name.ifBlank { "Favorite ${latitude}, ${longitude}" },
            latitude = latitude,
            longitude = longitude
        )
        repository.addFavoriteLocation(favorite)
    }

    suspend operator fun invoke(favorite: FavoriteLocation) {
        repository.addFavoriteLocation(favorite)
    }
}

class DeleteFavoriteLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(id: String) = repository.deleteFavoriteLocation(id)
}

class UpdateFavoriteLocationUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(favorite: FavoriteLocation) = repository.updateFavoriteLocation(favorite)
}

class GetHistoryUseCase(private val repository: LocationRepository) {
    operator fun invoke(): Flow<List<HistoryEntry>> = repository.getHistory()
}

class AddHistoryEntryUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(name: String? = null, latitude: Double, longitude: Double) {
        val entry = HistoryEntry(
            name = name?.ifBlank { HistoryEntry.DEFAULT_NAME } ?: HistoryEntry.DEFAULT_NAME,
            latitude = latitude,
            longitude = longitude
        )
        repository.addHistoryEntry(entry)
    }

    suspend operator fun invoke(entry: HistoryEntry) {
        repository.addHistoryEntry(entry)
    }
}

class DeleteHistoryEntryUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke(id: String) = repository.deleteHistoryEntry(id)
}

class ClearHistoryUseCase(private val repository: LocationRepository) {
    suspend operator fun invoke() = repository.clearHistory()
}
