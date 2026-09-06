package com.fantopo.metacrtl.core.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ModelTest {

    @Test
    fun testLocationPointFormatCoordinates() {
        val point = LocationPoint(latitude = -6.208800, longitude = 106.845600)
        assertEquals("-6.208800, 106.845600", point.formatCoordinates())
    }

    @Test
    fun testSavedLocationCreation() {
        val saved = SavedLocation(name = "Monas", latitude = -6.1754, longitude = 106.8272)
        assertEquals("Monas", saved.name)
        assertEquals("-6.175400, 106.827200", saved.formatCoordinates())
    }

    @Test
    fun testFavoriteLocationCreation() {
        val favorite = FavoriteLocation(name = "Basecamp", latitude = -6.2000, longitude = 106.8000)
        assertEquals("Basecamp", favorite.name)
        assertEquals("-6.200000, 106.800000", favorite.formatCoordinates())
    }

    @Test
    fun testHistoryEntryDefaultName() {
        val history = HistoryEntry(latitude = -6.2088, longitude = 106.8456)
        assertEquals(HistoryEntry.DEFAULT_NAME, history.name)
    }

    @Test
    fun testAppSettingsDefaults() {
        val settings = AppSettings()
        assertEquals(5f, settings.accuracyMin, 0.001f)
        assertEquals(5f, settings.accuracyMax, 0.001f)
        assertEquals(0f, settings.altitudeMin, 0.001f)
        assertEquals(15f, settings.altitudeMax, 0.001f)
        assertEquals(1f, settings.speedMin, 0.001f)
        assertEquals(5f, settings.speedMax, 0.001f)
        assertEquals(1000L, settings.refreshTimeMs)
        assertTrue(settings.mapStyles.isEmpty())
    }
}
