package com.example.nbatrivial.domain.usecase

import com.example.nbatrivial.domain.repositories.GameRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.domain.model.Game
import kotlinx.coroutines.flow.Flow

class GetGamesUseCase : KoinComponent {
    private val repository: GameRepository by inject()

    operator fun invoke(): Flow<Result<List<Game>>> {
        return repository.getGamesRanking()
    }
}