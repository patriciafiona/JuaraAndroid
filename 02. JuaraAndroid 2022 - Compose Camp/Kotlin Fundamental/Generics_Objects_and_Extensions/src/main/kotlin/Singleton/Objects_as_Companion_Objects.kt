package Singleton

import Enum.Question02

class Quiz {
    val question1 = Question02<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question02<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question02<Int>("How many days are there between full moons?", 28, Difficulty.HARD)

    companion object StudentProgress {
        var total: Int = 10
        var answered: Int = 3
    }
}