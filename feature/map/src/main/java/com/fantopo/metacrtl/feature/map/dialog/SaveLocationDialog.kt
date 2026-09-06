package com.fantopo.metacrtl.feature.map.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.feature.map.ui.MapIcons
import com.fantopo.metacrtl.feature.map.ui.NoBorderTextField
import java.util.Locale

@Composable
fun SaveLocationDialog(
    initialPoint: LocationPoint,
    onDismiss: () -> Unit,
    onSave: (name: String, latitude: Double, longitude: Double) -> Unit
) {
    var latText by remember { mutableStateOf(String.format(Locale.US, "%.6f", initialPoint.latitude)) }
    var lngText by remember { mutableStateOf(String.format(Locale.US, "%.6f", initialPoint.longitude)) }
    var nameText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Save Location",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                )

                // Vertically arranged inputs with left icons and no-border style
                // 1) Latitude input
                NoBorderTextField(
                    value = latText,
                    onValueChange = { latText = it },
                    placeholder = "Latitude (e.g. -6.2088)",
                    icon = MapIcons.Navigation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                // 2) Longitude input
                NoBorderTextField(
                    value = lngText,
                    onValueChange = { lngText = it },
                    placeholder = "Longitude (e.g. 106.8456)",
                    icon = MapIcons.Explore,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                )

                // 3) Location name input
                NoBorderTextField(
                    value = nameText,
                    onValueChange = { nameText = it },
                    placeholder = "Location Name (e.g. Office / Favorite Spot)",
                    icon = MapIcons.EditLocation,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
                )

                if (errorMessage != null) {
                    Text(
                        text = errorMessage!!,
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Cancel")
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Button(
                        onClick = {
                            val lat = latText.toDoubleOrNull()
                            val lng = lngText.toDoubleOrNull()
                            if (lat == null || lng == null) {
                                errorMessage = "Please enter valid numerical coordinates"
                                return@Button
                            }
                            val name = nameText.ifBlank { "Point (${String.format(Locale.US, "%.4f, %.4f", lat, lng)})" }
                            onSave(name, lat, lng)
                        },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Save")
                    }
                }
            }
        }
    }
}
