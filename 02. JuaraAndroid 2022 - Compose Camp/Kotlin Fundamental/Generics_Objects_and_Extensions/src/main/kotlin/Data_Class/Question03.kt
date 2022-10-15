package Data_Class

import Difficulty

data class Question03<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)