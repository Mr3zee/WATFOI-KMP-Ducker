package ui.screen

import model.Model
import ui.VerticalDivider
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import api.apiService
import org.jetbrains.compose.resources.LoadState
import org.jetbrains.compose.resources.load

@Composable
fun Leaderboard(model: Model) {
    val padding = 16.dp
    val leaderboardState = load {
        apiService.getLeaderboard()
    }

    Column(
        modifier = Modifier
            .padding(start = padding, end = padding, bottom = 60.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LeaderboardRow(
            index = null,
            leftText = "User",
            rightText = "Score",
            isLast = false,
            highlight = false,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = padding),
        )
        
        when (leaderboardState) {
            is LoadState.Loading -> {
                Text("Loading leaderboard...")
            }
            is LoadState.Error -> {
                Text("failed to load leaderboard")
            }
            is LoadState.Success -> {
                val leaderboard = leaderboardState.value

                leaderboard.users.sortedByDescending { it.counterValue }.forEachIndexed { i, score ->
                    LeaderboardRow(
                        index = i + 1,
                        leftText = score.username,
                        rightText = score.counterValue.toString(),
                        highlight = score.username == model.username,
                        isLast = i == leaderboard.users.size - 1
                    )
                }
            }
        }
    }
}

@Composable
private fun LeaderboardRow(
    index: Int?,
    leftText: String,
    rightText: String,
    isLast: Boolean,
    highlight: Boolean,
    fontWeight: FontWeight? = null,
    modifier: Modifier = Modifier,
) {
    val fontSize = 20.sp
    val horizontalPadding = 4.dp
    val textColor = if (highlight) Color.White else Color.Black
    
    Row(
        modifier
            .height(36.dp)
            .background(if (highlight) MaterialTheme.colors.primary else MaterialTheme.colors.background)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .weight(0.1f)
                .padding(horizontal = horizontalPadding)
        ) {
            if (index != null) {
                Text("$index.", fontSize = fontSize, fontWeight = fontWeight, color = textColor)
            }
        }

        VerticalDivider()

        Box(
            modifier = Modifier
                .weight(0.45f)
                .padding(horizontal = horizontalPadding)
        ) {
            Text(leftText, fontSize = fontSize, fontWeight = fontWeight, color = textColor)
        }

        VerticalDivider()

        Box(
            modifier = Modifier
                .weight(0.45f)
                .padding(horizontal = horizontalPadding)
        ) {
            Text(rightText, fontSize = fontSize, fontWeight = fontWeight, color = textColor)
        }
    }

    if (!isLast) {
        Divider()
    }
}
