import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration

@Composable
actual fun getScreenWidthDp(): Float {
    return LocalConfiguration.current.screenWidthDp.toFloat()
}
