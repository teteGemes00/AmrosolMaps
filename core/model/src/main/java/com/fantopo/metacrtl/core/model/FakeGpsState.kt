package com.fantopo.metacrtl.core.model

data class FakeGpsState(
    val isActive: Boolean = false,
    val pinnedLocation: LocationPoint = LocationPoint.DEFAULT,
    val simulatedLocation: LocationPoint = LocationPoint.DEFAULT,
    val activeProvider: ProviderServiceType? = null,
    val lastUpdatedAt: Long = 0L,
    val refreshCount: Long = 0L
)
