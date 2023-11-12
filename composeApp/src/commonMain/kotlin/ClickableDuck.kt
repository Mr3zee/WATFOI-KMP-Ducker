import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

enum class ImageAnimatonState(val targetSize: Int) {
    Shirinked(280),
    Full(300),
    Interrupted(300),
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ClickableDuck(duckName: String) {
    var imageState by remember { mutableStateOf(ImageAnimatonState.Full) }
    val imageSize = remember {
        Animatable(imageState.targetSize.toFloat())
    }

    LaunchedEffect(imageState) {
        launch {
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
    }

    Image(
        painterResource("ducks/$duckName"),
        null,
        modifier = Modifier
            .size(imageSize.value.dp, imageSize.value.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
            ) {

                imageState = ImageAnimatonState.Interrupted
            }
    )
}