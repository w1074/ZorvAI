package com.ai.assistance.quro.genui.aiapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ai.assistance.quro.genui.sdk.style.EchoPalette

/**
 * GenUI Agent 应用主题 —— 与 Echo AI 宿主同一视觉体系
 *
 * 【为什么整体重写】
 * 原为「深青 Teal #0D9488 + 玫红 #E11D48」自成一套，而 SDK 侧主题又是 Material 默认紫，
 * 宿主 Echo AI 本体是「陶土 #C25A38 + 纸 #F4F1EA + 墨」。三套配色并行 →
 * GenUI Agent 打开后像另一个 App 贴进来的一块外来 UI，这就是「没有配色/没有审美」的根。
 *
 * 现在统一为 **陶土 / 纸 / 墨 / 鼠尾草 / 金**（取自 [EchoPalette]，与宿主 QuroTheme 同源），
 * 组件里 259 处 `MaterialTheme.colorScheme.*` 与 SDK 侧 `ctx.theme.colorScheme.*` 读到的是同一套颜色。
 */

// ========== 浅色：纸感底 + 白卡 + 陶土强调 ==========
private val LightColors = lightColorScheme(
    primary = EchoPalette.Terracotta,
    onPrimary = Color.White,
    primaryContainer = EchoPalette.TerracottaSoft,
    onPrimaryContainer = EchoPalette.TerracottaPress,
    inversePrimary = EchoPalette.TerracottaDark,

    secondary = EchoPalette.Sage,
    onSecondary = Color.White,
    secondaryContainer = EchoPalette.SageSoft,
    onSecondaryContainer = Color(0xFF3E4A36),

    tertiary = EchoPalette.Gold,
    onTertiary = Color.White,
    tertiaryContainer = EchoPalette.GoldSoft,
    onTertiaryContainer = EchoPalette.GoldPress,

    background = EchoPalette.Paper,
    onBackground = EchoPalette.Ink,
    surface = EchoPalette.Card,
    onSurface = EchoPalette.Ink,
    surfaceVariant = EchoPalette.Paper2,
    onSurfaceVariant = EchoPalette.InkSoft,
    surfaceTint = EchoPalette.Terracotta,

    // 纸感表面层叠（Lowest 最亮 → Highest 最暗）
    surfaceContainerLowest = Color.White,
    surfaceContainerLow = EchoPalette.PaperBright,
    surfaceContainer = EchoPalette.Paper,
    surfaceContainerHigh = EchoPalette.Paper2,
    surfaceContainerHighest = EchoPalette.Paper3,
    surfaceBright = EchoPalette.PaperBright,
    surfaceDim = EchoPalette.Paper3,

    outline = EchoPalette.Line,
    outlineVariant = EchoPalette.LineSoft,

    error = EchoPalette.ErrorWarm,
    onError = Color.White,
    errorContainer = EchoPalette.ErrorSoft,
    onErrorContainer = Color(0xFF7A2417),

    inverseSurface = EchoPalette.Ink,
    inverseOnSurface = EchoPalette.PaperBright,
    scrim = Color(0xFF000000)
)

// ========== 深色：暖近黑（保留纸的温度，不用纯黑）==========
private val DarkColors = darkColorScheme(
    primary = EchoPalette.TerracottaDark,
    onPrimary = EchoPalette.TerracottaDarkOn,
    primaryContainer = EchoPalette.TerracottaDarkContainer,
    onPrimaryContainer = EchoPalette.TerracottaSoft,
    inversePrimary = EchoPalette.Terracotta,

    secondary = EchoPalette.SageDark,
    onSecondary = EchoPalette.SageDarkOn,
    secondaryContainer = EchoPalette.SageDarkContainer,
    onSecondaryContainer = Color(0xFFC7D2BB),

    tertiary = EchoPalette.GoldDark,
    onTertiary = EchoPalette.GoldDarkOn,
    tertiaryContainer = EchoPalette.GoldDarkContainer,
    onTertiaryContainer = Color(0xFFF0DFAE),

    background = EchoPalette.BrandBackgroundDark,
    onBackground = EchoPalette.OnDark,
    surface = EchoPalette.SurfaceDark,
    onSurface = EchoPalette.OnDark,
    surfaceVariant = EchoPalette.ContainerDark,
    onSurfaceVariant = EchoPalette.OnDarkSoft,
    surfaceTint = EchoPalette.TerracottaDark,

    surfaceContainerLowest = EchoPalette.SurfaceLowestDark,
    surfaceContainerLow = EchoPalette.SurfaceLowDark,
    surfaceContainer = EchoPalette.SurfaceDark,
    surfaceContainerHigh = EchoPalette.SurfaceHighDark,
    surfaceContainerHighest = EchoPalette.SurfaceHighestDark,
    surfaceBright = EchoPalette.SurfaceHighDark,
    surfaceDim = EchoPalette.SurfaceLowestDark,

    outline = EchoPalette.LineDark,
    outlineVariant = EchoPalette.LineSoftDark,

    error = EchoPalette.ErrorDark,
    onError = EchoPalette.ErrorDarkOn,
    errorContainer = EchoPalette.ErrorDarkContainer,
    onErrorContainer = Color(0xFFF2C9C0),

    inverseSurface = EchoPalette.OnDark,
    inverseOnSurface = EchoPalette.BrandBackgroundDark,
    scrim = Color(0xFF000000)
)

// ========== 排版：标题衬线（墨问纸质风），正文无衬线 ==========
private val GenUIAppTypography = Typography(
    displayLarge = TextStyle(fontSize = 32.sp, fontWeight = FontWeight.SemiBold, lineHeight = 40.sp, letterSpacing = (-0.5).sp, fontFamily = FontFamily.Serif),
    displayMedium = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.SemiBold, lineHeight = 36.sp, letterSpacing = (-0.4).sp, fontFamily = FontFamily.Serif),
    displaySmall = TextStyle(fontSize = 26.sp, fontWeight = FontWeight.SemiBold, lineHeight = 34.sp, letterSpacing = (-0.4).sp, fontFamily = FontFamily.Serif),
    headlineLarge = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.SemiBold, lineHeight = 32.sp, letterSpacing = (-0.2).sp, fontFamily = FontFamily.Serif),
    headlineMedium = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.SemiBold, lineHeight = 28.sp, letterSpacing = (-0.2).sp, fontFamily = FontFamily.Serif),
    headlineSmall = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 26.sp, fontFamily = FontFamily.Serif),
    titleLarge = TextStyle(fontSize = 18.sp, fontWeight = FontWeight.SemiBold, lineHeight = 26.sp, fontFamily = FontFamily.Serif),
    titleMedium = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Medium, lineHeight = 24.sp),
    titleSmall = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, lineHeight = 20.sp),
    bodyLarge = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, lineHeight = 23.sp, letterSpacing = 0.1.sp),
    bodyMedium = TextStyle(fontSize = 13.sp, fontWeight = FontWeight.Normal, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    bodySmall = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Normal, lineHeight = 18.sp),
    labelLarge = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Medium, lineHeight = 20.sp, letterSpacing = 0.1.sp),
    labelMedium = TextStyle(fontSize = 12.sp, fontWeight = FontWeight.Medium, lineHeight = 16.sp, letterSpacing = 0.3.sp),
    labelSmall = TextStyle(fontSize = 11.sp, fontWeight = FontWeight.Medium, lineHeight = 14.sp, letterSpacing = 0.5.sp)
)

/**
 * GenUI 应用主题
 *
 * @param darkTheme 是否使用深色主题，默认跟随系统
 * @param content 主题内容
 */
@Composable
fun GenUITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = GenUIAppTypography,
        content = content
    )
}
