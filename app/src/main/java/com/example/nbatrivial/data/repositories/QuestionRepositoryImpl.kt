package com.example.nbatrivial.data.repositories

import com.example.nbatrivial.data.ApiService
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.data.model.SetQuestionResponse
import com.example.nbatrivial.domain.model.Question
import com.example.nbatrivial.domain.repositories.QuestionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import retrofit2.HttpException
import java.io.IOException

class QuestionRepositoryImpl(
    private val api: ApiService
) : QuestionRepository, KoinComponent {
    override fun getQuestions(): Flow<Result<List<Question>>> = flow {
        emit(Result.Loading())
        try {
            val response = api.getQuestions().questions
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = "Oops, something went wrong",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                Result.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = null
                )
            )
        }
    }

    override fun setQuestion(question: Question): Flow<Result<SetQuestionResponse>> = flow {
        emit(Result.Loading())
        try {
            val response = api.setQuestion(question)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(
                Result.Error(
                    message = "Oops, something went wrong",
                    data = null
                )
            )
        } catch (e: IOException) {
            emit(
                Result.Error(
                    message = "Couldn't reach server, check your internet connection",
                    data = null
                )
            )
        }
    }
}
