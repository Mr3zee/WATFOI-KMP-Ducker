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

class UserScore(
    val userName: String,
    val score: Int,
)

val allPlayers = listOf<UserScore>(
    UserScore("user1", 1),
    UserScore("user2", 12),
    UserScore("user3", 1000),
    UserScore("user4", 800),
    UserScore("user5", 900),
    UserScore("user6", 700),
    UserScore("user7", 43),
    UserScore("me", 13),
)

@Composable
fun Leaderboard(model: Model) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        LeaderboardRow(
            index = null,
            leftText = "User",
            rightText = "Score",
            isLast = false,
            highlight = false,
            fontWeight = FontWeight.Bold
        )
        
        allPlayers.sortedByDescending { it.score }.forEachIndexed { i, score ->
            LeaderboardRow(
                index = i + 1,
                leftText = score.userName,
                rightText = score.score.toString(),
                highlight = score.userName == model.user.name,
                isLast = i == allPlayers.size - 1
            )
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
) {
    val fontSize = 20.sp
    val horizontalPadding = 4.dp
    val textColor = if (highlight) Color.White else Color.Black
    
    Row(
        Modifier
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
