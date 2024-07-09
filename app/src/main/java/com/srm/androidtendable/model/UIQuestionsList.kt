package com.srm.androidtendable.model

data class UICategory(
    val id: Int,
    val name: String,
    val questions: List<QuestionsList>
)

data class QuestionsList(
    val answerChoices: List<AnswerChoice>,
    val id: Int,
    val name: String,
    val selectedAnswerChoiceId: Any
)