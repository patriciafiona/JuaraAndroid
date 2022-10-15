
fun main(){
    val alphabeticalMenu = cookies.sortedBy {
        it.name
    }

    println("Alphabetical menu:")
    alphabeticalMenu.forEach {
        println(it.name)
    }
}