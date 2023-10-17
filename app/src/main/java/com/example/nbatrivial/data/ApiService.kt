package com.example.nbatrivial.data

import com.example.nbatrivial.data.model.GetGamesResponse
import com.example.nbatrivial.data.model.QuestionsResponse
import com.example.nbatrivial.data.model.SetNewGameResponse
import com.example.nbatrivial.data.model.SetQuestionResponse
import com.example.nbatrivial.domain.model.Game
import com.example.nbatrivial.domain.model.Question
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface ApiService {

    @PUT("questions/create")
    suspend fun setQuestion(@Body question: Question): SetQuestionResponse

    @GET("questions/index")
    suspend fun getQuestions(): QuestionsResponse

    @GET("games/ranking")
    suspend fun getGamesRanking(): GetGamesResponse

    @PUT("games/create")
    suspend fun setNewGame(@Body game: Game): SetNewGameResponse
}