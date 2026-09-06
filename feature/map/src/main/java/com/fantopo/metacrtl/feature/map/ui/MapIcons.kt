package com.fantopo.metacrtl.feature.map.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

/**
 * Self-contained vector icons used across the map feature UI to ensure
 * 100% offline compilation without requiring the heavy material-icons-extended library.
 */
object MapIcons {
    val Add: ImageVector get() = Icons.Default.Add
    val Check: ImageVector get() = Icons.Default.Check
    val Close: ImageVector get() = Icons.Default.Close
    val Delete: ImageVector get() = Icons.Default.Delete
    val Edit: ImageVector get() = Icons.Default.Edit
    val Favorite: ImageVector get() = Icons.Default.Favorite
    val LocationOn: ImageVector get() = Icons.Default.LocationOn
    val PlayArrow: ImageVector get() = Icons.Default.PlayArrow
    val Refresh: ImageVector get() = Icons.Default.Refresh
    val Search: ImageVector get() = Icons.Default.Search
    val Settings: ImageVector get() = Icons.Default.Settings

    val Stop: ImageVector by lazy {
        materialIcon(name = "Stop") {
            moveTo(6f, 6f)
            lineTo(18f, 6f)
            lineTo(18f, 18f)
            lineTo(6f, 18f)
            close()
        }
    }

    val MyLocation: ImageVector by lazy {
        materialIcon(name = "MyLocation") {
            moveTo(12f, 8f)
            curveTo(9.79f, 8f, 8f, 9.79f, 8f, 12f)
            curveTo(8f, 14.21f, 9.79f, 16f, 12f, 16f)
            curveTo(14.21f, 16f, 16f, 14.21f, 16f, 12f)
            curveTo(16f, 9.79f, 14.21f, 8f, 12f, 8f)
            close()
            moveTo(20.94f, 11f)
            curveTo(20.48f, 6.83f, 17.17f, 3.52f, 13f, 3.06f)
            lineTo(13f, 1f)
            lineTo(11f, 1f)
            lineTo(11f, 3.06f)
            curveTo(6.83f, 3.52f, 3.52f, 6.83f, 3.06f, 11f)
            lineTo(1f, 11f)
            lineTo(1f, 13f)
            lineTo(3.06f, 13f)
            curveTo(3.52f, 17.17f, 6.83f, 20.48f, 11f, 20.94f)
            lineTo(11f, 23f)
            lineTo(13f, 23f)
            lineTo(13f, 20.94f)
            curveTo(17.17f, 20.48f, 20.48f, 17.17f, 20.94f, 13f)
            lineTo(23f, 13f)
            lineTo(23f, 11f)
            lineTo(20.94f, 11f)
            close()
            moveTo(12f, 19f)
            curveTo(8.13f, 19f, 5f, 15.87f, 5f, 12f)
            curveTo(5f, 8.13f, 8.13f, 5f, 12f, 5f)
            curveTo(15.87f, 5f, 19f, 8.13f, 19f, 12f)
            curveTo(19f, 15.87f, 15.87f, 19f, 12f, 19f)
            close()
        }
    }

    val ZoomIn: ImageVector by lazy {
        materialIcon(name = "ZoomIn") {
            moveTo(15.5f, 14f)
            lineTo(14.71f, 14f)
            lineTo(14.43f, 13.73f)
            curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
            curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
            curveTo(5.91f, 3f, 3f, 5.91f, 3f, 9.5f)
            curveTo(3f, 13.09f, 5.91f, 16f, 9.5f, 16f)
            curveTo(11.11f, 16f, 12.59f, 15.41f, 13.73f, 14.43f)
            lineTo(14f, 14.71f)
            lineTo(14f, 15.5f)
            lineTo(19f, 20.49f)
            lineTo(20.49f, 19f)
            lineTo(15.5f, 14f)
            close()
            moveTo(9.5f, 14f)
            curveTo(7.01f, 14f, 5f, 11.99f, 5f, 9.5f)
            curveTo(5f, 7.01f, 7.01f, 5f, 9.5f, 5f)
            curveTo(11.99f, 5f, 14f, 7.01f, 14f, 9.5f)
            curveTo(14f, 11.99f, 11.99f, 14f, 9.5f, 14f)
            close()
            moveTo(10f, 7f)
            lineTo(9f, 7f)
            lineTo(9f, 9f)
            lineTo(7f, 9f)
            lineTo(7f, 10f)
            lineTo(9f, 10f)
            lineTo(9f, 12f)
            lineTo(10f, 12f)
            lineTo(10f, 10f)
            lineTo(12f, 10f)
            lineTo(12f, 9f)
            lineTo(10f, 9f)
            close()
        }
    }

    val ZoomOut: ImageVector by lazy {
        materialIcon(name = "ZoomOut") {
            moveTo(15.5f, 14f)
            lineTo(14.71f, 14f)
            lineTo(14.43f, 13.73f)
            curveTo(15.41f, 12.59f, 16f, 11.11f, 16f, 9.5f)
            curveTo(16f, 5.91f, 13.09f, 3f, 9.5f, 3f)
            curveTo(5.91f, 3f, 3f, 5.91f, 3f, 9.5f)
            curveTo(3f, 13.09f, 5.91f, 16f, 9.5f, 16f)
            curveTo(11.11f, 16f, 12.59f, 15.41f, 13.73f, 14.43f)
            lineTo(14f, 14.71f)
            lineTo(14f, 15.5f)
            lineTo(19f, 20.49f)
            lineTo(20.49f, 19f)
            lineTo(15.5f, 14f)
            close()
            moveTo(9.5f, 14f)
            curveTo(7.01f, 14f, 5f, 11.99f, 5f, 9.5f)
            curveTo(5f, 7.01f, 7.01f, 5f, 9.5f, 5f)
            curveTo(11.99f, 5f, 14f, 7.01f, 14f, 9.5f)
            curveTo(14f, 11.99f, 11.99f, 14f, 9.5f, 14f)
            close()
            moveTo(7f, 9f)
            lineTo(12f, 9f)
            lineTo(12f, 10f)
            lineTo(7f, 10f)
            close()
        }
    }

    val Fullscreen: ImageVector by lazy {
        materialIcon(name = "Fullscreen") {
            moveTo(7f, 14f)
            lineTo(5f, 14f)
            lineTo(5f, 19f)
            lineTo(10f, 19f)
            lineTo(10f, 17f)
            lineTo(7f, 17f)
            close()
            moveTo(5f, 10f)
            lineTo(7f, 10f)
            lineTo(7f, 7f)
            lineTo(10f, 7f)
            lineTo(10f, 5f)
            lineTo(5f, 5f)
            close()
            moveTo(19f, 19f)
            lineTo(19f, 14f)
            lineTo(17f, 14f)
            lineTo(17f, 17f)
            lineTo(14f, 17f)
            lineTo(14f, 19f)
            close()
            moveTo(17f, 7f)
            lineTo(17f, 10f)
            lineTo(19f, 10f)
            lineTo(19f, 5f)
            lineTo(14f, 5f)
            lineTo(14f, 7f)
            close()
        }
    }

    val FullscreenExit: ImageVector by lazy {
        materialIcon(name = "FullscreenExit") {
            moveTo(5f, 16f)
            lineTo(8f, 16f)
            lineTo(8f, 19f)
            lineTo(10f, 19f)
            lineTo(10f, 14f)
            lineTo(5f, 14f)
            close()
            moveTo(8f, 8f)
            lineTo(5f, 8f)
            lineTo(5f, 10f)
            lineTo(10f, 10f)
            lineTo(10f, 5f)
            lineTo(8f, 5f)
            close()
            moveTo(14f, 19f)
            lineTo(16f, 19f)
            lineTo(16f, 16f)
            lineTo(19f, 16f)
            lineTo(19f, 14f)
            lineTo(14f, 14f)
            close()
            moveTo(16f, 8f)
            lineTo(19f, 8f)
            lineTo(19f, 5f)
            lineTo(16f, 5f)
            lineTo(14f, 5f)
            lineTo(14f, 10f)
            lineTo(16f, 10f)
            close()
        }
    }

    val NearMe: ImageVector by lazy {
        materialIcon(name = "NearMe") {
            moveTo(21f, 3f)
            lineTo(3f, 10.53f)
            lineTo(11.41f, 12.59f)
            lineTo(13.47f, 21f)
            close()
        }
    }

    val Bookmark: ImageVector by lazy {
        materialIcon(name = "Bookmark") {
            moveTo(17f, 3f)
            lineTo(7f, 3f)
            curveTo(5.9f, 3f, 5f, 3.9f, 5f, 5f)
            lineTo(5f, 21f)
            lineTo(12f, 18f)
            lineTo(19f, 21f)
            lineTo(19f, 5f)
            curveTo(19f, 3.9f, 18.1f, 3f, 17f, 3f)
            close()
        }
    }

    val BookmarkAdd: ImageVector by lazy {
        materialIcon(name = "BookmarkAdd") {
            moveTo(17f, 3f)
            lineTo(7f, 3f)
            curveTo(5.9f, 3f, 5f, 3.9f, 5f, 5f)
            lineTo(5f, 21f)
            lineTo(12f, 18f)
            lineTo(19f, 21f)
            lineTo(19f, 13.54f)
            curveTo(18.37f, 13.83f, 17.69f, 14f, 17f, 14f)
            curveTo(14.24f, 14f, 12f, 11.76f, 12f, 9f)
            curveTo(12f, 6.54f, 13.79f, 4.49f, 16.12f, 4.09f)
            curveTo(16.39f, 4.03f, 16.69f, 4f, 17f, 4f)
            lineTo(17f, 3f)
            close()
            moveTo(18f, 6f)
            lineTo(18f, 8f)
            lineTo(16f, 8f)
            lineTo(16f, 10f)
            lineTo(18f, 10f)
            lineTo(18f, 12f)
            lineTo(20f, 12f)
            lineTo(20f, 10f)
            lineTo(22f, 10f)
            lineTo(22f, 8f)
            lineTo(20f, 8f)
            lineTo(20f, 6f)
            close()
        }
    }

    val History: ImageVector by lazy {
        materialIcon(name = "History") {
            moveTo(13f, 3f)
            curveTo(8.03f, 3f, 4f, 7.03f, 4f, 12f)
            lineTo(1f, 12f)
            lineTo(4.89f, 15.89f)
            lineTo(4.96f, 16.03f)
            lineTo(9f, 12f)
            lineTo(6f, 12f)
            curveTo(6f, 8.13f, 9.13f, 5f, 13f, 5f)
            curveTo(16.87f, 5f, 20f, 8.13f, 20f, 12f)
            curveTo(20f, 15.87f, 16.87f, 19f, 13f, 19f)
            curveTo(11.07f, 19f, 9.32f, 18.21f, 8.06f, 16.94f)
            lineTo(6.64f, 18.36f)
            curveTo(8.27f, 19.99f, 10.51f, 21f, 13f, 21f)
            curveTo(17.97f, 21f, 22f, 16.97f, 22f, 12f)
            curveTo(22f, 7.03f, 17.97f, 3f, 13f, 3f)
            close()
            moveTo(12f, 8f)
            lineTo(12f, 13f)
            lineTo(16.28f, 15.54f)
            lineTo(17f, 14.33f)
            lineTo(13.5f, 12.25f)
            lineTo(13.5f, 8f)
            close()
        }
    }

    val ClearAll: ImageVector by lazy {
        materialIcon(name = "ClearAll") {
            moveTo(5f, 13f)
            lineTo(19f, 13f)
            lineTo(19f, 11f)
            lineTo(5f, 11f)
            close()
            moveTo(3f, 17f)
            lineTo(17f, 17f)
            lineTo(17f, 15f)
            lineTo(3f, 15f)
            close()
            moveTo(7f, 7f)
            lineTo(7f, 9f)
            lineTo(21f, 9f)
            lineTo(21f, 7f)
            close()
        }
    }

    val PinDrop: ImageVector by lazy {
        materialIcon(name = "PinDrop") {
            moveTo(18f, 8f)
            curveTo(18f, 3.58f, 14.42f, 0f, 10f, 0f)
            curveTo(5.58f, 0f, 2f, 3.58f, 2f, 8f)
            curveTo(2f, 13.08f, 8.42f, 20.35f, 9.24f, 21.26f)
            curveTo(9.64f, 21.71f, 10.36f, 21.71f, 10.76f, 21.26f)
            curveTo(11.58f, 20.35f, 18f, 13.08f, 18f, 8f)
            close()
            moveTo(10f, 11f)
            curveTo(8.34f, 11f, 7f, 9.66f, 7f, 8f)
            curveTo(7f, 6.34f, 8.34f, 5f, 10f, 5f)
            curveTo(11.66f, 5f, 13f, 6.34f, 13f, 8f)
            curveTo(13f, 9.66f, 11.66f, 11f, 10f, 11f)
            close()
            moveTo(20f, 22f)
            lineTo(0f, 22f)
            lineTo(0f, 24f)
            lineTo(20f, 24f)
            close()
        }
    }

    val ElectricRickshaw: ImageVector by lazy {
        materialIcon(name = "ElectricRickshaw") {
            moveTo(21f, 11.18f)
            lineTo(21f, 9f)
            curveTo(21f, 7.9f, 20.1f, 7f, 19f, 7f)
            lineTo(18f, 7f)
            lineTo(15f, 3f)
            lineTo(7f, 3f)
            curveTo(5.9f, 3f, 5f, 3.9f, 5f, 5f)
            lineTo(5f, 6f)
            lineTo(3f, 6f)
            curveTo(1.9f, 6f, 1f, 6.9f, 1f, 8f)
            lineTo(1f, 15f)
            lineTo(3.08f, 15f)
            curveTo(3.54f, 16.78f, 5.16f, 18.11f, 7.1f, 17.99f)
            curveTo(9.12f, 17.86f, 10.74f, 16.18f, 10.87f, 14.16f)
            curveTo(10.96f, 12.87f, 10.43f, 11.71f, 9.54f, 11f)
            lineTo(14f, 11f)
            lineTo(14f, 15f)
            lineTo(16.08f, 15f)
            curveTo(16.54f, 16.78f, 18.16f, 18.11f, 20.1f, 17.99f)
            curveTo(22.12f, 17.86f, 23.74f, 16.18f, 23.87f, 14.16f)
            curveTo(24f, 12.14f, 22.75f, 11.33f, 21f, 11.18f)
            close()
            moveTo(7f, 5f)
            lineTo(13.5f, 5f)
            lineTo(15.75f, 8f)
            lineTo(7f, 8f)
            close()
            moveTo(7f, 16f)
            curveTo(5.9f, 16f, 5f, 15.1f, 5f, 14f)
            curveTo(5f, 12.9f, 5.9f, 12f, 7f, 12f)
            curveTo(8.1f, 12f, 9f, 12.9f, 9f, 14f)
            curveTo(9f, 15.1f, 8.1f, 16f, 7f, 16f)
            close()
            moveTo(20f, 16f)
            curveTo(18.9f, 16f, 18f, 15.1f, 18f, 14f)
            curveTo(18f, 12.9f, 18.9f, 12f, 20f, 12f)
            curveTo(21.1f, 12f, 22f, 12.9f, 22f, 14f)
            curveTo(22f, 15.1f, 21.1f, 16f, 20f, 16f)
            close()
        }
    }

    val Navigation: ImageVector by lazy {
        materialIcon(name = "Navigation") {
            moveTo(12f, 2f)
            lineTo(4.5f, 20.29f)
            lineTo(5.21f, 21f)
            lineTo(12f, 18f)
            lineTo(18.79f, 21f)
            lineTo(19.5f, 20.29f)
            close()
        }
    }

    val Explore: ImageVector by lazy {
        materialIcon(name = "Explore") {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
            moveTo(12f, 20f)
            curveTo(7.59f, 20f, 4f, 16.41f, 4f, 12f)
            curveTo(4f, 7.59f, 7.59f, 4f, 12f, 4f)
            curveTo(16.41f, 4f, 20f, 7.59f, 20f, 12f)
            curveTo(20f, 16.41f, 16.41f, 20f, 12f, 20f)
            close()
            moveTo(6.5f, 17.5f)
            lineTo(10.5f, 10.5f)
            lineTo(17.5f, 6.5f)
            lineTo(13.5f, 13.5f)
            close()
            moveTo(12f, 10.9f)
            curveTo(11.39f, 10.9f, 10.9f, 11.39f, 10.9f, 12f)
            curveTo(10.9f, 12.61f, 11.39f, 13.1f, 12f, 13.1f)
            curveTo(12.61f, 13.1f, 13.1f, 12.61f, 13.1f, 12f)
            curveTo(13.1f, 11.39f, 12.61f, 10.9f, 12f, 10.9f)
            close()
        }
    }

    val EditLocation: ImageVector by lazy {
        materialIcon(name = "EditLocation") {
            moveTo(12f, 2f)
            curveTo(8.13f, 2f, 5f, 5.13f, 5f, 9f)
            curveTo(5f, 14.25f, 12f, 22f, 12f, 22f)
            curveTo(12f, 22f, 19f, 14.25f, 19f, 9f)
            curveTo(19f, 5.13f, 15.87f, 2f, 12f, 2f)
            close()
            moveTo(14.3f, 8.45f)
            lineTo(13.43f, 9.32f)
            lineTo(11.68f, 7.57f)
            lineTo(12.55f, 6.7f)
            curveTo(12.75f, 6.5f, 13.06f, 6.5f, 13.25f, 6.7f)
            lineTo(14.3f, 7.75f)
            curveTo(14.5f, 7.94f, 14.5f, 8.26f, 14.3f, 8.45f)
            close()
            moveTo(8.5f, 10.75f)
            lineTo(11.11f, 8.14f)
            lineTo(12.86f, 9.89f)
            lineTo(10.25f, 12.5f)
            lineTo(8.5f, 12.5f)
            close()
        }
    }

    val PictureInPicture: ImageVector by lazy {
        materialIcon(name = "PictureInPicture") {
            moveTo(19f, 7f)
            lineTo(11f, 7f)
            lineTo(11f, 13f)
            lineTo(19f, 13f)
            close()
            moveTo(21f, 3f)
            lineTo(3f, 3f)
            curveTo(1.9f, 3f, 1f, 3.9f, 1f, 5f)
            lineTo(1f, 19f)
            curveTo(1f, 20.1f, 1.9f, 21f, 3f, 21f)
            lineTo(21f, 21f)
            curveTo(22.1f, 21f, 23f, 20.1f, 23f, 19f)
            lineTo(23f, 5f)
            curveTo(23f, 3.9f, 22.1f, 3f, 21f, 3f)
            close()
            moveTo(21f, 19f)
            lineTo(3f, 19f)
            lineTo(3f, 5f)
            lineTo(21f, 5f)
            close()
        }
    }

    val CompassCalibration: ImageVector by lazy {
        materialIcon(name = "CompassCalibration") {
            moveTo(12f, 17f)
            curveTo(10.9f, 17f, 10f, 17.9f, 10f, 19f)
            curveTo(10f, 20.1f, 10.9f, 21f, 12f, 21f)
            curveTo(13.1f, 21f, 14f, 20.1f, 14f, 19f)
            curveTo(14f, 17.9f, 13.1f, 17f, 12f, 17f)
            close()
            moveTo(12f, 3f)
            curveTo(7.03f, 3f, 2.74f, 5.09f, -0.01f, 8.44f)
            lineTo(12f, 21f)
            lineTo(24.01f, 8.44f)
            curveTo(21.26f, 5.09f, 16.97f, 3f, 12f, 3f)
            close()
            moveTo(12f, 15f)
            curveTo(14.21f, 15f, 16f, 13.21f, 16f, 11f)
            curveTo(16f, 8.79f, 14.21f, 7f, 12f, 7f)
            curveTo(9.79f, 7f, 8f, 8.79f, 8f, 11f)
            curveTo(8f, 13.21f, 9.79f, 15f, 12f, 15f)
            close()
        }
    }

    val Terrain: ImageVector by lazy {
        materialIcon(name = "Terrain") {
            moveTo(14f, 6f)
            lineTo(10.25f, 11f)
            lineTo(11.6f, 12.8f)
            lineTo(14f, 9.6f)
            lineTo(19f, 16f)
            lineTo(5f, 16f)
            lineTo(8.5f, 11.33f)
            lineTo(5f, 16f)
            close()
            moveTo(14f, 4f)
            lineTo(5f, 16f)
            curveTo(4.45f, 16.73f, 4.97f, 17.75f, 5.87f, 17.75f)
            lineTo(18.13f, 17.75f)
            curveTo(19.03f, 17.75f, 19.55f, 16.73f, 19f, 16f)
            lineTo(14f, 4f)
            close()
        }
    }

    val Speed: ImageVector by lazy {
        materialIcon(name = "Speed") {
            moveTo(20.38f, 8.57f)
            lineTo(18.96f, 9.99f)
            curveTo(19.61f, 11.19f, 20f, 12.55f, 20f, 14f)
            curveTo(20f, 18.42f, 16.42f, 22f, 12f, 22f)
            curveTo(7.58f, 22f, 4f, 18.42f, 4f, 14f)
            curveTo(4f, 10.45f, 6.31f, 7.44f, 9.53f, 6.4f)
            lineTo(7.05f, 3.92f)
            lineTo(8.46f, 2.51f)
            lineTo(10.94f, 4.99f)
            curveTo(11.29f, 4.91f, 11.64f, 4.86f, 12f, 4.86f)
            curveTo(12.36f, 4.86f, 12.71f, 4.91f, 13.06f, 4.99f)
            lineTo(15.54f, 2.51f)
            lineTo(16.95f, 3.92f)
            lineTo(14.47f, 6.4f)
            curveTo(16.63f, 7.1f, 18.44f, 8.57f, 19.59f, 10.46f)
            lineTo(20.38f, 8.57f)
            close()
            moveTo(10.59f, 15.41f)
            curveTo(10.96f, 15.78f, 11.48f, 16f, 12f, 16f)
            curveTo(13.1f, 16f, 14f, 15.1f, 14f, 14f)
            curveTo(14f, 13.48f, 13.78f, 12.96f, 13.41f, 12.59f)
            lineTo(16f, 10f)
            lineTo(10.59f, 15.41f)
            close()
        }
    }

    val Timer: ImageVector by lazy {
        materialIcon(name = "Timer") {
            moveTo(15f, 1f)
            lineTo(9f, 1f)
            lineTo(9f, 3f)
            lineTo(15f, 3f)
            close()
            moveTo(11f, 14f)
            lineTo(13f, 14f)
            lineTo(13f, 8f)
            lineTo(11f, 8f)
            close()
            moveTo(19.03f, 7.39f)
            lineTo(20.45f, 5.97f)
            curveTo(20.02f, 5.46f, 19.55f, 4.98f, 19.04f, 4.56f)
            lineTo(17.62f, 5.98f)
            curveTo(16.07f, 4.74f, 14.12f, 4f, 12f, 4f)
            curveTo(7.03f, 4f, 3f, 8.03f, 3f, 13f)
            curveTo(3f, 17.97f, 7.03f, 22f, 12f, 22f)
            curveTo(16.97f, 22f, 21f, 17.97f, 21f, 13f)
            curveTo(21f, 10.88f, 20.26f, 8.93f, 19.03f, 7.39f)
            close()
            moveTo(12f, 20f)
            curveTo(8.13f, 20f, 5f, 16.87f, 5f, 13f)
            curveTo(5f, 9.13f, 8.13f, 6f, 12f, 6f)
            curveTo(15.87f, 6f, 19f, 9.13f, 19f, 13f)
            curveTo(19f, 16.87f, 15.87f, 20f, 12f, 20f)
            close()
        }
    }

    val Layers: ImageVector by lazy {
        materialIcon(name = "Layers") {
            moveTo(11.99f, 18.54f)
            lineTo(4.62f, 12.81f)
            lineTo(3.41f, 13.75f)
            lineTo(12f, 20.42f)
            lineTo(20.59f, 13.75f)
            lineTo(19.38f, 12.81f)
            close()
            moveTo(12f, 16f)
            lineTo(20.55f, 9.35f)
            lineTo(21.76f, 8.41f)
            lineTo(12f, 0.81f)
            lineTo(2.24f, 8.41f)
            lineTo(3.45f, 9.35f)
            close()
        }
    }

    val Map: ImageVector by lazy {
        materialIcon(name = "Map") {
            moveTo(20.5f, 3f)
            lineTo(15f, 5.1f)
            lineTo(9f, 3f)
            lineTo(3.36f, 4.9f)
            curveTo(3.15f, 4.97f, 3f, 5.17f, 3f, 5.39f)
            lineTo(3f, 20.5f)
            curveTo(3f, 20.78f, 3.22f, 21f, 3.5f, 21f)
            lineTo(9f, 18.9f)
            lineTo(15f, 21f)
            lineTo(20.64f, 19.1f)
            curveTo(20.85f, 19.03f, 21f, 18.83f, 21f, 18.61f)
            lineTo(21f, 3.5f)
            curveTo(21f, 3.22f, 20.78f, 3f, 20.5f, 3f)
            close()
            moveTo(15f, 19f)
            lineTo(9f, 16.9f)
            lineTo(9f, 5f)
            lineTo(15f, 7.1f)
            close()
        }
    }

    private inline fun materialIcon(
        name: String,
        block: androidx.compose.ui.graphics.vector.PathBuilder.() -> Unit
    ): ImageVector = ImageVector.Builder(
        name = name,
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).path(
        fill = SolidColor(Color.Black),
        fillAlpha = 1f,
        stroke = null,
        strokeAlpha = 1f,
        strokeLineWidth = 1f,
        strokeLineCap = StrokeCap.Butt,
        strokeLineJoin = StrokeJoin.Miter,
        strokeLineMiter = 1f,
        pathFillType = PathFillType.NonZero,
        pathBuilder = block
    ).build()
}
