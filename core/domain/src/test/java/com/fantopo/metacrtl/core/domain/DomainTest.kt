package com.fantopo.metacrtl.core.domain

import com.fantopo.metacrtl.core.data.manager.FakeGpsManager
import com.fantopo.metacrtl.core.data.repository.DefaultLocationRepository
import com.fantopo.metacrtl.core.data.repository.DefaultProviderRepository
import com.fantopo.metacrtl.core.data.repository.DefaultSettingsRepository
import com.fantopo.metacrtl.core.domain.usecase.AddFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.AddHistoryEntryUseCase
import com.fantopo.metacrtl.core.domain.usecase.ClearHistoryUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteFavoriteLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteHistoryEntryUseCase
import com.fantopo.metacrtl.core.domain.usecase.DeleteSavedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetFavoriteLocationsUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetHistoryUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetSavedLocationsUseCase
import com.fantopo.metacrtl.core.domain.usecase.GetSettingsUseCase
import com.fantopo.metacrtl.core.domain.usecase.SaveLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.SelectProviderUseCase
import com.fantopo.metacrtl.core.domain.usecase.SetFloatingModeUseCase
import com.fantopo.metacrtl.core.domain.usecase.SetPinnedLocationUseCase
import com.fantopo.metacrtl.core.domain.usecase.SetRandomAccuracyUseCase
import com.fantopo.metacrtl.core.domain.usecase.SetRefreshTimeUseCase
import com.fantopo.metacrtl.core.domain.usecase.ToggleFakeGpsUseCase
import com.fantopo.metacrtl.core.domain.usecase.ToggleMapStyleUseCase
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DomainTest {

    @Test
    fun testLocationAndHistoryUseCases() = runTest {
        val locationRepo = DefaultLocationRepository()
        val saveLocation = SaveLocationUseCase(locationRepo)
        val getSaved = GetSavedLocationsUseCase(locationRepo)
        val deleteSaved = DeleteSavedLocationUseCase(locationRepo)

        saveLocation("Favorite Beach", -8.409518, 115.188919)
        var savedList = getSaved().first()
        assertEquals(1, savedList.size)
        assertEquals("Favorite Beach", savedList.first().name)

        deleteSaved(savedList.first().id)
        savedList = getSaved().first()
        assertTrue(savedList.isEmpty())

        val addFav = AddFavoriteLocationUseCase(locationRepo)
        val getFav = GetFavoriteLocationsUseCase(locationRepo)
        val delFav = DeleteFavoriteLocationUseCase(locationRepo)

        addFav("HQ", -6.2, 106.8)
        var favList = getFav().first()
        assertEquals(1, favList.size)
        delFav(favList.first().id)
        favList = getFav().first()
        assertTrue(favList.isEmpty())

        val addHist = AddHistoryEntryUseCase(locationRepo)
        val getHist = GetHistoryUseCase(locationRepo)
        val delHist = DeleteHistoryEntryUseCase(locationRepo)
        val clearHist = ClearHistoryUseCase(locationRepo)

        addHist(null, -6.1, 106.7)
        var histList = getHist().first()
        assertEquals(1, histList.size)
        assertEquals("Fantopo History", histList.first().name)

        clearHist()
        histList = getHist().first()
        assertTrue(histList.isEmpty())
    }

    @Test
    fun testPinnedLocationAndHistoryAutoRecording() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val testScope = TestScope(testDispatcher)
        val locationRepo = DefaultLocationRepository()
        val settingsRepo = DefaultSettingsRepository()
        val manager = FakeGpsManager(settingsRepo, dispatcher = testDispatcher, externalScope = testScope)

        val setPin = SetPinnedLocationUseCase(manager, locationRepo)
        val getHist = GetHistoryUseCase(locationRepo)

        val target = LocationPoint(latitude = -6.917464, longitude = 107.619123)
        setPin(target, recordHistory = true, historyName = "Bandung Point")

        assertEquals(target.latitude, manager.state.value.pinnedLocation.latitude, 0.0001)
        val history = getHist().first()
        assertEquals(1, history.size)
        assertEquals("Bandung Point", history.first().name)
    }

    @Test
    fun testSettingsAndProviderUseCases() = runTest {
        val settingsRepo = DefaultSettingsRepository()
        val providerRepo = DefaultProviderRepository()

        val getSettings = GetSettingsUseCase(settingsRepo)
        val setFloating = SetFloatingModeUseCase(settingsRepo)
        val setRefresh = SetRefreshTimeUseCase(settingsRepo)
        val setAccuracy = SetRandomAccuracyUseCase(settingsRepo)
        val toggleStyle = ToggleMapStyleUseCase(settingsRepo)
        val selectProvider = SelectProviderUseCase(providerRepo, settingsRepo)

        setFloating(true)
        setRefresh(800L)
        setAccuracy(true, 1f, 3f)
        toggleStyle(MapStyleMode.NIGHT)
        toggleStyle(MapStyleMode.TRAFFIC)
        selectProvider(ProviderServiceType.GRAB)

        val current = getSettings().first()
        assertTrue(current.isFloatingMode)
        assertEquals(800L, current.refreshTimeMs)
        assertTrue(current.isRandomAccuracy)
        assertEquals(1f, current.accuracyMin, 0.001f)
        assertEquals(3f, current.accuracyMax, 0.001f)
        assertTrue(current.mapStyles.contains(MapStyleMode.NIGHT))
        assertTrue(current.mapStyles.contains(MapStyleMode.TRAFFIC))
        assertEquals(ProviderServiceType.GRAB, current.selectedProvider)
    }
}
