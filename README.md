# Fantopo Maps (`com.fantopo.metacrtl`)

A fake GPS Android application built with **Jetpack Compose Material 3**, **Kotlin**, **Coroutines & StateFlow**, and **Clean Architecture (MVVM + Use Cases + Repositories)**.

---

## 📱 Project Specifications

- **App Name**: Fantopo Maps
- **Package Name**: `com.fantopo.metacrtl`
- **Language**: Kotlin 2.1.0
- **Build System**: Gradle 8.11.1 (Kotlin DSL - KTS) with Version Catalog (`libs.versions.toml`)
- **JDK**: Java 17
- **SDK Configuration**:
  - `minSdk`: 28 (Android 9.0 Pie)
  - `targetSdk`: 37
  - `compileSdk`: 37
- **UI Toolkit**: Jetpack Compose with Material 3

---

## 🏛 Architecture & Module Layout

The project follows modern modular Android architecture standards:

```text
AmrosolMaps/
├── app/                  # Application initialization, dependency container, MainActivity & Theme
│   ├── src/main/java/com/fantopo/metacrtl/
│   │   ├── di/AppContainer.kt
│   │   ├── ui/theme/Theme.kt
│   │   ├── FantopoApp.kt
│   │   └── MainActivity.kt
│   └── src/test/...
├── core/
│   ├── model/            # Pure domain models (LocationPoint, AppSettings, SavedLocation, etc.)
│   ├── data/             # Repositories & Location Simulation Engine (FakeGpsManager, LocationRandomizer)
│   └── domain/           # Clean domain use cases for Locations, Settings, GPS State, Providers
└── feature/
    └── map/              # Full-screen Compose Map UI, In-App Overlays, Bottom Menu Bar & Dialogs
        ├── src/main/java/com/fantopo/metacrtl/feature/map/
        │   ├── dialog/   # Save, Favorite, History, Settings, Provider Service dialogs
        │   ├── model/    # UI state models and dialog navigation types
        │   ├── overlay/  # Floating overlay widget with downward expandable controls
        │   ├── ui/       # MapSurfaceView, InAppControlsOverlay, BottomMenuBar, MapScreen, NoBorderTextField
        │   └── viewmodel/# MapViewModel orchestrating UI and business use cases
        └── src/test/...  # Feature unit tests
```

---

## 🚀 Features & Components

### 1. Full-Screen Map UI & In-App Overlays
- Full-screen interactive map surface with canvas-rendered grid, crosshair target, and draggable pin marker.
- Left-side **Play/Stop toggle button** to start or stop simulated GPS broadcasting.
- Center location button to re-center the map viewport to current pinned coordinates.
- Single-button **Zoom cycle toggle** (12x → 15x → 18x → 12x).
- Single-button **Fullscreen / Normal screen toggle** (hides or shows HUD overlays).
- **Delete / Reset button** to reset marker coordinates to origin.

### 2. Floating Overlay Widget (Floating Mode)
- When enabled in Settings, renders an on-screen floating logo badge.
- Clicking the logo expands downward to reveal:
  - **Refresh**: Instantly recalculates realistic simulated coordinates and notifies subscribers.
  - **Stop**: Halts fake GPS broadcasting.

### 3. Bottom Menu Bar & Dialogs
- **Save Dialog**:
  - Input fields: Latitude, Longitude, Location Name.
  - Vertically arranged with custom leading icons (`LocationOn`, `Explore`, `Edit`).
  - Borderless input styling (`NoBorderTextField`).
  - Persists saved locations into repository storage.
- **Favorite Dialog**:
  - Displays saved favorite locations as card items.
  - Each card shows location name, small `(lat, long)` subtitle, and action buttons (`Edit`, `Move`, `Delete`).
  - `Move` action immediately updates map marker to the target coordinate.
- **History Dialog**:
  - Automatically records marker movements and selections.
  - Displays history items with fallback title `Fantopo History` when unnamed.
  - Action buttons: `Save` to favorites, `Move` marker to coordinate, `Delete` entry.
- **Settings Dialog**:
  - **Floating Mode** toggle.
  - **Fused Mode** toggle.
  - **Random Coordinate** toggle for realistic micro-jitter offsets.
  - **Random Accuracy** toggle with dual-thumb range slider (Range: `0.0`–`5.0`, Default min/max: `5.0`).
  - **Random Altitude** toggle with dual-thumb range slider (Range: `0.0`–`75.0`, Default: `0.0`–`15.0`).
  - **Random Bearing** toggle (`0°`–`360°`).
  - **Random Speed** toggle with dual-thumb range slider (Range: `0.0`–`55.0`, Default: `1.0`–`5.0`).
  - **Refresh Time** timing control: Slider (`0`–`1300` ms) and borderless numeric input field.
  - **Maps Style**: Combinable multi-select modes (`Hybrid`, `Night`, `Traffic`).
- **Provider Service Menu**:
  - Supports quick-switching provider profiles for **Grab**, **Gojek**, and **Shopeefood**.
  - Provides extensible repository and domain hooks for future provider-specific dispatch algorithms.

---

## 🗺 Map Surface & Mock GPS Simulation Architecture

- **Map Surface Abstraction (`MapSurfaceView`)**:
  - Implements a self-contained canvas map renderer with dynamic grid coordinates, bearing compass, style color overlays (Night, Hybrid, Traffic), and interactive pin markers.
  - Includes clear architectural integration points for swapping in the Google Maps Compose SDK (`com.google.maps.android:maps-compose`) when API keys and Play Services are configured.
- **Simulation Engine (`FakeGpsManager` & `LocationRandomizer`)**:
  - Runs a background coroutine timer at the user-configured refresh rate (`0`–`1300` ms).
  - Calculates realistic geodesic jitter, randomized speed, altitude, accuracy, and bearing according to active setting constraints.
  - Ready for integration with Android's `LocationManager.setTestProviderLocation()` mock provider APIs.

---

## 🛠 How to Build and Run

### Prerequisites
- JDK 17
- Android SDK with Platform 37 (`compileSdk = 37`, `minSdk = 28`)
- Android Studio Ladybug / Meerkat or Gradle CLI

### Build Commands
```bash
# Build debug APK
./gradlew assembleDebug

# Run unit tests across all modules
./gradlew testDebugUnitTest

# Install on connected device/emulator
./gradlew installDebug
```

---

## 🧪 Testing

Comprehensive unit tests cover all layers:
- `:core:model` - Domain models and default settings invariants
- `:core:data` - Repositories, `LocationRandomizer`, and `FakeGpsManager` simulation timing
- `:core:domain` - Use cases for locations, settings, GPS lifecycle, and provider selection
- `:feature:map` - `MapViewModel` state mutations, dialog flows, and control interactions
- `:app` - `AppContainer` dependency graph verification
