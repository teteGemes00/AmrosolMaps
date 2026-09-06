package com.fantopo.metacrtl.core.data.repository

import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DefaultSettingsRepository(
    initialSettings: AppSettings = AppSettings()
) : SettingsRepository {

    private val _settings = MutableStateFlow(initialSettings)
    override fun getSettings(): Flow<AppSettings> = _settings.asStateFlow()

    override suspend fun updateSettings(settings: AppSettings) {
        _settings.value = settings
    }

    override suspend fun setFloatingMode(enabled: Boolean) {
        _settings.update { it.copy(isFloatingMode = enabled) }
    }

    override suspend fun setFusedMode(enabled: Boolean) {
        _settings.update { it.copy(isFusedMode = enabled) }
    }

    override suspend fun setRandomCoordinate(enabled: Boolean) {
        _settings.update { it.copy(isRandomCoordinate = enabled) }
    }

    override suspend fun setRandomAccuracy(enabled: Boolean, min: Float?, max: Float?) {
        _settings.update {
            it.copy(
                isRandomAccuracy = enabled,
                accuracyMin = min ?: it.accuracyMin,
                accuracyMax = max ?: it.accuracyMax
            )
        }
    }

    override suspend fun setRandomAltitude(enabled: Boolean, min: Float?, max: Float?) {
        _settings.update {
            it.copy(
                isRandomAltitude = enabled,
                altitudeMin = min ?: it.altitudeMin,
                altitudeMax = max ?: it.altitudeMax
            )
        }
    }

    override suspend fun setRandomBearing(enabled: Boolean) {
        _settings.update { it.copy(isRandomBearing = enabled) }
    }

    override suspend fun setRandomSpeed(enabled: Boolean, min: Float?, max: Float?) {
        _settings.update {
            it.copy(
                isRandomSpeed = enabled,
                speedMin = min ?: it.speedMin,
                speedMax = max ?: it.speedMax
            )
        }
    }

    override suspend fun setRefreshTimeMs(refreshTimeMs: Long) {
        val clamped = refreshTimeMs.coerceIn(AppSettings.REFRESH_TIME_MIN, AppSettings.REFRESH_TIME_MAX)
        _settings.update { it.copy(refreshTimeMs = clamped) }
    }

    override suspend fun toggleMapStyle(style: MapStyleMode) {
        _settings.update { current ->
            val updated = if (current.mapStyles.contains(style)) {
                current.mapStyles - style
            } else {
                current.mapStyles + style
            }
            current.copy(mapStyles = updated)
        }
    }

    override suspend fun setSelectedProvider(provider: ProviderServiceType?) {
        _settings.update { it.copy(selectedProvider = provider) }
    }
}
