package com.fantopo.metacrtl.feature.map.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.fantopo.metacrtl.feature.map.dialog.FavoriteLocationDialog
import com.fantopo.metacrtl.feature.map.dialog.HistoryLocationDialog
import com.fantopo.metacrtl.feature.map.dialog.ProviderServiceDialog
import com.fantopo.metacrtl.feature.map.dialog.SaveLocationDialog
import com.fantopo.metacrtl.feature.map.dialog.SettingsDialog
import com.fantopo.metacrtl.feature.map.model.DialogType
import com.fantopo.metacrtl.feature.map.overlay.FloatingOverlayWidget
import com.fantopo.metacrtl.feature.map.viewmodel.MapViewModel

@Composable
fun MapScreen(
    viewModel: MapViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.messageSnackbar) {
        uiState.messageSnackbar?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.onDismissMessage()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = modifier.fillMaxSize()
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // 1. Full-screen map rendering surface
            MapSurfaceView(
                pinnedLocation = uiState.pinnedLocation,
                simulatedLocation = uiState.simulatedLocation,
                isGpsActive = uiState.isGpsActive,
                zoomLevel = uiState.zoomLevel,
                mapStyles = uiState.settings.mapStyles,
                onLocationPinned = { point ->
                    viewModel.onPinLocation(point)
                }
            )

            // 2. Floating mode overlay concept (when floating mode setting is active)
            if (uiState.settings.isFloatingMode) {
                FloatingOverlayWidget(
                    isExpanded = uiState.isFloatingExpanded,
                    onToggleExpand = { viewModel.onToggleFloatingOverlay() },
                    onRefreshLocation = { viewModel.onRefreshLocation() },
                    onStopSimulation = { viewModel.onStopGps() },
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 16.dp, end = 16.dp)
                )
            }

            // 3. In-app controls overlay (play/off on left, center, zoom, fullscreen, delete on right)
            InAppControlsOverlay(
                isGpsActive = uiState.isGpsActive,
                isFullScreen = uiState.isFullScreen,
                zoomLevel = uiState.zoomLevel,
                onTogglePlayGps = { viewModel.onTogglePlayGps() },
                onCenterLocation = { viewModel.onCenterLocation() },
                onToggleZoom = { viewModel.onToggleZoom() },
                onToggleFullScreen = { viewModel.onToggleFullScreen() },
                onDeleteLocation = { viewModel.onDeleteLocation() }
            )

            // 4. Bottom Menu Bar (hidden when fullscreen mode is toggled)
            AnimatedVisibility(
                visible = !uiState.isFullScreen,
                enter = slideInVertically(initialOffsetY = { it }),
                exit = slideOutVertically(targetOffsetY = { it }),
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                BottomMenuBar(
                    activeDialog = uiState.activeDialog,
                    onOpenDialog = { dialog -> viewModel.onOpenDialog(dialog) }
                )
            }

            // 5. Active Dialogs
            when (uiState.activeDialog) {
                DialogType.SAVE -> {
                    SaveLocationDialog(
                        initialPoint = uiState.pinnedLocation,
                        onDismiss = { viewModel.onDismissDialog() },
                        onSave = { name, lat, lng ->
                            viewModel.onSaveLocation(name, lat, lng)
                        }
                    )
                }
                DialogType.FAVORITE -> {
                    FavoriteLocationDialog(
                        favorites = uiState.favoriteLocations,
                        currentPoint = uiState.pinnedLocation,
                        onDismiss = { viewModel.onDismissDialog() },
                        onMoveTo = { lat, lng, name ->
                            viewModel.onMoveToLocation(lat, lng, name)
                        },
                        onEditFavorite = { updated ->
                            viewModel.onUpdateFavorite(updated)
                        },
                        onDeleteFavorite = { id ->
                            viewModel.onDeleteFavorite(id)
                        },
                        onAddFavorite = { name, lat, lng ->
                            viewModel.onAddFavorite(name, lat, lng)
                        }
                    )
                }
                DialogType.HISTORY -> {
                    HistoryLocationDialog(
                        historyEntries = uiState.historyEntries,
                        onDismiss = { viewModel.onDismissDialog() },
                        onMoveTo = { lat, lng, name ->
                            viewModel.onMoveToLocation(lat, lng, name)
                        },
                        onSaveToSaved = { name, lat, lng ->
                            viewModel.onSaveLocation(name, lat, lng)
                        },
                        onDeleteHistory = { id ->
                            viewModel.onDeleteHistory(id)
                        },
                        onClearAllHistory = {
                            viewModel.onClearHistory()
                        }
                    )
                }
                DialogType.SETTINGS -> {
                    SettingsDialog(
                        settings = uiState.settings,
                        onDismiss = { viewModel.onDismissDialog() },
                        onUpdateSettings = { newSettings ->
                            viewModel.onUpdateSettings(newSettings)
                        },
                        onToggleMapStyle = { style ->
                            viewModel.onToggleMapStyle(style)
                        }
                    )
                }
                DialogType.PROVIDER_SERVICE -> {
                    ProviderServiceDialog(
                        selectedProvider = uiState.selectedProvider,
                        onDismiss = { viewModel.onDismissDialog() },
                        onSelectProvider = { provider ->
                            viewModel.onSelectProvider(provider)
                        }
                    )
                }
                null -> {}
            }
        }
    }
}
