package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.FavoriteDataBaseDataBaseRepositoryImpl
import ru.practicum.android.diploma.domain.db.FavoriteDataBaseInteractor
import ru.practicum.android.diploma.domain.db.FavoriteDataBaseInteractorImpl
import ru.practicum.android.diploma.domain.db.FavoriteDataBaseRepository

val domainModules = module {

    single<FavoriteDataBaseInteractor> {
        FavoriteDataBaseInteractorImpl(get())
    }

    single<FavoriteDataBaseRepository>{
        FavoriteDataBaseDataBaseRepositoryImpl(get(), get())
    }

}
