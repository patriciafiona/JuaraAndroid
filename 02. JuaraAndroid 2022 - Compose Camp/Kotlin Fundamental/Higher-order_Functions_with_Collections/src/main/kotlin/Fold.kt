
fun main(){
    val totalPrice = cookies.fold(0.0) {total, cookie ->
        total + cookie.price
    }

    println("Total price: $${totalPrice}")
}