package typography

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

val DefaultTextStyle = CineVerseTextStyle(
    display = TextStyle(
        fontFamily = Manrope,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp
    ),

    title = TitleStyle(
        extraLarge = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp
        ),
        large = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        medium = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp
        ),
        small = TextStyle(
            fontFamily = Manrope,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
    ),

    body = BodyAndLabelStyle(
        large = FontWeightStyle(
            regular = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp
            ),
            medium = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp
            ),
            semiBold = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        ),
        medium = FontWeightStyle(
            regular = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            ),
            medium = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            ),
            semiBold = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
        ),
        small = FontWeightStyle(
            regular = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        )
    ),

    label = BodyAndLabelStyle(
        large = FontWeightStyle(
            regular = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        ),
        medium = FontWeightStyle(
            regular = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        ),
        small = FontWeightStyle(
            regular = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            ),
            medium = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            ),
            semiBold = TextStyle(
                fontFamily = Manrope,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        )
    )
)

val LocalCineVerseTextStyle = staticCompositionLocalOf { DefaultTextStyle }