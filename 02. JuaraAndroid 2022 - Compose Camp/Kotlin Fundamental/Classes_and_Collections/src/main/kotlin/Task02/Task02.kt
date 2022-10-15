package Task02

fun main(){
    val event = Event(
        title = "Study Kotlin",
        description = "Commit to studying Kotlin at least 15 minutes per day.",
        daypart = Daypart.NIGHT,
        durationInMinutes = 15
    )

    println(event)
}