package com.example.styleai.core.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Color definitions based on premium neutral palette (Earth, Sand, Creams)
val WarmSand = Color(0xFFF3EFE9)
val TerracottaPoint = Color(0xFF8D5B4C)
val DeepCharcoal = Color(0xFF262626)
val SoftMoss = Color(0xFF5C6B5E)

val LightColors = lightColorScheme(
    primary = TerracottaPoint,
    onPrimary = Color.White,
    primaryContainer = Color(0xFFF9EFEA),
    onPrimaryContainer = Color(0xFF2E1A14),
    secondary = SoftMoss,
    onSecondary = Color.White,
    background = WarmSand,
    onBackground = DeepCharcoal,
    surface = Color.White,
    onSurface = DeepCharcoal,
    surfaceVariant = Color(0xFFEBE5DF),
    onSurfaceVariant = Color(0xFF4C4742)
)

val DarkColors = darkColorScheme(
    primary = Color(0xFFDCA494),
    onPrimary = Color(0xFF4E2A1F),
    primaryContainer = Color(0xFF2F1A14),
    onPrimaryContainer = Color(0xFFF9EFEA),
    secondary = Color(0xFFA2B5A5),
    onSecondary = Color(0xFF2E3B30),
    background = DeepCharcoal,
    onBackground = WarmSand,
    surface = Color(0xFF1F1F1F),
    onSurface = WarmSand,
    surfaceVariant = Color(0xFF4C4742),
    onSurfaceVariant = Color(0xFFDBCECE)
)

val Typography = Typography(
    displayMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
        letterSpacing = (-0.5).sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        letterSpacing = 0.25.sp
    ),
    labelLarge = TextStyle(
        fontFamily = FontFamily.Monospace,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        letterSpacing = 1.sp
    )
)

@Composable
fun StyleAITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
