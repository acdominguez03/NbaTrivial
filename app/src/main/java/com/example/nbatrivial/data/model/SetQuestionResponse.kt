package com.example.nbatrivial.data.model

import com.example.nbatrivial.domain.model.Question
import com.google.gson.annotations.SerializedName

data class SetQuestionResponse(
    @SerializedName("result") val result: Question
)
