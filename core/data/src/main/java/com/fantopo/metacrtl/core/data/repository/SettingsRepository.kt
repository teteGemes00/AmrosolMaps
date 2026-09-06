package com.fantopo.metacrtl.core.data.repository

import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getSettings(): Flow<AppSettings>
    suspend fun updateSettings(settings: AppSettings)
    suspend fun setFloatingMode(enabled: Boolean)
    suspend fun setFusedMode(enabled: Boolean)
    suspend fun setRandomCoordinate(enabled: Boolean)
    suspend fun setRandomAccuracy(enabled: Boolean, min: Float? = null, max: Float? = null)
    suspend fun setRandomAltitude(enabled: Boolean, min: Float? = null, max: Float? = null)
    suspend fun setRandomBearing(enabled: Boolean)
    suspend fun setRandomSpeed(enabled: Boolean, min: Float? = null, max: Float? = null)
    suspend fun setRefreshTimeMs(refreshTimeMs: Long)
    suspend fun toggleMapStyle(style: MapStyleMode)
    suspend fun setSelectedProvider(provider: ProviderServiceType?)
}
