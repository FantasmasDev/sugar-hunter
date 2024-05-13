package ru.practicum.android.diploma.di

import org.koin.dsl.module
import ru.practicum.android.diploma.data.db.FavouriteDataBaseDataBaseRepositoryImpl
import ru.practicum.android.diploma.domain.db.FavouriteDataBaseInteractor
import ru.practicum.android.diploma.domain.db.FavouriteDataBaseInteractorImpl
import ru.practicum.android.diploma.domain.db.FavouriteDataBaseRepository

val domainModules = module {

    single<FavouriteDataBaseInteractor> {
        FavouriteDataBaseInteractorImpl(get())
    }

    single<FavouriteDataBaseRepository>{
        FavouriteDataBaseDataBaseRepositoryImpl(get(), get())
    }

}
