# Changelog

All notable changes to TourCompose will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [2.0.0] - 2024-XX-XX

### 🎯 Major Changes - Breaking Changes

#### Modular Architecture

- **BREAKING:** Split library into two modules for better flexibility:
    - `TourCompose` - Design system agnostic base library
    - `TourCompose-Material3` - Material3 integration module

#### Dependencies

- **BREAKING:** Removed Material3 dependency from base `TourCompose` module
- **NEW:** Added separate `TourCompose-Material3` module with Material3 dependencies
- **CHANGE:** Base library now uses `androidx.foundation` instead of Material3 components

#### Component Changes

- **BREAKING:** `BubbleContentBasicSettings` now uses `BasicText` instead of Material3 `Text`
- **BREAKING:** Button components in base library use custom styling instead of Material3
- **BREAKING:** Color references changed from `MaterialTheme.colorScheme` to basic `Color` values in
  base library

### ✨ New Features

#### TourCompose Base (Design System Agnostic)

- **NEW:** Fully design system agnostic implementation
- **NEW:** Works with any custom design system or theming approach
- **NEW:** Manual color control for complete customization
- **NEW:** No Material3 dependencies - lighter bundle size

#### TourCompose-Material3 Integration

- **NEW:** `TourComposeMaterial3` wrapper component
- **NEW:** `bubbleContentMaterial3()` function for automatic Material3 theming
- **NEW:** Automatic light/dark mode adaptation
- **NEW:** Seamless integration with MaterialTheme color schemes

#### Demo Architecture

- **NEW:** Separate demo controllers per tab:
    - `BasicDemoController` - Design system agnostic demos
    - `Material3DemoController` - Material3 integration demos
    - `CustomDemoController` - Advanced customization demos
- **NEW:** Tab-based demo organization in sample app
- **NEW:** Clear separation of use cases and examples

### 🔄 Migration Guide

#### From v1.x to v2.0

**If you want Material3 integration (similar to v1.x behavior):**

```kotlin
// Old v1.x
dependencies {
    implementation("com.github.tonyakitori:TourCompose:1.x.x")
}

// New v2.0 - Material3 integration
dependencies {
    implementation("com.github.tonyakitori:TourCompose-Material3:2.0.0")
}

// Change in composable usage:
// Old:
TourCompose(
    componentRectArea = rect,
    bubbleContentSettings = settings
)

// New:
TourComposeMaterial3(
    componentRectArea = rect,
    bubbleContentSettings = settings
)
```

**If you want design system agnostic approach:**

```kotlin
// New v2.0 - Base library only
dependencies {
    implementation("com.github.tonyakitori:TourCompose:2.0.0")
}

// Usage remains the same for base TourCompose:
TourCompose(
    componentRectArea = rect,
    bubbleContentSettings = settings,
    tourComposeProperties = TourComposeProperties.getDefaultInstance()
)
```

#### Bubble Content Migration

**Material3 approach (recommended for Material3 apps):**

```kotlin
// Old:
bubbleContentBasicSettings(
    title = "Title",
    description = "Description",
    // ...
)

// New - Material3:
bubbleContentMaterial3(
    title = "Title",
    description = "Description",
    // ...
)
```

**Design system agnostic approach:**

```kotlin
// Still works, but now uses basic components:
bubbleContentBasicSettings(
    title = "Title",
    description = "Description",
    colors = customBubbleContentColors(), // Optional custom colors
    // ...
)
```

### 📦 Dependencies

#### TourCompose Base

- `androidx.compose.foundation` (replaces Material3 dependency)
- `androidx.compose.ui`
- Removed: `androidx.compose.material3`

#### TourCompose-Material3

- All base dependencies
- `androidx.compose.material3`
- `androidx.compose.material`

### 🏗️ Architecture Changes

- **NEW:** Modular package structure:
    - `basicdemo/` - Design system agnostic examples
    - `material3/` - Material3 integration examples
    - `custom/` - Advanced customization examples
- **IMPROVED:** Controller architecture with specific implementations per demo type
- **IMPROVED:** Clear separation of concerns between base and Material3 functionality

### 📱 Demo App

- **NEW:** Three distinct demo tabs showcasing different approaches
- **NEW:** Real-world examples of both base and Material3 usage
- **IMPROVED:** Better organized codebase for learning and reference

---

## [1.x.x] - Previous Versions

Previous versions were tightly coupled to Material3. See git history for detailed changes in 1.x
versions.

### Migration from 1.x

Version 2.0 represents a major architectural shift towards modularity and design system flexibility.
Choose the appropriate module based on your needs:

- Use `TourCompose-Material3` for drop-in replacement of 1.x behavior
- Use `TourCompose` base for maximum flexibility and custom design systems
- Use both modules for mixed usage scenarios

---

## Legend

- 🎯 **BREAKING:** Breaking changes requiring code updates
- ✨ **NEW:** New features and additions
- 🔄 **CHANGE:** Changes to existing functionality
- 🐛 **FIX:** Bug fixes
- 📦 **DEPS:** Dependency changes
- 🏗️ **ARCH:** Architecture changes
- 📱 **DEMO:** Demo app changes
- 🌍 **I18N:** Internationalization changes