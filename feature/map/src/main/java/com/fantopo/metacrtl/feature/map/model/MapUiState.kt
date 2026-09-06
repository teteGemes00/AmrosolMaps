package com.fantopo.metacrtl.feature.map.model

import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.HistoryEntry
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.ProviderServiceType
import com.fantopo.metacrtl.core.model.SavedLocation

enum class DialogType {
    SAVE,
    FAVORITE,
    HISTORY,
    SETTINGS,
    PROVIDER_SERVICE
}

data class MapUiState(
    val pinnedLocation: LocationPoint = LocationPoint.DEFAULT,
    val simulatedLocation: LocationPoint = LocationPoint.DEFAULT,
    val isGpsActive: Boolean = false,
    val isFullScreen: Boolean = false,
    val zoomLevel: Float = 1.0f,
    val activeDialog: DialogType? = null,
    val isFloatingExpanded: Boolean = false,
    val savedLocations: List<SavedLocation> = emptyList(),
    val favoriteLocations: List<FavoriteLocation> = emptyList(),
    val historyEntries: List<HistoryEntry> = emptyList(),
    val settings: AppSettings = AppSettings(),
    val selectedProvider: ProviderServiceType? = null,
    val messageSnackbar: String? = null
)
