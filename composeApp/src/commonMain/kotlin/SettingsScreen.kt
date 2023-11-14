import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

private const val paddingPx = 16

@Composable
fun SettignsScreen(model: Model) {
    val screenWidth = getScreenWidthDp()

    Column(
        modifier = Modifier
            .padding(start = paddingPx.dp, end = paddingPx.dp, bottom = 60.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = paddingPx.dp),
            horizontalArrangement = Arrangement.Center,
        ) {
            Button(onClick = {
                model.logout()
            }) {
                Text("Logout")
            }
        }

        Divider(Modifier.padding(paddingPx.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
        ) {
            Text("Select your duck", fontSize = 30.sp)
        }

        DuckType.entries.chunked(2).forEach { list ->
            val duck1 = list[0]
            val duck2 = list.getOrNull(1)

            val rowSize = screenWidth - paddingPx * 2
            Row(
                modifier = Modifier
                    .width(rowSize.dp)
                    .padding(vertical = (paddingPx / 2).dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                val duckSize = ((rowSize - 16) / 2).dp

                SelectableDuck(model, duck1, duckSize)

                if (duck2 != null) {
                    SelectableDuck(model, duck2, duckSize)
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun SelectableDuck(model: Model, duckType: DuckType, size: Dp) {
    val selected by remember { model.selectedDuck }
    Box(
        modifier = Modifier
            .border(2.dp, Color.Black)
            .background(if (selected == duckType) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.background)
            .clickable {
                model.selectedDuck.value = duckType
            },
    ) {
        Image(
            painterResource(duckType.resourse()),
            contentDescription = duckType.name,
            modifier = Modifier.size(size)
        )
    }
}
