package com.fantopo.metacrtl

import android.content.Context
import com.fantopo.metacrtl.di.AppContainer
import com.fantopo.metacrtl.feature.map.viewmodel.MapViewModel
import org.junit.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito.mock

class AppWiringTest {

    @Test
    fun testAppContainerCreationAndViewModelFactory() {
        val mockContext = mock(Context::class.java)
        val container = AppContainer(mockContext)

        assertNotNull(container.locationRepository)
        assertNotNull(container.settingsRepository)
        assertNotNull(container.providerRepository)
        assertNotNull(container.fakeGpsManager)
        assertNotNull(container.locationRandomizer)

        val factory = container.provideMapViewModelFactory()
        val viewModel = factory.create(MapViewModel::class.java)
        assertNotNull(viewModel)
    }
}
