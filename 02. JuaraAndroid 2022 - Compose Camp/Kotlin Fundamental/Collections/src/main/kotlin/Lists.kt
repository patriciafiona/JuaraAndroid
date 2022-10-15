
fun main() {
    var solarSystem = listOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")

    println(solarSystem.size)

    //Access elements from a list
    println(solarSystem[2])
    println(solarSystem.get(3))

    println(solarSystem.indexOf("Earth"))
    println(solarSystem.indexOf("Pluto"))

    for (planet in solarSystem) {
        println(planet)
    }

    //Add elements to a list
    solarSystem = mutableListOf("Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune")
    solarSystem.add("Pluto")
    solarSystem.add(3, "Theia")

    //Update elements at a specific index
    solarSystem[3] = "Future Moon"

    println(solarSystem[3])
    println(solarSystem[9])

    //Remove elements from a list
    solarSystem.removeAt(9)

    solarSystem.remove("Future Moon")

    println(solarSystem.contains("Pluto"))

    println("Future Moon" in solarSystem)

}