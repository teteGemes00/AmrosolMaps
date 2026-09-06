package com.fantopo.metacrtl.core.domain.usecase

import com.fantopo.metacrtl.core.data.repository.ProviderRepository
import com.fantopo.metacrtl.core.data.repository.SettingsRepository
import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.flow.Flow

class GetProvidersUseCase(private val repository: ProviderRepository) {
    operator fun invoke(): List<ProviderServiceType> = repository.getAvailableProviders()
}

class GetSelectedProviderUseCase(private val repository: ProviderRepository) {
    operator fun invoke(): Flow<ProviderServiceType?> = repository.getSelectedProvider()
}

class SelectProviderUseCase(
    private val providerRepository: ProviderRepository,
    private val settingsRepository: SettingsRepository
) {
    suspend operator fun invoke(provider: ProviderServiceType?) {
        providerRepository.selectProvider(provider)
        settingsRepository.setSelectedProvider(provider)
    }
}
