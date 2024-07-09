package com.srm.androidtendable.model

data class Questions(
    val questionText: String,
    val answers: List<String>,
    var selectedAnswer: Int = -1
)
