package com.oscar.hydrosense.theme


import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.oscar.hydrosense.R

// Set of Material typography styles to start with

val funnelSans = FontFamily(
    Font(R.font.funnelsans_bold, FontWeight.Bold),
    Font(R.font.funnelsans_regular, FontWeight.Normal),
    Font(R.font.funnelsans_semibold, FontWeight.SemiBold),
    Font(R.font.funnelsans_medium, FontWeight.Medium),
    Font(R.font.funnelsans_light, FontWeight.Light)
);

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)