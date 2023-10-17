package com.example.nbatrivial.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.domain.model.Game
import com.example.nbatrivial.domain.usecase.SetNewGameUseCase
import com.example.nbatrivial.utils.PreferenceService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class FinalScreenViewModel(
    private val setNewGameUseCase: SetNewGameUseCase,
    private val preferences: PreferenceService
) : ViewModel() {

    var finalScore by mutableStateOf(0)

    var state by mutableStateOf(SetNewGameState(goToHome = false))

    init {
        finalScore = preferences.score
    }

    fun createNewGame(username: String, score: Int): Game {
        return Game(username = username, score = score)
    }

    fun setNewGame(game: Game) {
        viewModelScope.launch {
            setNewGameUseCase(game).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            goToHome = true
                        )
                    }
                    is Result.Error -> {
                        state = state.copy(
                            goToHome = false
                        )
                    }
                    is Result.Loading -> {
                        state = state.copy(
                            goToHome = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}

data class SetNewGameState(
    var goToHome: Boolean
)
