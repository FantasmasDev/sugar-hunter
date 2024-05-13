package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

interface FavoriteRepository {

    suspend fun addFavouriteVacancy(vacancy: Vacancy)

    suspend fun deleteFavouriteVacancy(vacancy: Vacancy)

    fun getFavouritesVacancies() : Flow<List<Vacancy>>

}
