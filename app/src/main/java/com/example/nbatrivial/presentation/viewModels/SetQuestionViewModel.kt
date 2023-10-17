package com.example.nbatrivial.presentation.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbatrivial.domain.model.Question
import com.example.nbatrivial.domain.usecase.SetQuestionUseCase
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.example.nbatrivial.data.Result
import com.example.nbatrivial.utils.UIEvent
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

class SetQuestionViewModel(
    private val setQuestionUseCase: SetQuestionUseCase
) : ViewModel() {

    var state by mutableStateOf(SetQuestionState(resetData = false))

    private val _eventFlow = MutableSharedFlow<UIEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun createNewQuestion(question: String, answerOne: String, answerTwo: String, answerThree: String, correctAnswer: String, image: String?): Question {
        return Question(question = question, answers = listOf(answerOne, answerTwo, answerThree), correctAnswer = correctAnswer, image = image)
    }

    fun setQuestion(question: Question) {
        viewModelScope.launch {
            setQuestionUseCase(question = question).onEach { result ->
                when (result) {
                    is Result.Success -> {
                        state = state.copy(
                            resetData = true
                        )
                    }
                    is Result.Error -> {
                        state = state.copy(
                            resetData = false
                        )
                        _eventFlow.emit(
                            UIEvent.ShowSnackBar(
                                result.message ?: "Unknown Error"
                            )
                        )
                    }
                    is Result.Loading -> {
                        state = state.copy(
                            resetData = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }
}

data class SetQuestionState(
    val resetData: Boolean
)

