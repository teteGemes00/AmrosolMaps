package com.fantopo.metacrtl.feature.map.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun InAppControlsOverlay(
    isGpsActive: Boolean,
    isFullScreen: Boolean,
    zoomLevel: Float,
    onTogglePlayGps: () -> Unit,
    onCenterLocation: () -> Unit,
    onToggleZoom: () -> Unit,
    onToggleFullScreen: () -> Unit,
    onDeleteLocation: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Play / Off button on the left side of the screen
        FloatingActionButton(
            onClick = onTogglePlayGps,
            shape = CircleShape,
            containerColor = if (isGpsActive) Color(0xFF00E676) else Color(0xFFFF5252),
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(6.dp),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .size(56.dp)
        ) {
            Icon(
                imageVector = if (isGpsActive) MapIcons.Stop else MapIcons.PlayArrow,
                contentDescription = if (isGpsActive) "Stop Fake GPS" else "Start Fake GPS",
                modifier = Modifier.size(32.dp)
            )
        }

        // Additional in-app controls above menu bar on the right side:
        // center location, zoom in/out toggle, fullscreen toggle, delete button
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 80.dp) // Sits neatly above the bottom menu bar
        ) {
            // Pemusat Lokasi (Center Location)
            SmallFloatingActionButton(
                onClick = onCenterLocation,
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    imageVector = MapIcons.MyLocation,
                    contentDescription = "Center Location"
                )
            }

            // Zoom satu tombol in/out
            SmallFloatingActionButton(
                onClick = onToggleZoom,
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    imageVector = if (zoomLevel > 1.2f) MapIcons.ZoomOut else MapIcons.ZoomIn,
                    contentDescription = "Toggle Zoom"
                )
            }

            // Fullscreen / Normal screen satu tombol
            SmallFloatingActionButton(
                onClick = onToggleFullScreen,
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    imageVector = if (isFullScreen) MapIcons.FullscreenExit else MapIcons.Fullscreen,
                    contentDescription = "Toggle Fullscreen"
                )
            }

            // Delete button (Reset / Delete pinned marker)
            SmallFloatingActionButton(
                onClick = onDeleteLocation,
                shape = RoundedCornerShape(12.dp),
                containerColor = MaterialTheme.colorScheme.errorContainer,
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                elevation = FloatingActionButtonDefaults.elevation(4.dp)
            ) {
                Icon(
                    imageVector = MapIcons.Delete,
                    contentDescription = "Delete / Reset Pin"
                )
            }
        }
    }
}
