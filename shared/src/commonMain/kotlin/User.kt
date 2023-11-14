import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val password: String, // NEVER EVER EVER EVER DO THIS
)

@Serializable
data class UserSettings(
    val user: User,
    val counterValue: Int? = null,
    val duckType: String? = null,
)

@Serializable
data class Leaderboard(
    val users: List<LeaderboardEntry>,
)

@Serializable
data class LeaderboardEntry(
    val username: String,
    val counterValue: Int,
)
