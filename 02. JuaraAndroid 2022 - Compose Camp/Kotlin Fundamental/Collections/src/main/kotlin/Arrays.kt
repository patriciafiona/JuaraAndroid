
val rockPlanets = arrayOf<String>("Mercury", "Venus", "Earth", "Mars")
val gasPlanets = arrayOf("Jupiter", "Saturn", "Uranus", "Neptune")
val solarSystem = rockPlanets + gasPlanets

val newSolarSystem = arrayOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto")

fun main(){
    println(solarSystem[0])
    println(solarSystem[1])
    println(solarSystem[2])
    println(solarSystem[3])
    println(solarSystem[4])
    println(solarSystem[5])
    println(solarSystem[6])
    println(solarSystem[7])

    println("------------------")

    //Change value
    solarSystem[3] = "Little Earth"
    println(solarSystem[3])

    println("------------------")

    //Accessing Index out of bound - Outside actual size
    // solarSystem[8] = "Pluto" // Will Error

    println("------------------")

    //Access last value
    println(newSolarSystem[newSolarSystem.size - 1])
}