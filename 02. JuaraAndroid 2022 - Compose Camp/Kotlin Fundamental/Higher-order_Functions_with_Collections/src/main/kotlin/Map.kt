
fun main(){
    val fullMenu = cookies.map {
        "${it.name} - $${it.price}"
    }

    println("Full menu:")
    fullMenu.forEach {
        println(it)
    }
}