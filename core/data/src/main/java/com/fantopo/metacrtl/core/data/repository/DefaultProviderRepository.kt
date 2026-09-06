package com.fantopo.metacrtl.core.data.repository

import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultProviderRepository(
    initialProvider: ProviderServiceType? = null
) : ProviderRepository {

    private val _selectedProvider = MutableStateFlow(initialProvider)
    override fun getSelectedProvider(): Flow<ProviderServiceType?> = _selectedProvider.asStateFlow()

    override fun getAvailableProviders(): List<ProviderServiceType> {
        return ProviderServiceType.entries
    }

    override suspend fun selectProvider(provider: ProviderServiceType?) {
        _selectedProvider.value = provider
    }
}
