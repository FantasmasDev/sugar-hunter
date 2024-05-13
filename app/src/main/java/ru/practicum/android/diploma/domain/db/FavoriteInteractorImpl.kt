package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteInteractorImpl(private val favoriteRepository: FavoriteRepository) : FavoriteInteractor {
    override suspend fun addFavouriteVacancy(vacancy: Vacancy) {
        favoriteRepository.addFavouriteVacancy(vacancy)
    }

    override suspend fun deleteFavouriteVacancy(vacancy: Vacancy) {
         favoriteRepository.deleteFavouriteVacancy(vacancy)
    }

    override fun getFavouritesVacancies(): Flow<List<Vacancy>> {
        return favoriteRepository.getFavouritesVacancies()
    }


}
