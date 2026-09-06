package com.fantopo.metacrtl.core.model

import java.util.Locale
import java.util.UUID

data class FavoriteLocation(
    val id: String = UUID.randomUUID().toString(),
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val createdAt: Long = System.currentTimeMillis()
) {
    fun formatCoordinates(): String {
        return String.format(Locale.US, "%.6f, %.6f", latitude, longitude)
    }
}
