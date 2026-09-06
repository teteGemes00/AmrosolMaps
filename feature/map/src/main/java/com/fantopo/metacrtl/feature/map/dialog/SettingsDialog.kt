package com.fantopo.metacrtl.feature.map.dialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CompassCalibration
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.filled.PictureInPicture
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Terrain
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fantopo.metacrtl.core.model.AppSettings
import com.fantopo.metacrtl.core.model.MapStyleMode
import com.fantopo.metacrtl.feature.map.ui.NoBorderTextField
import java.util.Locale

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SettingsDialog(
    settings: AppSettings,
    onDismiss: () -> Unit,
    onUpdateSettings: (AppSettings) -> Unit,
    onToggleMapStyle: (MapStyleMode) -> Unit
) {
    var isFloating by remember { mutableStateOf(settings.isFloatingMode) }
    var isFused by remember { mutableStateOf(settings.isFusedMode) }
    var isRandomCoord by remember { mutableStateOf(settings.isRandomCoordinate) }

    var isRandomAccuracy by remember { mutableStateOf(settings.isRandomAccuracy) }
    var accuracyRange by remember { mutableStateOf(settings.accuracyMin..settings.accuracyMax) }

    var isRandomAltitude by remember { mutableStateOf(settings.isRandomAltitude) }
    var altitudeRange by remember { mutableStateOf(settings.altitudeMin..settings.altitudeMax) }

    var isRandomBearing by remember { mutableStateOf(settings.isRandomBearing) }

    var isRandomSpeed by remember { mutableStateOf(settings.isRandomSpeed) }
    var speedRange by remember { mutableStateOf(settings.speedMin..settings.speedMax) }

    var refreshTimeSlider by remember { mutableStateOf(settings.refreshTimeMs.toFloat()) }
    var refreshTimeText by remember { mutableStateOf(settings.refreshTimeMs.toString()) }

    fun commitSettings() {
        val parsedRefresh = refreshTimeText.toLongOrNull()?.coerceIn(AppSettings.REFRESH_TIME_MIN, AppSettings.REFRESH_TIME_MAX)
            ?: refreshTimeSlider.toLong()

        onUpdateSettings(
            settings.copy(
                isFloatingMode = isFloating,
                isFusedMode = isFused,
                isRandomCoordinate = isRandomCoord,
                isRandomAccuracy = isRandomAccuracy,
                accuracyMin = accuracyRange.start,
                accuracyMax = accuracyRange.endInclusive,
                isRandomAltitude = isRandomAltitude,
                altitudeMin = altitudeRange.start,
                altitudeMax = altitudeRange.endInclusive,
                isRandomBearing = isRandomBearing,
                isRandomSpeed = isRandomSpeed,
                speedMin = speedRange.start,
                speedMax = speedRange.endInclusive,
                refreshTimeMs = parsedRefresh
            )
        )
    }

    Dialog(onDismissRequest = {
        commitSettings()
        onDismiss()
    }) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.88f)
                .padding(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Fantopo Settings",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // 1) Floating mode toggle
                    SettingToggleRow(
                        title = "Floating Mode",
                        description = "Shows logo overlay with downward expand for refresh & stop",
                        icon = Icons.Default.PictureInPicture,
                        checked = isFloating,
                        onCheckedChange = {
                            isFloating = it
                            commitSettings()
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 2) Fused mode toggle
                    SettingToggleRow(
                        title = "Fused Mode",
                        description = "Simulate mock fused location provider integration",
                        icon = Icons.Default.Navigation,
                        checked = isFused,
                        onCheckedChange = {
                            isFused = it
                            commitSettings()
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 3) Random Coordinate toggle
                    SettingToggleRow(
                        title = "Random Coordinate",
                        description = "Realistic subtle lat/long random offset (1-5m)",
                        icon = Icons.Default.Explore,
                        checked = isRandomCoord,
                        onCheckedChange = {
                            isRandomCoord = it
                            commitSettings()
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 4) Random Accuracy toggle & RangeSlider (0-5, default 5)
                    SettingToggleRow(
                        title = "Random Accuracy",
                        description = "Range 0 to 5 meters (default min/max 5m)",
                        icon = Icons.Default.CompassCalibration,
                        checked = isRandomAccuracy,
                        onCheckedChange = {
                            isRandomAccuracy = it
                            commitSettings()
                        }
                    )
                    AnimatedVisibility(visible = isRandomAccuracy) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Accuracy: ${String.format(Locale.US, "%.1f", accuracyRange.start)}m – ${String.format(Locale.US, "%.1f", accuracyRange.endInclusive)}m",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                            )
                            RangeSlider(
                                value = accuracyRange,
                                onValueChange = { range ->
                                    accuracyRange = range
                                    commitSettings()
                                },
                                valueRange = AppSettings.ACCURACY_ALLOWED_MIN..AppSettings.ACCURACY_ALLOWED_MAX,
                                steps = 9
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 5) Random Altitude toggle & RangeSlider (0-75, default 0-15)
                    SettingToggleRow(
                        title = "Random Altitude",
                        description = "Range 0 to 75 meters (default min 0, max 15)",
                        icon = Icons.Default.Terrain,
                        checked = isRandomAltitude,
                        onCheckedChange = {
                            isRandomAltitude = it
                            commitSettings()
                        }
                    )
                    AnimatedVisibility(visible = isRandomAltitude) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Altitude: ${String.format(Locale.US, "%.1f", altitudeRange.start)}m – ${String.format(Locale.US, "%.1f", altitudeRange.endInclusive)}m",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                            )
                            RangeSlider(
                                value = altitudeRange,
                                onValueChange = { range ->
                                    altitudeRange = range
                                    commitSettings()
                                },
                                valueRange = AppSettings.ALTITUDE_ALLOWED_MIN..AppSettings.ALTITUDE_ALLOWED_MAX,
                                steps = 74
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 6) Random Bearing (0°-360°)
                    SettingToggleRow(
                        title = "Random Bearing",
                        description = "Randomize heading angle across 0°–360°",
                        icon = Icons.Default.CompassCalibration,
                        checked = isRandomBearing,
                        onCheckedChange = {
                            isRandomBearing = it
                            commitSettings()
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 7) Random Speed toggle & RangeSlider (0-55, default 1-5)
                    SettingToggleRow(
                        title = "Random Speed",
                        description = "Range 0 to 55 km/h (default min 1, max 5)",
                        icon = Icons.Default.Speed,
                        checked = isRandomSpeed,
                        onCheckedChange = {
                            isRandomSpeed = it
                            commitSettings()
                        }
                    )
                    AnimatedVisibility(visible = isRandomSpeed) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Text(
                                text = "Speed: ${String.format(Locale.US, "%.1f", speedRange.start)} km/h – ${String.format(Locale.US, "%.1f", speedRange.endInclusive)} km/h",
                                style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold)
                            )
                            RangeSlider(
                                value = speedRange,
                                onValueChange = { range ->
                                    speedRange = range
                                    commitSettings()
                                },
                                valueRange = AppSettings.SPEED_ALLOWED_MIN..AppSettings.SPEED_ALLOWED_MAX,
                                steps = 54
                            )
                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 8) Refresh time: Slider (0-1300) + text input without border line
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Timer,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Refresh Time: ${refreshTimeSlider.toLong()} ms",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        Slider(
                            value = refreshTimeSlider,
                            onValueChange = {
                                refreshTimeSlider = it
                                refreshTimeText = it.toLong().toString()
                                commitSettings()
                            },
                            valueRange = AppSettings.REFRESH_TIME_MIN.toFloat()..AppSettings.REFRESH_TIME_MAX.toFloat()
                        )

                        NoBorderTextField(
                            value = refreshTimeText,
                            onValueChange = { text ->
                                refreshTimeText = text
                                val parsed = text.toFloatOrNull()
                                if (parsed != null) {
                                    refreshTimeSlider = parsed.coerceIn(AppSettings.REFRESH_TIME_MIN.toFloat(), AppSettings.REFRESH_TIME_MAX.toFloat())
                                }
                                commitSettings()
                            },
                            placeholder = "Enter refresh time ms (0–1300)",
                            icon = Icons.Default.Refresh,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 2.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))

                    // 9) Maps Style (hybrid, night, traffic) combinable
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.35f), RoundedCornerShape(12.dp))
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Layers,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Maps Style (Combinable)",
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            MapStyleMode.entries.forEach { style ->
                                val isSelected = settings.mapStyles.contains(style)
                                FilterChip(
                                    selected = isSelected,
                                    onClick = { onToggleMapStyle(style) },
                                    label = { Text(style.title, fontSize = 12.sp) },
                                    leadingIcon = if (isSelected) {
                                        {
                                            Icon(
                                                imageVector = Icons.Default.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        }
                                    } else null,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = {
                            commitSettings()
                            onDismiss()
                        }
                    ) {
                        Text("Done")
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingToggleRow(
    title: String,
    description: String,
    icon: ImageVector,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .clickable { onCheckedChange(!checked) }
            .padding(vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = if (checked) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.size(22.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
            }
        }
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange
        )
    }
}
