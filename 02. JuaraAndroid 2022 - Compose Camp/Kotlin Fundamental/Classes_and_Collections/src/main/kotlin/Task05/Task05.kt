package Task05

import Task02.Daypart
import Task02.Event

fun main(){
    val events = mutableListOf<Event>(
        Event(title = "Wake up", description = "Time to get up", daypart = Daypart.MORNING, durationInMinutes = 0),
        Event(title = "Eat breakfast", daypart = Daypart.MORNING, durationInMinutes = 15),
        Event(title = "Learn about Kotlin", daypart = Daypart.AFTERNOON, durationInMinutes = 30),
        Event(title = "Practice Compose", daypart = Daypart.AFTERNOON, durationInMinutes = 60),
        Event(title = "Watch latest DevBytes video", daypart = Daypart.AFTERNOON, durationInMinutes = 10),
        Event(title = "Check out latest Android Jetpack library", daypart = Daypart.EVENING, durationInMinutes = 45),
    )

    //One by one solution
    val morningEvents = events.filter { it.daypart == Daypart.MORNING }
    println("Morning: ${morningEvents.size} events ")

    val afternoonEvents = events.filter { it.daypart == Daypart.AFTERNOON }
    println("Afternoon: ${afternoonEvents.size} events")

    val eveningEvents = events.filter { it.daypart == Daypart.EVENING }
    println("Evening: ${eveningEvents.size} events")

    println("------------------")

    //Directly solution
    val groupedEvents = events.groupBy { it.daypart }
    groupedEvents.forEach { (daypart, events) ->
        println("$daypart: ${events.size} events")
    }
}