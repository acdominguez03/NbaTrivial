package com.example.nbatrivial.data.repositories

import com.example.nbatrivial.data.ApiService
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.data.model.SetNewGameResponse
import com.example.nbatrivial.domain.model.Game
import com.example.nbatrivial.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import retrofit2.HttpException
import java.io.IOException

class GameRepositoryImpl(
    private val api: ApiService
) : GameRepository, KoinComponent {

    override fun getGamesRanking(): Flow<Result<List<Game>>> = flow {
        emit(Result.Loading())
        try {
            val response = api.getGamesRanking().games
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

    override fun setNewGame(game: Game): Flow<Result<SetNewGameResponse>> = flow {
        emit(Result.Loading())
        try {
            val response = api.setNewGame(game)
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
