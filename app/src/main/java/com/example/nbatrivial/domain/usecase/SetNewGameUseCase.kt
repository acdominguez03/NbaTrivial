package com.example.nbatrivial.domain.usecase

import com.example.nbatrivial.data.Result
import com.example.nbatrivial.data.model.SetNewGameResponse
import com.example.nbatrivial.domain.model.Game
import com.example.nbatrivial.domain.repositories.GameRepository
import kotlinx.coroutines.flow.Flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SetNewGameUseCase : KoinComponent {
    private val repository: GameRepository by inject()

    operator fun invoke(game: Game): Flow<Result<SetNewGameResponse>> {
        return repository.setNewGame(game = game)
    }
}
