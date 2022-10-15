package Data_Class

fun main(){
    val question1 = Question03<String>("Quoth the raven ___", "nevermore", Difficulty.MEDIUM)
    val question2 = Question03<Boolean>("The sky is green. True or false", false, Difficulty.EASY)
    val question3 = Question03<Int>("How many days are there between full moons?", 28, Difficulty.HARD)
}