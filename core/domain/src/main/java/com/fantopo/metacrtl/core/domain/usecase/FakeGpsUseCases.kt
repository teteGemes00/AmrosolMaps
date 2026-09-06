package com.fantopo.metacrtl.core.domain.usecase

import com.fantopo.metacrtl.core.data.manager.FakeGpsManager
import com.fantopo.metacrtl.core.data.repository.LocationRepository
import com.fantopo.metacrtl.core.model.FakeGpsState
import com.fantopo.metacrtl.core.model.HistoryEntry
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.flow.StateFlow

class GetFakeGpsStateUseCase(private val manager: FakeGpsManager) {
    operator fun invoke(): StateFlow<FakeGpsState> = manager.state
}

class SetPinnedLocationUseCase(
    private val manager: FakeGpsManager,
    private val locationRepository: LocationRepository
) {
    suspend operator fun invoke(point: LocationPoint, recordHistory: Boolean = true, historyName: String? = null) {
        manager.setPinnedLocation(point)
        if (recordHistory) {
            val entry = HistoryEntry(
                name = historyName?.ifBlank { HistoryEntry.DEFAULT_NAME } ?: HistoryEntry.DEFAULT_NAME,
                latitude = point.latitude,
                longitude = point.longitude
            )
            locationRepository.addHistoryEntry(entry)
        }
    }
}

class ToggleFakeGpsUseCase(private val manager: FakeGpsManager) {
    operator fun invoke(provider: ProviderServiceType? = null) = manager.toggleSimulation(provider)
}

class RefreshSimulatedLocationUseCase(private val manager: FakeGpsManager) {
    operator fun invoke() = manager.refreshLocation()
}

class StopFakeGpsUseCase(private val manager: FakeGpsManager) {
    operator fun invoke() = manager.stopSimulation()
}
