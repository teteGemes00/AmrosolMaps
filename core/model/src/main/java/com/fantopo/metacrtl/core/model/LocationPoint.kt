package com.fantopo.metacrtl.core.model

import java.util.Locale

data class LocationPoint(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double = 0.0,
    val bearing: Float = 0f,
    val speed: Float = 0f,
    val accuracy: Float = 5f
) {
    fun formatCoordinates(): String {
        return String.format(Locale.US, "%.6f, %.6f", latitude, longitude)
    }

    companion object {
        val DEFAULT = LocationPoint(
            latitude = -6.2088,
            longitude = 106.8456, // Jakarta center coordinate as sensible default
            altitude = 12.0,
            bearing = 0f,
            speed = 0f,
            accuracy = 5f
        )
    }
}
