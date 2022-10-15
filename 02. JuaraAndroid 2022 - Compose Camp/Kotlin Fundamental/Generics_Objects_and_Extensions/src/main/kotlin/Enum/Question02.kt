package Enum

import Difficulty

class Question02<T>(
    val questionText: String,
    val answer: T,
    val difficulty: Difficulty
)