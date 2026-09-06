package com.fantopo.metacrtl.core.data.repository

import com.fantopo.metacrtl.core.model.ProviderServiceType
import kotlinx.coroutines.flow.Flow

interface ProviderRepository {
    fun getAvailableProviders(): List<ProviderServiceType>
    fun getSelectedProvider(): Flow<ProviderServiceType?>
    suspend fun selectProvider(provider: ProviderServiceType?)
}
