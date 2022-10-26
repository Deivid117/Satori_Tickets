package mx.com.satoritech.satoritickets.ui.ui

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import mx.com.satoritech.satoritickets.R

// Set of Material typography styles to start with
val DidactGothic = Typography(
    //Font(R.font.didactgothic_regular, FontWeight.W400)
)

val Avenir = FontFamily(
    Font(R.font.avenir_book, FontWeight.Normal),
    Font(R.font.avenir_roman, FontWeight.SemiBold),
    Font(R.font.avenir_black, FontWeight.Bold),
)
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)