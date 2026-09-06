package com.fantopo.metacrtl.core.domain.usecase

import com.fantopo.metacrtl.core.data.repository.SettingsRepository
import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.MapStyleMode
import kotlinx.coroutines.flow.Flow

class GetSettingsUseCase(private val repository: SettingsRepository) {
    operator fun invoke(): Flow<AppSettings> = repository.getSettings()
}

class UpdateSettingsUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(settings: AppSettings) = repository.updateSettings(settings)
}

class SetFloatingModeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.setFloatingMode(enabled)
}

class SetFusedModeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.setFusedMode(enabled)
}

class SetRandomCoordinateUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.setRandomCoordinate(enabled)
}

class SetRandomAccuracyUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean, min: Float? = null, max: Float? = null) {
        repository.setRandomAccuracy(enabled, min, max)
    }
}

class SetRandomAltitudeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean, min: Float? = null, max: Float? = null) {
        repository.setRandomAltitude(enabled, min, max)
    }
}

class SetRandomBearingUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean) = repository.setRandomBearing(enabled)
}

class SetRandomSpeedUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(enabled: Boolean, min: Float? = null, max: Float? = null) {
        repository.setRandomSpeed(enabled, min, max)
    }
}

class SetRefreshTimeUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(refreshTimeMs: Long) = repository.setRefreshTimeMs(refreshTimeMs)
}

class ToggleMapStyleUseCase(private val repository: SettingsRepository) {
    suspend operator fun invoke(style: MapStyleMode) = repository.toggleMapStyle(style)
}
