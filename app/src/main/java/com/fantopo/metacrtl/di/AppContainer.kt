package com.fantopo.metacrtl.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fantopo.metacrtl.core.data.manager.FakeGpsManager
import com.fantopo.metacrtl.core.data.manager.LocationRandomizer
import com.fantopo.metacrtl.core.data.repository.DefaultLocationRepository
import com.fantopo.metacrtl.core.data.repository.DefaultProviderRepository
import com.fantopo.metacrtl.core.data.repository.DefaultSettingsRepository
import com.fantopo.metacrtl.core.data.repository.LocationRepository
import com.fantopo.metacrtl.core.data.repository.ProviderRepository
import com.fantopo.metacrtl.core.data.repository.SettingsRepository
import com.fantopo.metacrtl.core.domain.usecase.AddFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.AddHistoryEntryUseCase
import com.fantopo.metacrtl.core.domain.usecase.ClearHistoryUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteHistoryEntryUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteSavedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetFakeGpsStateUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetFavoriteLocationsUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetHistoryUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetProvidersUseCase
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
import com.fantopo.metacrtl.feature.map.viewmodel.MapViewModel

class AppContainer(private val context: Context) {

    val locationRepository: LocationRepository by lazy {
        DefaultLocationRepository()
    }

    val settingsRepository: SettingsRepository by lazy {
        DefaultSettingsRepository()
    }

    val providerRepository: ProviderRepository by lazy {
        DefaultProviderRepository()
    }

    val locationRandomizer: LocationRandomizer by lazy {
        LocationRandomizer()
    }

    val fakeGpsManager: FakeGpsManager by lazy {
        FakeGpsManager(
            settingsRepository = settingsRepository,
            randomizer = locationRandomizer
        )
    }

    // Use cases
    val getSavedLocationsUseCase by lazy { GetSavedLocationsUseCase(locationRepository) }
    val saveLocationUseCase by lazy { SaveLocationUseCase(locationRepository) }
    val deleteSavedLocationUseCase by lazy { DeleteSavedLocationUseCase(locationRepository) }
    val updateSavedLocationUseCase by lazy { UpdateSavedLocationUseCase(locationRepository) }

    val getFavoriteLocationsUseCase by lazy { GetFavoriteLocationsUseCase(locationRepository) }
    val addFavoriteLocationUseCase by lazy { AddFavoriteLocationUseCase(locationRepository) }
    val deleteFavoriteLocationUseCase by lazy { DeleteFavoriteLocationUseCase(locationRepository) }
    val updateFavoriteLocationUseCase by lazy { UpdateFavoriteLocationUseCase(locationRepository) }

    val getHistoryUseCase by lazy { GetHistoryUseCase(locationRepository) }
    val addHistoryEntryUseCase by lazy { AddHistoryEntryUseCase(locationRepository) }
    val deleteHistoryEntryUseCase by lazy { DeleteHistoryEntryUseCase(locationRepository) }
    val clearHistoryUseCase by lazy { ClearHistoryUseCase(locationRepository) }

    val getSettingsUseCase by lazy { GetSettingsUseCase(settingsRepository) }
    val updateSettingsUseCase by lazy { UpdateSettingsUseCase(settingsRepository) }
    val toggleMapStyleUseCase by lazy { ToggleMapStyleUseCase(settingsRepository) }

    val getFakeGpsStateUseCase by lazy { GetFakeGpsStateUseCase(fakeGpsManager) }
    val setPinnedLocationUseCase by lazy { SetPinnedLocationUseCase(fakeGpsManager, locationRepository) }
    val toggleFakeGpsUseCase by lazy { ToggleFakeGpsUseCase(fakeGpsManager) }
    val refreshSimulatedLocationUseCase by lazy { RefreshSimulatedLocationUseCase(fakeGpsManager) }
    val stopFakeGpsUseCase by lazy { StopFakeGpsUseCase(fakeGpsManager) }

    val getProvidersUseCase by lazy { GetProvidersUseCase(providerRepository) }
    val getSelectedProviderUseCase by lazy { GetSelectedProviderUseCase(providerRepository) }
    val selectProviderUseCase by lazy { SelectProviderUseCase(providerRepository, settingsRepository) }

    fun provideMapViewModelFactory(): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                    return MapViewModel(
                        getSavedLocationsUseCase = getSavedLocationsUseCase,
                        saveLocationUseCase = saveLocationUseCase,
                        deleteSavedLocationUseCase = deleteSavedLocationUseCase,
                        updateSavedLocationUseCase = updateSavedLocationUseCase,
                        getFavoriteLocationsUseCase = getFavoriteLocationsUseCase,
                        addFavoriteLocationUseCase = addFavoriteLocationUseCase,
                        deleteFavoriteLocationUseCase = deleteFavoriteLocationUseCase,
                        updateFavoriteLocationUseCase = updateFavoriteLocationUseCase,
                        getHistoryUseCase = getHistoryUseCase,
                        addHistoryEntryUseCase = addHistoryEntryUseCase,
                        deleteHistoryEntryUseCase = deleteHistoryEntryUseCase,
                        clearHistoryUseCase = clearHistoryUseCase,
                        getSettingsUseCase = getSettingsUseCase,
                        updateSettingsUseCase = updateSettingsUseCase,
                        toggleMapStyleUseCase = toggleMapStyleUseCase,
                        getFakeGpsStateUseCase = getFakeGpsStateUseCase,
                        setPinnedLocationUseCase = setPinnedLocationUseCase,
                        toggleFakeGpsUseCase = toggleFakeGpsUseCase,
                        refreshSimulatedLocationUseCase = refreshSimulatedLocationUseCase,
                        stopFakeGpsUseCase = stopFakeGpsUseCase,
                        getSelectedProviderUseCase = getSelectedProviderUseCase,
                        selectProviderUseCase = selectProviderUseCase
                    ) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        }
    }
}
