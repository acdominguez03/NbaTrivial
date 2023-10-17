package com.example.nbatrivial.domain.usecase

import com.example.nbatrivial.data.Result
import com.example.nbatrivial.domain.model.Question
import com.example.nbatrivial.domain.repositories.QuestionRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class GetQuestionsUseCase : KoinComponent {
    private val repository: QuestionRepository by inject()

    operator fun invoke(): Flow<Result<List<Question>>> {
        return repository.getQuestions()
    }
}
