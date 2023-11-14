package ui

import model.Model
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

object ClickableDuck {
    const val FULL_SIZE = 360
    const val SHRINKED_SIZE = 330
}

enum class ImageAnimatonState(val targetSize: Int) {
    Shirinked(ClickableDuck.SHRINKED_SIZE),
    Full(ClickableDuck.FULL_SIZE),
    Interrupted(ClickableDuck.FULL_SIZE),
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClickableDuck(model: Model) {
    var imageState by remember { mutableStateOf(ImageAnimatonState.Full) }
    val imageSize = remember {
        Animatable(imageState.targetSize.toFloat())
    }

    LaunchedEffect(imageState) {
        when (imageState) {
            ImageAnimatonState.Full, ImageAnimatonState.Shirinked -> {
                imageSize.animateTo(
                    targetValue = imageState.targetSize.toFloat(),
                    animationSpec = tween(durationMillis = 70),
                ) {
                    if (value == targetValue) {
                        imageState = ImageAnimatonState.Full
                    }
                }
            }

            ImageAnimatonState.Interrupted -> {
                imageSize.snapTo(ImageAnimatonState.Full.targetSize.toFloat())
                imageState = ImageAnimatonState.Shirinked
            }
        }
    }

    Box(
        modifier = Modifier.size(ClickableDuck.FULL_SIZE.dp),
        contentAlignment = Alignment.Center,
    ) {
        val duck by remember { model.selectedDuck }

        Image(
            painterResource(duck.resourse()),
            contentDescription = "Duck",
            modifier = Modifier
                .size(imageSize.value.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    imageState = ImageAnimatonState.Interrupted

                    model.conterValue.value++
                }
        )
    }
}
