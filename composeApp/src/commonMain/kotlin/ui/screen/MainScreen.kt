package ui.screen

import ui.ClickableDuck
import ui.Counter
import model.Model
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainScreen(model: Model) {
    Column(
        Modifier.fillMaxSize().padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Ducker", fontSize = 50.sp, fontWeight = FontWeight.Bold)
            Text("It is like clicker, but with ducks", fontSize = 20.sp)
        }

        Column(
            Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            ClickableDuck(model)
            Counter(model)
        }
    }
}
