package com.srm.androidtendable.model

data class InspectionResponse(
    val inspection: Inspection
)

data class Inspection(
    val area: Area,
    val id: Int,
    val inspectionType: InspectionType,
    val survey: Survey
)

data class InspectionType(
    val access: String,
    val id: Int,
    val name: String
)

data class Category(
    val id: Int,
    val name: String,
    var questions: List<Question>
)

data class Question(
    val answerChoices: List<AnswerChoice>,
    val id: Int,
    val name: String,
    var selectedAnswerChoiceId: Int = -1
)

data class AnswerChoice(
    val id: Int,
    val name: String,
    val score: Double,
    var selectedAnswer: Int = -1
)

data class Survey(
    var categories: List<Category>,
    val id: Int
)

data class Area(
    val id: Int,
    val name: String
)

