package Generic

import Difficulty

class Question<T>(
    val questionText: String,
    val answer: T,
    val difficulty: String
)