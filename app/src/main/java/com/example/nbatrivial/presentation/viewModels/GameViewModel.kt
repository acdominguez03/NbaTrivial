package com.example.nbatrivial.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.domain.model.Question
import com.example.nbatrivial.domain.usecase.GetQuestionsUseCase
import com.example.nbatrivial.utils.MAX_SECONDS
import com.example.nbatrivial.utils.PreferenceService
import com.example.nbatrivial.utils.UIEvent
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class GameViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val preferences: PreferenceService
) : ViewModel() {

    var state by mutableStateOf(
        GameState(
            isLoading = false,
            questionNumber = 0
        )
    )

    var progressTimer by mutableStateOf(0f)
    var seconds by mutableStateOf(0)
    var finalScore by mutableStateOf(0)

    var finishGame by mutableStateOf(false)

    var correctOrNotAnswerState by mutableStateOf(false)

    var correctAnswerIndex by mutableStateOf<Int?>(null)

    var answerState by mutableStateOf<Int?>(null)

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    init {
        getQuestions()
    }

    private fun getQuestions() {
        viewModelScope.launch {
            getQuestionsUseCase().onEach { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            questions = result.data ?: emptyList(),
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

    fun checkCorrectAnswer(answer: String, answerIndex: Int) {
        viewModelScope.launch {
            if (answer == state.questions[state.questionNumber].correctAnswer) {
                correctOrNotAnswerState = true
                answerState = answerIndex
                delay(1000)
                finalScore += MAX_SECONDS - seconds
                resetQuestion()
            } else {
                correctOrNotAnswerState = false
                answerState = answerIndex
                correctAnswerIndex = state.questions[state.questionNumber].answers.indexOf(state.questions[state.questionNumber].correctAnswer)
                delay(1000)
                resetQuestion()
            }
        }
    }

    fun outOfTimeResponse() {
        viewModelScope.launch {
            correctAnswerIndex = state.questions[state.questionNumber].answers.indexOf(state.questions[state.questionNumber].correctAnswer)
            delay(1000)
            resetQuestion()
        }
    }

    private fun resetQuestion() {
        correctOrNotAnswerState = false
        answerState = null
        correctAnswerIndex = null
        if (state.questionNumber < state.questions.size - 1) {
            state = state.copy(
                questionNumber = state.questionNumber + 1
            )
        } else {
            preferences.score = finalScore
            finishGame = true
        }
        progressTimer = 0f
        seconds = 0
    }
}

data class GameState(
    val questions: List<Question> = emptyList(),
    val isLoading: Boolean = false,
    val questionNumber: Int = 0
)
