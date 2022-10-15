fun main(){
    val softBakedMenuStart = cookies.filter {
        it.softBaked
    }
    println("Soft cookies:")
    softBakedMenuStart.forEach {
        println("${it.name} - $${it.price}")
    }

    println("----------------")

    //Group By
    val groupedMenu = cookies.groupBy { it.softBaked }

    val softBakedMenu = groupedMenu[true] ?: listOf()
    val crunchyMenu = groupedMenu[false] ?: listOf()

    println("Soft cookies:")
    softBakedMenu.forEach {
        println("${it.name} - $${it.price}")
    }
    println("----------------")
    
    println("Crunchy cookies:")
    crunchyMenu.forEach {
        println("${it.name} - $${it.price}")
    }
}