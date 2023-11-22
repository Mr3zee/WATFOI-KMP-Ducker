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
    const val FULL_SIZE = 360f
    const val SHRANK_SIZE = 330f
}

enum class ImageAnimationState(val targetSize: Float) {
    Shrank(ClickableDuck.SHRANK_SIZE),
    Full(ClickableDuck.FULL_SIZE),
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClickableDuck(model: Model) {
    var imageState by remember { mutableStateOf(ImageAnimationState.Full) }
    val imageSize = remember {
        Animatable(imageState.targetSize)
    }

    LaunchedEffect(imageState) {
        imageSize.animateTo(
            targetValue = imageState.targetSize,
            animationSpec = tween(durationMillis = 70),
        ) {
            if (value == targetValue) {
                imageState = ImageAnimationState.Full
            }
        }
    }

    Box(
        modifier = Modifier.size(ClickableDuck.FULL_SIZE.dp),
        contentAlignment = Alignment.Center,
    ) {
        val duck by remember { model.selectedDuck }

        Image(
            painterResource(duck.resource()),
            contentDescription = "Duck",
            modifier = Modifier
                .size(imageSize.value.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                ) {
                    imageState = ImageAnimationState.Shrank

                    model.counterValue.value++
                }
        )
    }
}
