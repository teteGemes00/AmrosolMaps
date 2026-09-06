package com.fantopo.metacrtl.feature.map.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fantopo.metacrtl.feature.map.model.DialogType

@Composable
fun BottomMenuBar(
    activeDialog: DialogType?,
    onOpenDialog: (DialogType) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.96f)
        ),
        shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 4.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            MenuItem(
                title = "Save",
                icon = MapIcons.Bookmark,
                isSelected = activeDialog == DialogType.SAVE,
                onClick = { onOpenDialog(DialogType.SAVE) }
            )
            MenuItem(
                title = "Favorite",
                icon = MapIcons.Favorite,
                isSelected = activeDialog == DialogType.FAVORITE,
                onClick = { onOpenDialog(DialogType.FAVORITE) }
            )
            MenuItem(
                title = "Histori",
                icon = MapIcons.History,
                isSelected = activeDialog == DialogType.HISTORY,
                onClick = { onOpenDialog(DialogType.HISTORY) }
            )
            MenuItem(
                title = "Setting",
                icon = MapIcons.Settings,
                isSelected = activeDialog == DialogType.SETTINGS,
                onClick = { onOpenDialog(DialogType.SETTINGS) }
            )
            MenuItem(
                title = "Provider",
                icon = MapIcons.ElectricRickshaw,
                isSelected = activeDialog == DialogType.PROVIDER_SERVICE,
                onClick = { onOpenDialog(DialogType.PROVIDER_SERVICE) }
            )
        }
    }
}

@Composable
private fun MenuItem(
    title: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val activeColor = MaterialTheme.colorScheme.primary
    val inactiveColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.75f)

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(horizontal = 8.dp, vertical = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    color = if (isSelected) activeColor.copy(alpha = 0.15f) else Color.Transparent,
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = if (isSelected) activeColor else inactiveColor,
                modifier = Modifier.size(22.dp)
            )
        }
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 11.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) activeColor else inactiveColor
            )
        )
    }
}
