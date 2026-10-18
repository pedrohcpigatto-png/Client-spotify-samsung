package com.example.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.model.SamsungPalette

data class OneUiThemeColors(
    val primaryAccent: Color,
    val primaryContainer: Color,
    val secondaryAccent: Color,
    val cardSurface: Color,
    val cardBorder: Color,
    val background: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color
)

val LocalOneUiThemeColors = staticCompositionLocalOf {
    OneUiThemeColors(
        primaryAccent = SpotifyGreen,
        primaryContainer = SpotifyGreen.copy(alpha = 0.15f),
        secondaryAccent = GalaxyElectricBlue,
        cardSurface = OneUiSurfaceVariant,
        cardBorder = OneUiCardBorder,
        background = OneUiDarkBackground,
        textPrimary = OneUiTextPrimary,
        textSecondary = OneUiTextSecondary,
        textMuted = OneUiTextMuted
    )
}

@Composable
fun GalaxyMusicTheme(
    palette: SamsungPalette = SamsungPalette.SYSTEM_DYNAMIC,
    pureBlackAmoled: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val baseScheme: ColorScheme = when (palette) {
        SamsungPalette.SYSTEM_DYNAMIC -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                dynamicDarkColorScheme(context)
            } else {
                createSamsungPaletteScheme(GalaxyElectricBlue)
            }
        }
        SamsungPalette.SAMSUNG_BLUE -> createSamsungPaletteScheme(Color(0xFF2B82F6))
        SamsungPalette.SPOTIFY_GREEN -> createSamsungPaletteScheme(Color(0xFF1ED760))
        SamsungPalette.GALAXY_PURPLE -> createSamsungPaletteScheme(Color(0xFFA855F7))
        SamsungPalette.GALAXY_AMBER -> createSamsungPaletteScheme(Color(0xFFF59E0B))
        SamsungPalette.GALAXY_MINT -> createSamsungPaletteScheme(Color(0xFF10B981))
    }

    val finalScheme = if (pureBlackAmoled) {
        baseScheme.copy(
            background = OneUiPureBlack,
            surface = Color(0xFF0B0E14),
            surfaceVariant = Color(0xFF131822)
        )
    } else {
        baseScheme.copy(
            background = OneUiDarkBackground,
            surface = OneUiSurface,
            surfaceVariant = OneUiSurfaceVariant
        )
    }

    val oneUiColors = OneUiThemeColors(
        primaryAccent = finalScheme.primary,
        primaryContainer = finalScheme.primaryContainer,
        secondaryAccent = finalScheme.secondary,
        cardSurface = finalScheme.surfaceVariant,
        cardBorder = OneUiCardBorder,
        background = finalScheme.background,
        textPrimary = OneUiTextPrimary,
        textSecondary = OneUiTextSecondary,
        textMuted = OneUiTextMuted
    )

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                window.statusBarColor = finalScheme.background.toArgb()
                window.navigationBarColor = finalScheme.background.toArgb()
                val controller = WindowCompat.getInsetsController(window, view)
                controller.isAppearanceLightStatusBars = false
                controller.isAppearanceLightNavigationBars = false
            }
        }
    }

    CompositionLocalProvider(LocalOneUiThemeColors provides oneUiColors) {
        MaterialTheme(
            colorScheme = finalScheme,
            typography = Typography,
            content = content
        )
    }
}

private fun createSamsungPaletteScheme(accent: Color): ColorScheme {
    return darkColorScheme(
        primary = accent,
        onPrimary = OneUiPureBlack,
        primaryContainer = accent.copy(alpha = 0.2f),
        onPrimaryContainer = accent,
        secondary = GalaxyElectricBlue,
        onSecondary = OneUiPureBlack,
        secondaryContainer = GalaxyElectricBlue.copy(alpha = 0.18f),
        onSecondaryContainer = GalaxyElectricBlue,
        tertiary = GalaxyAmber,
        background = OneUiDarkBackground,
        onBackground = OneUiTextPrimary,
        surface = OneUiSurface,
        onSurface = OneUiTextPrimary,
        surfaceVariant = OneUiSurfaceVariant,
        onSurfaceVariant = OneUiTextSecondary,
        outline = OneUiCardBorder
    )
}
