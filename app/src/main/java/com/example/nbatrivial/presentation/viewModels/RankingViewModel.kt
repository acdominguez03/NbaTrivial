package com.example.nbatrivial.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.domain.model.Game
import com.example.nbatrivial.domain.usecase.GetGamesUseCase
import com.example.nbatrivial.utils.UIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class RankingViewModel(
    private val getGamesUseCase: GetGamesUseCase
) : ViewModel() {

    var state by mutableStateOf(RankingState(isLoading = false))
        private set

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getGames()
    }

    fun getGames() {
        viewModelScope.launch {
            getGamesUseCase().onEach { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            games = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Result.Error -> {
                        state = state.copy(
                            isLoading = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown Error"
                            )
                        )
                    }
                    is Result.Loading -> {
                        state = state.copy(
                            isLoading = true
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}

data class RankingState(
    val isLoading: Boolean = false,
    val games: List<Game> = emptyList()
)
