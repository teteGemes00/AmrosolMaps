package com.fantopo.metacrtl.feature.map

import com.fantopo.metacrtl.core.data.manager.FakeGpsManager
import com.fantopo.metacrtl.core.data.repository.DefaultLocationRepository
import com.fantopo.metacrtl.core.data.repository.DefaultProviderRepository
import com.fantopo.metacrtl.core.data.repository.DefaultSettingsRepository
import com.fantopo.metacrtl.core.domain.usecase.*
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.core.model.ProviderServiceType
import com.fantopo.metacrtl.feature.map.model.DialogType
import com.fantopo.metacrtl.feature.map.viewmodel.MapViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MapViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private lateinit var locationRepo: DefaultLocationRepository
    private lateinit var settingsRepo: DefaultSettingsRepository
    private lateinit var providerRepo: DefaultProviderRepository
    private lateinit var fakeGpsManager: FakeGpsManager
    private lateinit var viewModel: MapViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        locationRepo = DefaultLocationRepository()
        settingsRepo = DefaultSettingsRepository()
        providerRepo = DefaultProviderRepository()
        fakeGpsManager = FakeGpsManager(settingsRepo, dispatcher = testDispatcher, externalScope = testScope)

        viewModel = MapViewModel(
            getSavedLocationsUseCase = GetSavedLocationsUseCase(locationRepo),
            saveLocationUseCase = SaveLocationUseCase(locationRepo),
            deleteSavedLocationUseCase = DeleteSavedLocationUseCase(locationRepo),
            updateSavedLocationUseCase = UpdateSavedLocationUseCase(locationRepo),
            getFavoriteLocationsUseCase = GetFavoriteLocationsUseCase(locationRepo),
            addFavoriteLocationUseCase = AddFavoriteLocationUseCase(locationRepo),
            deleteFavoriteLocationUseCase = DeleteFavoriteLocationUseCase(locationRepo),
            updateFavoriteLocationUseCase = UpdateFavoriteLocationUseCase(locationRepo),
            getHistoryUseCase = GetHistoryUseCase(locationRepo),
            addHistoryEntryUseCase = AddHistoryEntryUseCase(locationRepo),
            deleteHistoryEntryUseCase = DeleteHistoryEntryUseCase(locationRepo),
            clearHistoryUseCase = ClearHistoryUseCase(locationRepo),
            getSettingsUseCase = GetSettingsUseCase(settingsRepo),
            updateSettingsUseCase = UpdateSettingsUseCase(settingsRepo),
            toggleMapStyleUseCase = ToggleMapStyleUseCase(settingsRepo),
            getFakeGpsStateUseCase = GetFakeGpsStateUseCase(fakeGpsManager),
            setPinnedLocationUseCase = SetPinnedLocationUseCase(fakeGpsManager, locationRepo),
            toggleFakeGpsUseCase = ToggleFakeGpsUseCase(fakeGpsManager),
            refreshSimulatedLocationUseCase = RefreshSimulatedLocationUseCase(fakeGpsManager),
            stopFakeGpsUseCase = StopFakeGpsUseCase(fakeGpsManager),
            getSelectedProviderUseCase = GetSelectedProviderUseCase(providerRepo),
            selectProviderUseCase = SelectProviderUseCase(providerRepo, settingsRepo)
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun testInitialState() = runTest {
        advanceUntilIdle()
        val state = viewModel.uiState.value
        assertEquals(LocationPoint.DEFAULT.latitude, state.pinnedLocation.latitude, 0.0001)
        assertFalse(state.isGpsActive)
        assertFalse(state.isFullScreen)
        assertEquals(1.0f, state.zoomLevel, 0.01f)
        assertNull(state.activeDialog)
    }

    @Test
    fun testDialogNavigation() = runTest {
        viewModel.onOpenDialog(DialogType.SAVE)
        assertEquals(DialogType.SAVE, viewModel.uiState.value.activeDialog)

        viewModel.onDismissDialog()
        assertNull(viewModel.uiState.value.activeDialog)
    }

    @Test
    fun testZoomCycle() = runTest {
        assertEquals(1.0f, viewModel.uiState.value.zoomLevel, 0.01f)
        viewModel.onToggleZoom()
        assertEquals(1.5f, viewModel.uiState.value.zoomLevel, 0.01f)
        viewModel.onToggleZoom()
        assertEquals(2.0f, viewModel.uiState.value.zoomLevel, 0.01f)
        viewModel.onToggleZoom()
        assertEquals(1.0f, viewModel.uiState.value.zoomLevel, 0.01f)
    }

    @Test
    fun testFullScreenToggle() = runTest {
        assertFalse(viewModel.uiState.value.isFullScreen)
        viewModel.onToggleFullScreen()
        assertTrue(viewModel.uiState.value.isFullScreen)
        viewModel.onToggleFullScreen()
        assertFalse(viewModel.uiState.value.isFullScreen)
    }

    @Test
    fun testPinAndMoveLocation() = runTest {
        viewModel.onMoveToLocation(-6.917464, 107.619123, "Bandung Center")
        advanceUntilIdle()

        assertEquals(-6.917464, viewModel.uiState.value.pinnedLocation.latitude, 0.0001)
        assertEquals(1, viewModel.uiState.value.historyEntries.size)
        assertEquals("Bandung Center", viewModel.uiState.value.historyEntries.first().name)
    }

    @Test
    fun testSaveAndFavoriteWorkflows() = runTest {
        // Save
        viewModel.onSaveLocation("Mall", -6.2, 106.8)
        advanceUntilIdle()
        assertEquals(1, viewModel.uiState.value.savedLocations.size)
        assertEquals("Mall", viewModel.uiState.value.savedLocations.first().name)

        // Favorite
        viewModel.onAddFavorite("Work", -6.3, 106.9)
        advanceUntilIdle()
        assertEquals(1, viewModel.uiState.value.favoriteLocations.size)
        assertEquals("Work", viewModel.uiState.value.favoriteLocations.first().name)
    }

    @Test
    fun testProviderSelectionAndSettings() = runTest {
        viewModel.onSelectProvider(ProviderServiceType.GRAB)
        viewModel.onToggleMapStyle(MapStyleMode.NIGHT)
        advanceUntilIdle()

        assertEquals(ProviderServiceType.GRAB, viewModel.uiState.value.selectedProvider)
        assertTrue(viewModel.uiState.value.settings.mapStyles.contains(MapStyleMode.NIGHT))
    }

    @Test
    fun testPlayStopGps() = runTest {
        viewModel.onTogglePlayGps()
        advanceUntilIdle()
        assertTrue(viewModel.uiState.value.isGpsActive)

        viewModel.onStopGps()
        advanceUntilIdle()
        assertFalse(viewModel.uiState.value.isGpsActive)
    }
}
