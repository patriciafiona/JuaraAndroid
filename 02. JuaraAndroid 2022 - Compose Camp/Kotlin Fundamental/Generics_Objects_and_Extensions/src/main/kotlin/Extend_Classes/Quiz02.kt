package Extend_Classes

import Difficulty
import Enum.Question02

class Quiz02 {
    val question1 = Question02<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question02<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question02<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }

    fun printQuiz() {
        question1.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question2.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
        question3.let {
            println(it.questionText)
            println(it.answer)
            println(it.difficulty)
        }
        println()
    }
}

val Quiz02.StudentProgress.progressText: String
    get() = "${answered} of ${total} answered"

fun Quiz02.StudentProgress.printProgressBar() {
    repeat(Quiz02.answered) { print("▓") }
    repeat(Quiz02.total - Quiz02.answered) { print("▒") }
    println()
    println(Quiz02.progressText)
}