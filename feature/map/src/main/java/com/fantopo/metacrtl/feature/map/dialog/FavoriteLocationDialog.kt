package com.fantopo.metacrtl.feature.map.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.fantopo.metacrtl.core.model.FavoriteLocation
import com.fantopo.metacrtl.core.model.LocationPoint
import com.fantopo.metacrtl.feature.map.ui.NoBorderTextField
import java.util.Locale

@Composable
fun FavoriteLocationDialog(
    favorites: List<FavoriteLocation>,
    currentPoint: LocationPoint,
    onDismiss: () -> Unit,
    onMoveTo: (latitude: Double, longitude: Double, name: String) -> Unit,
    onEditFavorite: (FavoriteLocation) -> Unit,
    onDeleteFavorite: (id: String) -> Unit,
    onAddFavorite: (name: String, latitude: Double, longitude: Double) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var editingFavorite by remember { mutableStateOf<FavoriteLocation?>(null) }
    var isAddingNew by remember { mutableStateOf(false) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.80f)
                .padding(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = null,
                            tint = Color(0xFFFF4081),
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Favorite Locations",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                        )
                    }
                    IconButton(onClick = { isAddingNew = !isAddingNew }) {
                        Icon(
                            imageVector = Icons.Default.Add,
                            contentDescription = "Add Current to Favorite",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Search field with no border lines
                NoBorderTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = "Search favorites...",
                    icon = Icons.Default.Search
                )

                // Quick add current location view
                if (isAddingNew) {
                    Spacer(modifier = Modifier.height(8.dp))
                    QuickAddFavoriteCard(
                        currentPoint = currentPoint,
                        onAdd = { name ->
                            onAddFavorite(name, currentPoint.latitude, currentPoint.longitude)
                            isAddingNew = false
                        },
                        onCancel = { isAddingNew = false }
                    )
                }

                // Edit dialog popup if editing
                if (editingFavorite != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    EditFavoriteCard(
                        favorite = editingFavorite!!,
                        onSave = { updated ->
                            onEditFavorite(updated)
                            editingFavorite = null
                        },
                        onCancel = { editingFavorite = null }
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                val filtered = favorites.filter {
                    it.name.contains(searchQuery, ignoreCase = true) ||
                    it.formatCoordinates().contains(searchQuery, ignoreCase = true)
                }

                if (filtered.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = if (favorites.isEmpty()) "No favorites saved yet" else "No matching favorites",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filtered, key = { it.id }) { item ->
                            FavoriteCardItem(
                                favorite = item,
                                onMove = {
                                    onMoveTo(item.latitude, item.longitude, item.name)
                                    onDismiss()
                                },
                                onEdit = { editingFavorite = item },
                                onDelete = { onDeleteFavorite(item.id) }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}

@Composable
private fun FavoriteCardItem(
    favorite: FavoriteLocation,
    onMove: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = favorite.name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                // Small subtitle with coordinates (lat, long) below name
                Text(
                    text = "(${favorite.formatCoordinates()})",
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                )
            }

            // Action buttons: edit, move, delete
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                IconButton(onClick = onEdit, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Favorite",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(onClick = onMove, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.NearMe,
                        contentDescription = "Move to Coordinate",
                        tint = Color(0xFF00C853),
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(onClick = onDelete, modifier = Modifier.size(36.dp)) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Favorite",
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickAddFavoriteCard(
    currentPoint: LocationPoint,
    onAdd: (name: String) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Add Current Point to Favorites",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            NoBorderTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Favorite Name (e.g. Home / Spot)"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) { Text("Cancel") }
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = {
                        val finalName = name.ifBlank { "Favorite (${currentPoint.formatCoordinates()})" }
                        onAdd(finalName)
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Add")
                }
            }
        }
    }
}

@Composable
private fun EditFavoriteCard(
    favorite: FavoriteLocation,
    onSave: (FavoriteLocation) -> Unit,
    onCancel: () -> Unit
) {
    var name by remember { mutableStateOf(favorite.name) }
    var latText by remember { mutableStateOf(String.format(Locale.US, "%.6f", favorite.latitude)) }
    var lngText by remember { mutableStateOf(String.format(Locale.US, "%.6f", favorite.longitude)) }

    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.4f)
        ),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Edit Favorite Location",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
            )
            NoBorderTextField(
                value = name,
                onValueChange = { name = it },
                placeholder = "Location Name"
            )
            NoBorderTextField(
                value = latText,
                onValueChange = { latText = it },
                placeholder = "Latitude"
            )
            NoBorderTextField(
                value = lngText,
                onValueChange = { lngText = it },
                placeholder = "Longitude"
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onCancel) { Text("Cancel") }
                Spacer(modifier = Modifier.width(6.dp))
                Button(
                    onClick = {
                        val lat = latText.toDoubleOrNull() ?: favorite.latitude
                        val lng = lngText.toDoubleOrNull() ?: favorite.longitude
                        onSave(favorite.copy(name = name.ifBlank { favorite.name }, latitude = lat, longitude = lng))
                    },
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text("Save")
                }
            }
        }
    }
}
