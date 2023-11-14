package ui

import model.Model
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun Counter(model: Model) {
    val value: Int by remember { model.conterValue }

    Text(
        text = "$value",
        fontStyle = FontStyle.Normal,
        fontWeight = FontWeight.Bold,
        fontFamily = FontFamily.Monospace,
        color = Color.Black,
        fontSize = 30.sp,
    )
}
