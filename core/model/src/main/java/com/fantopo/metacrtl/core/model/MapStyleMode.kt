package com.fantopo.metacrtl.core.model

enum class MapStyleMode(val title: String, val description: String) {
    HYBRID("Hybrid Mode", "Satellite imagery with street and landmark overlays"),
    NIGHT("Night Mode", "Dark styled map with high contrast glow routes"),
    TRAFFIC("Traffic Mode", "Real-time simulated traffic congestion indicators")
}
