
fun main(){
    val solarSystem = mutableMapOf(
        "Mercury" to 0,
        "Venus" to 0,
        "Earth" to 1,
        "Mars" to 2,
        "Jupiter" to 79,
        "Saturn" to 82,
        "Uranus" to 27,
        "Neptune" to 14
    )

    println(solarSystem.size)

    solarSystem["Pluto"] = 5

    println(solarSystem.size)
    println(solarSystem["Pluto"])
    println(solarSystem["Theia"])

    solarSystem.remove("Pluto")
    println(solarSystem.size)

    solarSystem["Jupiter"] = 78
    println(solarSystem["Jupiter"])
}