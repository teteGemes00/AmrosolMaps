package com.fantopo.metacrtl.core.model

import java.util.Locale
import java.util.UUID

data class HistoryEntry(
    val id: String = UUID.randomUUID().toString(),
    val name: String = DEFAULT_NAME,
    val latitude: Double,
    val longitude: Double,
    val timestamp: Long = System.currentTimeMillis()
) {
    fun formatCoordinates(): String {
        return String.format(Locale.US, "%.6f, %.6f", latitude, longitude)
    }

    companion object {
        const val DEFAULT_NAME = "Fantopo History"
    }
}
