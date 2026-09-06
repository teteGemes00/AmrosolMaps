package com.fantopo.metacrtl.feature.map.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fantopo.metacrtl.core.domain.usecase.AddFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.AddHistoryEntryUseCase
import com.fantopo.metacrtl.core.domain.usecase.ClearHistoryUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteHistoryEntryUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteSavedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetFakeGpsStateUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetFavoriteLocationsUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetHistoryUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetSavedLocationsUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetSelectedProviderUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetSettingsUseCase
import com.fantopo.metacrtl.core.domain.usecase.RefreshSimulatedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.SaveLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.SelectProviderUseCase
import com.fantopo.metacrtl.core.domain.usecase.SetPinnedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.StopFakeGpsUseCase
import com.fantopo.metacrtl.core.domain.usecase.ToggleFakeGpsUseCase
import com.fantopo.metacrtl.core.domain.usecase.ToggleMapStyleUseCase
import com.fantopo.metacrtl.core.domain.usecase.UpdateFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.UpdateSavedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.UpdateSettingsUseCase
import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.core.model.ProviderServiceType
import com.fantopo.metacrtl.core.model.SavedLocation
import com.fantopo.metacrtl.feature.map.model.DialogType
import com.fantopo.metacrtl.feature.map.model.MapUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapViewModel(
    private val getSavedLocationsUseCase: GetSavedLocationsUseCase,
    private val saveLocationUseCase: SaveLocationUseCase,
    private val deleteSavedLocationUseCase: DeleteSavedLocationUseCase,
    private val updateSavedLocationUseCase: UpdateSavedLocationUseCase,
    private val getFavoriteLocationsUseCase: GetFavoriteLocationsUseCase,
    private val addFavoriteLocationUseCase: AddFavoriteLocationUseCase,
    private val deleteFavoriteLocationUseCase: DeleteFavoriteLocationUseCase,
    private val updateFavoriteLocationUseCase: UpdateFavoriteLocationUseCase,
    private val getHistoryUseCase: GetHistoryUseCase,
    private val addHistoryEntryUseCase: AddHistoryEntryUseCase,
    private val deleteHistoryEntryUseCase: DeleteHistoryEntryUseCase,
    private val clearHistoryUseCase: ClearHistoryUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val updateSettingsUseCase: UpdateSettingsUseCase,
    private val toggleMapStyleUseCase: ToggleMapStyleUseCase,
    private val getFakeGpsStateUseCase: GetFakeGpsStateUseCase,
    private val setPinnedLocationUseCase: SetPinnedLocationUseCase,
    private val toggleFakeGpsUseCase: ToggleFakeGpsUseCase,
    private val refreshSimulatedLocationUseCase: RefreshSimulatedLocationUseCase,
    private val stopFakeGpsUseCase: StopFakeGpsUseCase,
    private val getSelectedProviderUseCase: GetSelectedProviderUseCase,
    private val selectProviderUseCase: SelectProviderUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            getSavedLocationsUseCase().collectLatest { savedList ->
                _uiState.update { it.copy(savedLocations = savedList) }
            }
        }
        viewModelScope.launch {
            getFavoriteLocationsUseCase().collectLatest { favList ->
                _uiState.update { it.copy(favoriteLocations = favList) }
            }
        }
        viewModelScope.launch {
            getHistoryUseCase().collectLatest { histList ->
                _uiState.update { it.copy(historyEntries = histList) }
            }
        }
        viewModelScope.launch {
            getSettingsUseCase().collectLatest { settings ->
                _uiState.update { it.copy(settings = settings) }
            }
        }
        viewModelScope.launch {
            getSelectedProviderUseCase().collectLatest { provider ->
                _uiState.update { it.copy(selectedProvider = provider) }
            }
        }
        viewModelScope.launch {
            getFakeGpsStateUseCase().collectLatest { fakeGps ->
                _uiState.update {
                    it.copy(
                        isGpsActive = fakeGps.isActive,
                        pinnedLocation = fakeGps.pinnedLocation,
                        simulatedLocation = fakeGps.simulatedLocation
                    )
                }
            }
        }
    }

    fun onPinLocation(point: LocationPoint, name: String? = null) {
        viewModelScope.launch {
            setPinnedLocationUseCase(point, recordHistory = true, historyName = name)
        }
    }

    fun onMoveToLocation(latitude: Double, longitude: Double, name: String? = null) {
        val newPoint = _uiState.value.pinnedLocation.copy(
            latitude = latitude,
            longitude = longitude
        )
        onPinLocation(newPoint, name)
    }

    fun onTogglePlayGps() {
        toggleFakeGpsUseCase(_uiState.value.selectedProvider)
    }

    fun onStopGps() {
        stopFakeGpsUseCase()
    }

    fun onRefreshLocation() {
        refreshSimulatedLocationUseCase()
    }

    fun onCenterLocation() {
        // Re-center pin to default or simulated point
        val currentSim = _uiState.value.simulatedLocation
        onPinLocation(currentSim, "Centered Pin")
    }

    fun onToggleZoom() {
        _uiState.update { current ->
            // Cycle zoom levels: 1.0f -> 1.5f -> 2.0f -> 1.0f
            val nextZoom = when {
                current.zoomLevel < 1.4f -> 1.5f
                current.zoomLevel < 1.9f -> 2.0f
                else -> 1.0f
            }
            current.copy(zoomLevel = nextZoom)
        }
    }

    fun onToggleFullScreen() {
        _uiState.update { it.copy(isFullScreen = !it.isFullScreen) }
    }

    fun onDeleteLocation() {
        // Reset marker back to default
        viewModelScope.launch {
            setPinnedLocationUseCase(LocationPoint.DEFAULT, recordHistory = false)
        }
    }

    fun onOpenDialog(dialog: DialogType) {
        _uiState.update { it.copy(activeDialog = dialog) }
    }

    fun onDismissDialog() {
        _uiState.update { it.copy(activeDialog = null) }
    }

    fun onToggleFloatingOverlay() {
        _uiState.update { it.copy(isFloatingExpanded = !it.isFloatingExpanded) }
    }

    // Save dialog actions
    fun onSaveLocation(name: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            saveLocationUseCase(name, latitude, longitude)
            onDismissDialog()
        }
    }

    fun onDeleteSavedLocation(id: String) {
        viewModelScope.launch {
            deleteSavedLocationUseCase(id)
        }
    }

    fun onUpdateSavedLocation(location: SavedLocation) {
        viewModelScope.launch {
            updateSavedLocationUseCase(location)
        }
    }

    // Favorite dialog actions
    fun onAddFavorite(name: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            addFavoriteLocationUseCase(name, latitude, longitude)
        }
    }

    fun onDeleteFavorite(id: String) {
        viewModelScope.launch {
            deleteFavoriteLocationUseCase(id)
        }
    }

    fun onUpdateFavorite(favorite: FavoriteLocation) {
        viewModelScope.launch {
            updateFavoriteLocationUseCase(favorite)
        }
    }

    // History actions
    fun onDeleteHistory(id: String) {
        viewModelScope.launch {
            deleteHistoryEntryUseCase(id)
        }
    }

    fun onClearHistory() {
        viewModelScope.launch {
            clearHistoryUseCase()
        }
    }

    // Settings actions
    fun onUpdateSettings(settings: AppSettings) {
        viewModelScope.launch {
            updateSettingsUseCase(settings)
        }
    }

    fun onToggleMapStyle(style: MapStyleMode) {
        viewModelScope.launch {
            toggleMapStyleUseCase(style)
        }
    }

    // Provider actions
    fun onSelectProvider(provider: ProviderServiceType?) {
        viewModelScope.launch {
            selectProviderUseCase(provider)
        }
    }

    fun onDismissMessage() {
        _uiState.update { it.copy(messageSnackbar = null) }
    }
}
