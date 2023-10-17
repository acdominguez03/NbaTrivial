package com.example.nbatrivial.domain.model

data class Question(
    val question: String,
    val answers: List<String>,
    val correctAnswer: String,
    val image: String?
)
