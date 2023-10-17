package com.example.nbatrivial.domain.repositories

import com.example.nbatrivial.data.Result
import com.example.nbatrivial.data.model.SetQuestionResponse
import com.example.nbatrivial.domain.model.Question
import kotlinx.coroutines.flow.Flow

interface QuestionRepository {

    fun getQuestions(): Flow<Result<List<Question>>>

    fun setQuestion(question: Question): Flow<Result<SetQuestionResponse>>
}
