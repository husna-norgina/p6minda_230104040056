package id.antasari.p6minda_230104040056.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

private val LightColors = lightColorScheme(
    primary = md_theme_primary,
    onPrimary = md_theme_onPrimary,
    secondary = md_theme_secondary,
    onSecondary = md_theme_onSecondary,
    background = md_theme_background,
    onBackground = md_theme_onBackground,
    surface = md_theme_surface,
    onSurface = md_theme_onSurface,
    surfaceVariant = md_theme_surfaceVariant,
    onSurfaceVariant = md_theme_onSurfaceVariant,
    error = md_theme_error,
    onError = md_theme_onError
)

private val DarkColors = darkColorScheme(
    primary = md_theme_primary,
    onPrimary = md_theme_onPrimary,
    secondary = md_theme_secondary,
    onSecondary = md_theme_onSecondary,
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE6E1E5),
    surface = Color(0xFF1C1B1F),
    onSurface = Color(0xFFE6E1E5),
    surfaceVariant = Color(0xFF49454F),
    onSurfaceVariant = Color(0xFFCAC4D0),
    error = md_theme_error,
    onError = md_theme_onError
)

@Composable
fun MindaTheme(
    useDarkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colors = if (useDarkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = MindaTypography,
        content = content
    )
}
