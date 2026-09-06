package com.fantopo.metacrtl.core.data.manager

import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.LocationPoint
import kotlin.math.cos
import kotlin.random.Random

class LocationRandomizer(
    private val random: Random = Random.Default
) {
    /**
     * Applies randomization logic based on the provided settings.
     * Realistic random coordinate offset generates subtle movement jitter (approx 1-6 meters).
     */
    fun randomize(basePoint: LocationPoint, settings: AppSettings): LocationPoint {
        var lat = basePoint.latitude
        var lng = basePoint.longitude
        var alt = basePoint.altitude
        var bearing = basePoint.bearing
        var speed = basePoint.speed
        var accuracy = basePoint.accuracy

        if (settings.isRandomCoordinate) {
            // Earth radius ~ 6,371,000 meters.
            // 1 meter in latitude ~ 0.00000899 degrees.
            // Generate realistic jitter between -3m and +3m
            val deltaLatMeters = (random.nextDouble() * 6.0) - 3.0
            val deltaLngMeters = (random.nextDouble() * 6.0) - 3.0

            val latOffset = deltaLatMeters / 111111.0
            val lngOffset = deltaLngMeters / (111111.0 * cos(Math.toRadians(lat)).coerceAtLeast(0.0001))

            lat += latOffset
            lng += lngOffset
        }

        if (settings.isRandomAccuracy) {
            val min = settings.accuracyMin.coerceAtLeast(AppSettings.ACCURACY_ALLOWED_MIN)
            val max = settings.accuracyMax.coerceAtMost(AppSettings.ACCURACY_ALLOWED_MAX)
            accuracy = if (max > min) {
                min + random.nextFloat() * (max - min)
            } else {
                min
            }
        }

        if (settings.isRandomAltitude) {
            val min = settings.altitudeMin.coerceAtLeast(AppSettings.ALTITUDE_ALLOWED_MIN)
            val max = settings.altitudeMax.coerceAtMost(AppSettings.ALTITUDE_ALLOWED_MAX)
            alt = if (max > min) {
                (min + random.nextFloat() * (max - min)).toDouble()
            } else {
                min.toDouble()
            }
        }

        if (settings.isRandomBearing) {
            bearing = random.nextFloat() * 360f
        }

        if (settings.isRandomSpeed) {
            val min = settings.speedMin.coerceAtLeast(AppSettings.SPEED_ALLOWED_MIN)
            val max = settings.speedMax.coerceAtMost(AppSettings.SPEED_ALLOWED_MAX)
            speed = if (max > min) {
                min + random.nextFloat() * (max - min)
            } else {
                min
            }
        }

        return LocationPoint(
            latitude = lat,
            longitude = lng,
            altitude = alt,
            bearing = bearing,
            speed = speed,
            accuracy = accuracy
        )
    }
}
