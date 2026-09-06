package com.fantopo.metacrtl.feature.map.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.core.model.MapStyleMode
import java.util.Locale

/**
 * Interactive map abstraction and rendering surface.
 *
 * NOTE FOR PRODUCTION DEPLOYMENT (Google Maps integration):
 * TODO: Replace or wrap this Canvas-based high-fidelity map renderer with GoogleMap Compose
 * (com.google.maps.android:maps-compose) or MapLibre Compose by supplying your GOOGLE_MAPS_API_KEY
 * in AndroidManifest.xml. The coordinate pinning, style overlay modes, and mock GPS engine
 * architecture will seamlessly bind to the GoogleMap marker state.
 */
@Composable
fun MapSurfaceView(
    pinnedLocation: LocationPoint,
    simulatedLocation: LocationPoint,
    isGpsActive: Boolean,
    zoomLevel: Float,
    mapStyles: Set<MapStyleMode>,
    onLocationPinned: (LocationPoint) -> Unit,
    modifier: Modifier = Modifier
) {
    var panOffsetX by remember { mutableStateOf(0f) }
    var panOffsetY by remember { mutableStateOf(0f) }

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseRadius by infiniteTransition.animateFloat(
        initialValue = 20f,
        targetValue = 60f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "radius"
    )
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 0.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "alpha"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectTapGestures { offset ->
                    val centerX = size.width / 2f + panOffsetX
                    val centerY = size.height / 2f + panOffsetY
                    val scaleFactor = 0.0001 / zoomLevel

                    val deltaX = offset.x - centerX
                    val deltaY = offset.y - centerY

                    val newLng = pinnedLocation.longitude + (deltaX * scaleFactor)
                    val newLat = pinnedLocation.latitude - (deltaY * scaleFactor)

                    onLocationPinned(
                        pinnedLocation.copy(
                            latitude = newLat,
                            longitude = newLng
                        )
                    )
                }
            }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    panOffsetX += dragAmount.x
                    panOffsetY += dragAmount.y
                }
            }
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val isNight = mapStyles.contains(MapStyleMode.NIGHT)
            val isHybrid = mapStyles.contains(MapStyleMode.HYBRID)
            val isTraffic = mapStyles.contains(MapStyleMode.TRAFFIC)

            drawMapBackground(isNight, isHybrid)
            drawGridAndLandmarks(size.width, size.height, panOffsetX, panOffsetY, zoomLevel, isNight, isHybrid)
            drawRoadNetwork(size.width, size.height, panOffsetX, panOffsetY, zoomLevel, isNight, isTraffic)

            val centerX = size.width / 2f + panOffsetX
            val centerY = size.height / 2f + panOffsetY

            if (isGpsActive) {
                // Pulse effect around simulated marker
                drawCircle(
                    color = Color(0xFF00E676).copy(alpha = pulseAlpha),
                    radius = pulseRadius * zoomLevel,
                    center = Offset(centerX, centerY)
                )
                drawCircle(
                    color = Color(0xFF00E676),
                    radius = 8f * zoomLevel,
                    center = Offset(centerX, centerY)
                )
                drawCircle(
                    color = Color.White,
                    radius = 4f * zoomLevel,
                    center = Offset(centerX, centerY)
                )
            } else {
                // Inactive pin indicator
                drawCircle(
                    color = Color(0xFFFF5252).copy(alpha = 0.4f),
                    radius = 16f * zoomLevel,
                    center = Offset(centerX, centerY)
                )
                drawCircle(
                    color = Color(0xFFFF5252),
                    radius = 8f * zoomLevel,
                    center = Offset(centerX, centerY)
                )
            }
        }

        // Location info card on top-center
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.92f)
            ),
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
        ) {
            Box(modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)) {
                Text(
                    text = if (isGpsActive) {
                        "Simulating: ${simulatedLocation.formatCoordinates()} | Speed: ${String.format(Locale.US, "%.1f", simulatedLocation.speed)} km/h"
                    } else {
                        "Pinned: ${pinnedLocation.formatCoordinates()} (GPS OFF)"
                    },
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = if (isGpsActive) Color(0xFF00C853) else MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        }
    }
}

private fun DrawScope.drawMapBackground(isNight: Boolean, isHybrid: Boolean) {
    val bgColor = when {
        isHybrid -> Color(0xFF1B2E1E) // Earth green-dark
        isNight -> Color(0xFF121824)  // Dark deep navy
        else -> Color(0xFFE8ECEF)     // Standard light map
    }
    drawRect(color = bgColor)
}

private fun DrawScope.drawGridAndLandmarks(
    w: Float,
    h: Float,
    offsetX: Float,
    offsetY: Float,
    zoom: Float,
    isNight: Boolean,
    isHybrid: Boolean
) {
    val gridColor = when {
        isNight -> Color(0xFF222F3E).copy(alpha = 0.5f)
        isHybrid -> Color(0xFF2E4033).copy(alpha = 0.7f)
        else -> Color(0xFFD4DADF)
    }

    val step = 80f * zoom
    var x = (offsetX % step)
    while (x < w) {
        drawLine(
            color = gridColor,
            start = Offset(x, 0f),
            end = Offset(x, h),
            strokeWidth = 1f
        )
        x += step
    }

    var y = (offsetY % step)
    while (y < h) {
        drawLine(
            color = gridColor,
            start = Offset(0f, y),
            end = Offset(w, y),
            strokeWidth = 1f
        )
        y += step
    }
}

private fun DrawScope.drawRoadNetwork(
    w: Float,
    h: Float,
    offsetX: Float,
    offsetY: Float,
    zoom: Float,
    isNight: Boolean,
    isTraffic: Boolean
) {
    val roadColor = when {
        isNight -> Color(0xFF2C3E50)
        else -> Color(0xFFFFFFFF)
    }

    val highwayColor = when {
        isNight -> Color(0xFF34495E)
        else -> Color(0xFFFFEAA7)
    }

    val mainRoadY = h / 2f + offsetY
    val mainRoadX = w / 2f + offsetX

    // Main Avenue Horizontal
    drawLine(
        color = roadColor,
        start = Offset(0f, mainRoadY),
        end = Offset(w, mainRoadY),
        strokeWidth = 14f * zoom
    )

    // Main Avenue Vertical
    drawLine(
        color = roadColor,
        start = Offset(mainRoadX, 0f),
        end = Offset(mainRoadX, h),
        strokeWidth = 14f * zoom
    )

    // Diagonal Highway
    drawLine(
        color = highwayColor,
        start = Offset(mainRoadX - 300f * zoom, mainRoadY - 300f * zoom),
        end = Offset(mainRoadX + 300f * zoom, mainRoadY + 300f * zoom),
        strokeWidth = 18f * zoom
    )

    if (isTraffic) {
        // Traffic congestion lines
        // Green fast traffic
        drawLine(
            color = Color(0xFF00E676).copy(alpha = 0.9f),
            start = Offset(0f, mainRoadY - 3f),
            end = Offset(mainRoadX, mainRoadY - 3f),
            strokeWidth = 4f * zoom
        )
        // Orange moderate traffic
        drawLine(
            color = Color(0xFFFFB300).copy(alpha = 0.9f),
            start = Offset(mainRoadX, mainRoadY - 3f),
            end = Offset(w, mainRoadY - 3f),
            strokeWidth = 4f * zoom
        )
        // Red heavy traffic on highway
        drawLine(
            color = Color(0xFFFF1744).copy(alpha = 0.9f),
            start = Offset(mainRoadX - 100f * zoom, mainRoadY - 100f * zoom),
            end = Offset(mainRoadX + 150f * zoom, mainRoadY + 150f * zoom),
            strokeWidth = 5f * zoom
        )
    }
}
