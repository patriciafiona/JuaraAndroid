package Singleton

fun main() {
    // Access a singleton object
    println("${StudentProgress.answered} of ${StudentProgress.total} answered.")

    // Declare objects as companion objects
    println("${Quiz.answered} of ${Quiz.total} answered.")
}