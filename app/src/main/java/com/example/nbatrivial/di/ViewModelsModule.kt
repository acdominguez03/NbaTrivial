package com.example.nbatrivial.di

import com.example.nbatrivial.domain.usecase.GetGamesUseCase
import com.example.nbatrivial.domain.usecase.GetQuestionsUseCase
import com.example.nbatrivial.domain.usecase.SetNewGameUseCase
import com.example.nbatrivial.domain.usecase.SetQuestionUseCase
import com.example.nbatrivial.presentation.viewModels.FinalScreenViewModel
import com.example.nbatrivial.presentation.viewModels.GameViewModel
import com.example.nbatrivial.presentation.viewModels.RankingViewModel
import com.example.nbatrivial.presentation.viewModels.SetQuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    // view models
    viewModelOf(::FinalScreenViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::SetQuestionViewModel)
    viewModelOf(::RankingViewModel)
    // Use Cases
    factory { GetQuestionsUseCase() }
    factory { GetGamesUseCase() }
    factory { SetQuestionUseCase() }
    factory { SetNewGameUseCase() }
}
