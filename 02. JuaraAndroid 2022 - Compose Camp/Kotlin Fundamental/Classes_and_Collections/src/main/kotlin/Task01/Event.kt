package Task01

data class Event(
    val title: String,
    val description: String? = null,
    val daypart: String,
    val durationInMinutes: Int,
)