
fun main(){
    val softBakedMenu = cookies.filter {
        it.softBaked
    }

    println("Soft cookies:")
    softBakedMenu.forEach {
        println("${it.name} - $${it.price}")
    }
}