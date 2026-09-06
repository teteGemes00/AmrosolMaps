package com.fantopo.metacrtl.core.data.manager

import com.fantopo.metacrtl.core.data.repository.SettingsRepository
import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.FakeGpsState
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class FakeGpsManager(
    private val settingsRepository: SettingsRepository,
    private val randomizer: LocationRandomizer = LocationRandomizer(),
    private val dispatcher: CoroutineDispatcher = Dispatchers.Default,
    private val externalScope: CoroutineScope = CoroutineScope(dispatcher)
) {
    private val _state = MutableStateFlow(
        FakeGpsState(
            isActive = false,
            pinnedLocation = LocationPoint.DEFAULT,
            simulatedLocation = LocationPoint.DEFAULT
        )
    )
    val state: StateFlow<FakeGpsState> = _state.asStateFlow()

    private var simulationJob: Job? = null

    fun setPinnedLocation(point: LocationPoint) {
        _state.update { current ->
            current.copy(
                pinnedLocation = point,
                simulatedLocation = point
            )
        }
        if (_state.value.isActive) {
            refreshLocation()
        }
    }

    fun startSimulation(provider: ProviderServiceType? = null) {
        _state.update {
            it.copy(
                isActive = true,
                activeProvider = provider
            )
        }
        restartSimulationLoop()
    }

    fun stopSimulation() {
        simulationJob?.cancel()
        simulationJob = null
        _state.update { it.copy(isActive = false) }
    }

    fun toggleSimulation(provider: ProviderServiceType? = null) {
        if (_state.value.isActive) {
            stopSimulation()
        } else {
            startSimulation(provider)
        }
    }

    /**
     * Refreshes the simulated location for current pinned marker immediately.
     */
    fun refreshLocation() {
        externalScope.launch(dispatcher) {
            val settings = settingsRepository.getSettings().first()
            val base = _state.value.pinnedLocation
            val simulated = randomizer.randomize(base, settings)
            _state.update { current ->
                current.copy(
                    simulatedLocation = simulated,
                    lastUpdatedAt = System.currentTimeMillis(),
                    refreshCount = current.refreshCount + 1
                )
            }
        }
    }

    private fun restartSimulationLoop() {
        simulationJob?.cancel()
        simulationJob = externalScope.launch(dispatcher) {
            while (isActive) {
                val settings = settingsRepository.getSettings().first()
                val base = _state.value.pinnedLocation
                val simulated = randomizer.randomize(base, settings)

                _state.update { current ->
                    current.copy(
                        simulatedLocation = simulated,
                        lastUpdatedAt = System.currentTimeMillis(),
                        refreshCount = current.refreshCount + 1
                    )
                }

                val delayMs = if (settings.refreshTimeMs <= 0L) {
                    100L // Safe minimum delay to prevent busy looping
                } else {
                    settings.refreshTimeMs
                }
                delay(delayMs)
            }
        }
    }
}
