package com.fantopo.metacrtl.core.data

import com.fantopo.metacrtl.core.data.manager.FakeGpsManager
import com.fantopo.metacrtl.core.data.manager.LocationRandomizer
import com.fantopo.metacrtl.core.data.repository.DefaultLocationRepository
import com.fantopo.metacrtl.core.data.repository.DefaultProviderRepository
import com.fantopo.metacrtl.core.data.repository.DefaultSettingsRepository
import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.HistoryEntry
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.core.model.ProviderServiceType
import com.fantopo.metacrtl.core.model.SavedLocation
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DataTest {

    @Test
    fun testLocationRepositoryOperations() = runTest {
        val repo = DefaultLocationRepository()

        // Saved locations
        val saved1 = SavedLocation(id = "1", name = "Point A", latitude = -6.1, longitude = 106.8)
        repo.saveLocation(saved1)
        var savedList = repo.getSavedLocations().first()
        assertEquals(1, savedList.size)
        assertEquals("Point A", savedList.first().name)

        val saved1Edited = saved1.copy(name = "Point A Renamed")
        repo.updateSavedLocation(saved1Edited)
        savedList = repo.getSavedLocations().first()
        assertEquals("Point A Renamed", savedList.first().name)

        repo.deleteSavedLocation("1")
        savedList = repo.getSavedLocations().first()
        assertTrue(savedList.isEmpty())

        // Favorites
        val fav1 = FavoriteLocation(id = "fav1", name = "Home", latitude = -6.2, longitude = 106.9)
        repo.addFavoriteLocation(fav1)
        var favList = repo.getFavoriteLocations().first()
        assertEquals(1, favList.size)

        repo.deleteFavoriteLocation("fav1")
        favList = repo.getFavoriteLocations().first()
        assertTrue(favList.isEmpty())

        // History
        val hist1 = HistoryEntry(id = "h1", name = "Pin 1", latitude = -6.3, longitude = 106.7)
        repo.addHistoryEntry(hist1)
        var histList = repo.getHistory().first()
        assertEquals(1, histList.size)

        repo.clearHistory()
        histList = repo.getHistory().first()
        assertTrue(histList.isEmpty())
    }

    @Test
    fun testSettingsRepositoryOperations() = runTest {
        val repo = DefaultSettingsRepository()

        repo.setFloatingMode(true)
        repo.setFusedMode(false)
        repo.setRandomAccuracy(true, min = 2f, max = 4f)
        repo.setRandomAltitude(true, min = 10f, max = 50f)
        repo.setRandomSpeed(true, min = 10f, max = 30f)
        repo.setRandomBearing(true)
        repo.setRandomCoordinate(true)
        repo.setRefreshTimeMs(500L)
        repo.toggleMapStyle(MapStyleMode.HYBRID)
        repo.setSelectedProvider(ProviderServiceType.GRAB)

        val settings = repo.getSettings().first()
        assertTrue(settings.isFloatingMode)
        assertFalse(settings.isFusedMode)
        assertTrue(settings.isRandomAccuracy)
        assertEquals(2f, settings.accuracyMin, 0.001f)
        assertEquals(4f, settings.accuracyMax, 0.001f)
        assertTrue(settings.isRandomAltitude)
        assertEquals(10f, settings.altitudeMin, 0.001f)
        assertEquals(50f, settings.altitudeMax, 0.001f)
        assertTrue(settings.isRandomSpeed)
        assertEquals(10f, settings.speedMin, 0.001f)
        assertEquals(30f, settings.speedMax, 0.001f)
        assertTrue(settings.isRandomBearing)
        assertTrue(settings.isRandomCoordinate)
        assertEquals(500L, settings.refreshTimeMs)
        assertTrue(settings.mapStyles.contains(MapStyleMode.HYBRID))
        assertEquals(ProviderServiceType.GRAB, settings.selectedProvider)
    }

    @Test
    fun testLocationRandomizerLimits() {
        val randomizer = LocationRandomizer()
        val base = LocationPoint(latitude = -6.2088, longitude = 106.8456, altitude = 0.0, bearing = 0f, speed = 0f, accuracy = 5f)

        val settings = AppSettings(
            isRandomCoordinate = true,
            isRandomAccuracy = true,
            accuracyMin = 2f,
            accuracyMax = 4f,
            isRandomAltitude = true,
            altitudeMin = 10f,
            altitudeMax = 30f,
            isRandomBearing = true,
            isRandomSpeed = true,
            speedMin = 5f,
            speedMax = 20f
        )

        for (i in 0 until 50) {
            val randomized = randomizer.randomize(base, settings)
            assertTrue("Accuracy ${randomized.accuracy} must be in range 2..4", randomized.accuracy in 2f..4f)
            assertTrue("Altitude ${randomized.altitude} must be in range 10..30", randomized.altitude in 10.0..30.0)
            assertTrue("Bearing ${randomized.bearing} must be in range 0..360", randomized.bearing in 0f..360f)
            assertTrue("Speed ${randomized.speed} must be in range 5..20", randomized.speed in 5f..20f)
            assertTrue("Lat should be close to base", Math.abs(randomized.latitude - base.latitude) < 0.01)
            assertTrue("Lng should be close to base", Math.abs(randomized.longitude - base.longitude) < 0.01)
        }
    }

    @Test
    fun testFakeGpsManagerSimulation() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val testScope = TestScope(testDispatcher)
        val settingsRepo = DefaultSettingsRepository(AppSettings(refreshTimeMs = 200L))
        val manager = FakeGpsManager(
            settingsRepository = settingsRepo,
            dispatcher = testDispatcher,
            externalScope = testScope
        )

        val customPin = LocationPoint(latitude = -7.250445, longitude = 112.768845)
        manager.setPinnedLocation(customPin)
        assertEquals(customPin.latitude, manager.state.value.pinnedLocation.latitude, 0.000001)

        manager.startSimulation(ProviderServiceType.GOJEK)
        assertTrue(manager.state.value.isActive)
        assertEquals(ProviderServiceType.GOJEK, manager.state.value.activeProvider)

        advanceTimeBy(650L)
        assertTrue(manager.state.value.refreshCount >= 3)

        manager.stopSimulation()
        assertFalse(manager.state.value.isActive)
    }

    @Test
    fun testProviderRepository() = runTest {
        val repo = DefaultProviderRepository()
        assertEquals(3, repo.getAvailableProviders().size)
        repo.selectProvider(ProviderServiceType.SHOPEEFOOD)
        assertEquals(ProviderServiceType.SHOPEEFOOD, repo.getSelectedProvider().first())
    }
}
