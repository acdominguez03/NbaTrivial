package com.example.nbatrivial.data.model

import com.example.nbatrivial.domain.model.Question
import com.google.gson.annotations.SerializedName

data class QuestionsResponse(
    @SerializedName("questions") val questions: List<Question>
)
