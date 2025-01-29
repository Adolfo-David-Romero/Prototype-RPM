package sheridan.adolfo_david_romero.prototype_device.ui.theme
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import sheridan.adolfo_david_romero.prototype_device.ui.theme.backgroundDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.backgroundDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.backgroundDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.backgroundLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.backgroundLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.backgroundLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.errorLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseOnSurfaceDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseOnSurfaceDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseOnSurfaceDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseOnSurfaceLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseOnSurfaceLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseOnSurfaceLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inversePrimaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inversePrimaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inversePrimaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inversePrimaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inversePrimaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inversePrimaryLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseSurfaceDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseSurfaceDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseSurfaceDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseSurfaceLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseSurfaceLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.inverseSurfaceLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onBackgroundDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onBackgroundDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onBackgroundDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onBackgroundLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onBackgroundLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onBackgroundLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onErrorLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onPrimaryLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSecondaryLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceVariantDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceVariantDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceVariantDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceVariantLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceVariantLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onSurfaceVariantLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.onTertiaryLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineVariantDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineVariantDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineVariantDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineVariantLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineVariantLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.outlineVariantLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.primaryLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.scrimDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.scrimDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.scrimDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.scrimLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.scrimLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.scrimLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.secondaryLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceBrightDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceBrightDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceBrightDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceBrightLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceBrightLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceBrightLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighestDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighestDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighestDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighestLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighestLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerHighestLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowestDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowestDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowestDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowestLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowestLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceContainerLowestLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDimDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDimDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDimDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDimLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDimLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceDimLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceVariantDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceVariantDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceVariantDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceVariantLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceVariantLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.surfaceVariantLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryContainerDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryContainerDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryContainerDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryContainerLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryContainerLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryContainerLightMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryDark
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryDarkHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryDarkMediumContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryLight
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryLightHighContrast
import sheridan.adolfo_david_romero.prototype_device.ui.theme.tertiaryLightMediumContrast

private val lightScheme = lightColorScheme(
    primary = primaryLight,
    onPrimary = onPrimaryLight,
    primaryContainer = primaryContainerLight,
    onPrimaryContainer = onPrimaryContainerLight,
    secondary = secondaryLight,
    onSecondary = onSecondaryLight,
    secondaryContainer = secondaryContainerLight,
    onSecondaryContainer = onSecondaryContainerLight,
    tertiary = tertiaryLight,
    onTertiary = onTertiaryLight,
    tertiaryContainer = tertiaryContainerLight,
    onTertiaryContainer = onTertiaryContainerLight,
    error = errorLight,
    onError = onErrorLight,
    errorContainer = errorContainerLight,
    onErrorContainer = onErrorContainerLight,
    background = backgroundLight,
    onBackground = onBackgroundLight,
    surface = surfaceLight,
    onSurface = onSurfaceLight,
    surfaceVariant = surfaceVariantLight,
    onSurfaceVariant = onSurfaceVariantLight,
    outline = outlineLight,
    outlineVariant = outlineVariantLight,
    scrim = scrimLight,
    inverseSurface = inverseSurfaceLight,
    inverseOnSurface = inverseOnSurfaceLight,
    inversePrimary = inversePrimaryLight,
    surfaceDim = surfaceDimLight,
    surfaceBright = surfaceBrightLight,
    surfaceContainerLowest = surfaceContainerLowestLight,
    surfaceContainerLow = surfaceContainerLowLight,
    surfaceContainer = surfaceContainerLight,
    surfaceContainerHigh = surfaceContainerHighLight,
    surfaceContainerHighest = surfaceContainerHighestLight,
)

private val darkScheme = darkColorScheme(
    primary = primaryDark,
    onPrimary = onPrimaryDark,
    primaryContainer = primaryContainerDark,
    onPrimaryContainer = onPrimaryContainerDark,
    secondary = secondaryDark,
    onSecondary = onSecondaryDark,
    secondaryContainer = secondaryContainerDark,
    onSecondaryContainer = onSecondaryContainerDark,
    tertiary = tertiaryDark,
    onTertiary = onTertiaryDark,
    tertiaryContainer = tertiaryContainerDark,
    onTertiaryContainer = onTertiaryContainerDark,
    error = errorDark,
    onError = onErrorDark,
    errorContainer = errorContainerDark,
    onErrorContainer = onErrorContainerDark,
    background = backgroundDark,
    onBackground = onBackgroundDark,
    surface = surfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = surfaceVariantDark,
    onSurfaceVariant = onSurfaceVariantDark,
    outline = outlineDark,
    outlineVariant = outlineVariantDark,
    scrim = scrimDark,
    inverseSurface = inverseSurfaceDark,
    inverseOnSurface = inverseOnSurfaceDark,
    inversePrimary = inversePrimaryDark,
    surfaceDim = surfaceDimDark,
    surfaceBright = surfaceBrightDark,
    surfaceContainerLowest = surfaceContainerLowestDark,
    surfaceContainerLow = surfaceContainerLowDark,
    surfaceContainer = surfaceContainerDark,
    surfaceContainerHigh = surfaceContainerHighDark,
    surfaceContainerHighest = surfaceContainerHighestDark,
)

private val mediumContrastLightColorScheme = lightColorScheme(
    primary = primaryLightMediumContrast,
    onPrimary = onPrimaryLightMediumContrast,
    primaryContainer = primaryContainerLightMediumContrast,
    onPrimaryContainer = onPrimaryContainerLightMediumContrast,
    secondary = secondaryLightMediumContrast,
    onSecondary = onSecondaryLightMediumContrast,
    secondaryContainer = secondaryContainerLightMediumContrast,
    onSecondaryContainer = onSecondaryContainerLightMediumContrast,
    tertiary = tertiaryLightMediumContrast,
    onTertiary = onTertiaryLightMediumContrast,
    tertiaryContainer = tertiaryContainerLightMediumContrast,
    onTertiaryContainer = onTertiaryContainerLightMediumContrast,
    error = errorLightMediumContrast,
    onError = onErrorLightMediumContrast,
    errorContainer = errorContainerLightMediumContrast,
    onErrorContainer = onErrorContainerLightMediumContrast,
    background = backgroundLightMediumContrast,
    onBackground = onBackgroundLightMediumContrast,
    surface = surfaceLightMediumContrast,
    onSurface = onSurfaceLightMediumContrast,
    surfaceVariant = surfaceVariantLightMediumContrast,
    onSurfaceVariant = onSurfaceVariantLightMediumContrast,
    outline = outlineLightMediumContrast,
    outlineVariant = outlineVariantLightMediumContrast,
    scrim = scrimLightMediumContrast,
    inverseSurface = inverseSurfaceLightMediumContrast,
    inverseOnSurface = inverseOnSurfaceLightMediumContrast,
    inversePrimary = inversePrimaryLightMediumContrast,
    surfaceDim = surfaceDimLightMediumContrast,
    surfaceBright = surfaceBrightLightMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestLightMediumContrast,
    surfaceContainerLow = surfaceContainerLowLightMediumContrast,
    surfaceContainer = surfaceContainerLightMediumContrast,
    surfaceContainerHigh = surfaceContainerHighLightMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestLightMediumContrast,
)

private val highContrastLightColorScheme = lightColorScheme(
    primary = primaryLightHighContrast,
    onPrimary = onPrimaryLightHighContrast,
    primaryContainer = primaryContainerLightHighContrast,
    onPrimaryContainer = onPrimaryContainerLightHighContrast,
    secondary = secondaryLightHighContrast,
    onSecondary = onSecondaryLightHighContrast,
    secondaryContainer = secondaryContainerLightHighContrast,
    onSecondaryContainer = onSecondaryContainerLightHighContrast,
    tertiary = tertiaryLightHighContrast,
    onTertiary = onTertiaryLightHighContrast,
    tertiaryContainer = tertiaryContainerLightHighContrast,
    onTertiaryContainer = onTertiaryContainerLightHighContrast,
    error = errorLightHighContrast,
    onError = onErrorLightHighContrast,
    errorContainer = errorContainerLightHighContrast,
    onErrorContainer = onErrorContainerLightHighContrast,
    background = backgroundLightHighContrast,
    onBackground = onBackgroundLightHighContrast,
    surface = surfaceLightHighContrast,
    onSurface = onSurfaceLightHighContrast,
    surfaceVariant = surfaceVariantLightHighContrast,
    onSurfaceVariant = onSurfaceVariantLightHighContrast,
    outline = outlineLightHighContrast,
    outlineVariant = outlineVariantLightHighContrast,
    scrim = scrimLightHighContrast,
    inverseSurface = inverseSurfaceLightHighContrast,
    inverseOnSurface = inverseOnSurfaceLightHighContrast,
    inversePrimary = inversePrimaryLightHighContrast,
    surfaceDim = surfaceDimLightHighContrast,
    surfaceBright = surfaceBrightLightHighContrast,
    surfaceContainerLowest = surfaceContainerLowestLightHighContrast,
    surfaceContainerLow = surfaceContainerLowLightHighContrast,
    surfaceContainer = surfaceContainerLightHighContrast,
    surfaceContainerHigh = surfaceContainerHighLightHighContrast,
    surfaceContainerHighest = surfaceContainerHighestLightHighContrast,
)

private val mediumContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkMediumContrast,
    onPrimary = onPrimaryDarkMediumContrast,
    primaryContainer = primaryContainerDarkMediumContrast,
    onPrimaryContainer = onPrimaryContainerDarkMediumContrast,
    secondary = secondaryDarkMediumContrast,
    onSecondary = onSecondaryDarkMediumContrast,
    secondaryContainer = secondaryContainerDarkMediumContrast,
    onSecondaryContainer = onSecondaryContainerDarkMediumContrast,
    tertiary = tertiaryDarkMediumContrast,
    onTertiary = onTertiaryDarkMediumContrast,
    tertiaryContainer = tertiaryContainerDarkMediumContrast,
    onTertiaryContainer = onTertiaryContainerDarkMediumContrast,
    error = errorDarkMediumContrast,
    onError = onErrorDarkMediumContrast,
    errorContainer = errorContainerDarkMediumContrast,
    onErrorContainer = onErrorContainerDarkMediumContrast,
    background = backgroundDarkMediumContrast,
    onBackground = onBackgroundDarkMediumContrast,
    surface = surfaceDarkMediumContrast,
    onSurface = onSurfaceDarkMediumContrast,
    surfaceVariant = surfaceVariantDarkMediumContrast,
    onSurfaceVariant = onSurfaceVariantDarkMediumContrast,
    outline = outlineDarkMediumContrast,
    outlineVariant = outlineVariantDarkMediumContrast,
    scrim = scrimDarkMediumContrast,
    inverseSurface = inverseSurfaceDarkMediumContrast,
    inverseOnSurface = inverseOnSurfaceDarkMediumContrast,
    inversePrimary = inversePrimaryDarkMediumContrast,
    surfaceDim = surfaceDimDarkMediumContrast,
    surfaceBright = surfaceBrightDarkMediumContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkMediumContrast,
    surfaceContainerLow = surfaceContainerLowDarkMediumContrast,
    surfaceContainer = surfaceContainerDarkMediumContrast,
    surfaceContainerHigh = surfaceContainerHighDarkMediumContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkMediumContrast,
)

private val highContrastDarkColorScheme = darkColorScheme(
    primary = primaryDarkHighContrast,
    onPrimary = onPrimaryDarkHighContrast,
    primaryContainer = primaryContainerDarkHighContrast,
    onPrimaryContainer = onPrimaryContainerDarkHighContrast,
    secondary = secondaryDarkHighContrast,
    onSecondary = onSecondaryDarkHighContrast,
    secondaryContainer = secondaryContainerDarkHighContrast,
    onSecondaryContainer = onSecondaryContainerDarkHighContrast,
    tertiary = tertiaryDarkHighContrast,
    onTertiary = onTertiaryDarkHighContrast,
    tertiaryContainer = tertiaryContainerDarkHighContrast,
    onTertiaryContainer = onTertiaryContainerDarkHighContrast,
    error = errorDarkHighContrast,
    onError = onErrorDarkHighContrast,
    errorContainer = errorContainerDarkHighContrast,
    onErrorContainer = onErrorContainerDarkHighContrast,
    background = backgroundDarkHighContrast,
    onBackground = onBackgroundDarkHighContrast,
    surface = surfaceDarkHighContrast,
    onSurface = onSurfaceDarkHighContrast,
    surfaceVariant = surfaceVariantDarkHighContrast,
    onSurfaceVariant = onSurfaceVariantDarkHighContrast,
    outline = outlineDarkHighContrast,
    outlineVariant = outlineVariantDarkHighContrast,
    scrim = scrimDarkHighContrast,
    inverseSurface = inverseSurfaceDarkHighContrast,
    inverseOnSurface = inverseOnSurfaceDarkHighContrast,
    inversePrimary = inversePrimaryDarkHighContrast,
    surfaceDim = surfaceDimDarkHighContrast,
    surfaceBright = surfaceBrightDarkHighContrast,
    surfaceContainerLowest = surfaceContainerLowestDarkHighContrast,
    surfaceContainerLow = surfaceContainerLowDarkHighContrast,
    surfaceContainer = surfaceContainerDarkHighContrast,
    surfaceContainerHigh = surfaceContainerHighDarkHighContrast,
    surfaceContainerHighest = surfaceContainerHighestDarkHighContrast,
)

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified, Color.Unspecified, Color.Unspecified, Color.Unspecified
)

@Composable
fun PrototypeDeviceTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable() () -> Unit
) {
  val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
          val context = LocalContext.current
          if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      
      darkTheme -> darkScheme
      else -> lightScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = AppTypography,
    content = content
  )
}

