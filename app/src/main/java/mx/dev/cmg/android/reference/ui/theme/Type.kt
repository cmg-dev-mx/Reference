package mx.dev.cmg.android.reference.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.dev.cmg.android.reference.R

// Kode Mono Font Family from Google Fonts
val KodeMonoFontFamily = FontFamily(
    listOf(
        Font(
            resId = R.font.kode_mono_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resId = R.font.kode_mono_bold,
            weight = FontWeight.Bold
        )
    )
)

// Reference App Typography based on Figma Design
val ReferenceTypography = Typography(
    // Title: Font(family: "Kode Mono", style: Bold, size: 24, weight: 700, lineHeight: 100)
    titleLarge = TextStyle(
        fontFamily = KodeMonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 24.sp, // 100% line height
        letterSpacing = 0.sp
    ),
    
    // Content: Font(family: "Kode Mono", style: Regular, size: 12, weight: 400, lineHeight: 100)
    bodySmall = TextStyle(
        fontFamily = KodeMonoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 12.sp, // 100% line height
        letterSpacing = 0.sp
    ),
    
    // Label: Font(family: "Kode Mono", style: Bold, size: 16, weight: 700, lineHeight: 100)
    labelLarge = TextStyle(
        fontFamily = KodeMonoFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 16.sp, // 100% line height
        letterSpacing = 0.sp
    ),
    
    // Default body style
    bodyLarge = TextStyle(
        fontFamily = KodeMonoFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

// Backward compatibility
val Typography = ReferenceTypography