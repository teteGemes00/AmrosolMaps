package com.fantopo.metacrtl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.fantopo.metacrtl.feature.map.ui.MapScreen
import com.fantopo.metacrtl.feature.map.viewmodel.MapViewModel
import com.fantopo.metacrtl.ui.theme.FantopoMapsTheme

class MainActivity : ComponentActivity() {

    private val mapViewModel: MapViewModel by viewModels {
        (application as FantopoApp).appContainer.provideMapViewModelFactory()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FantopoMapsTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MapScreen(viewModel = mapViewModel)
                }
            }
        }
    }
}
