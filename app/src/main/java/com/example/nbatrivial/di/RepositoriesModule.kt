package com.example.nbatrivial.di

import com.example.nbatrivial.data.repositories.GameRepositoryImpl
import com.example.nbatrivial.data.repositories.QuestionRepositoryImpl
import com.example.nbatrivial.domain.repositories.GameRepository
import com.example.nbatrivial.domain.repositories.QuestionRepository
import org.koin.dsl.module

val repositoriesModule = module {

    factory<QuestionRepository> {
        QuestionRepositoryImpl(get())
    }

    factory<GameRepository> {
        GameRepositoryImpl(get())
    }
}
