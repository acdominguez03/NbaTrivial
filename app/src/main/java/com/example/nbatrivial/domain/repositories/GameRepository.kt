package com.example.nbatrivial.domain.repositories

import com.example.nbatrivial.data.Result
import com.example.nbatrivial.data.model.SetNewGameResponse
import com.example.nbatrivial.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GameRepository {
    fun getGamesRanking(): Flow<Result<List<Game>>>

    fun setNewGame(game: Game): Flow<Result<SetNewGameResponse>>
}
