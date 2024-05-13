package ru.practicum.android.diploma.domain.db

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteDataBaseInteractorImpl(private val favoriteDataBaseRepository: FavoriteDataBaseRepository) : FavoriteDataBaseInteractor {
    override suspend fun addFavouriteVacancy(vacancy: Vacancy) {
        favoriteDataBaseRepository.addFavouriteVacancy(vacancy)
    }

    override suspend fun deleteFavouriteVacancy(vacancy: Vacancy) {
         favoriteDataBaseRepository.deleteFavouriteVacancy(vacancy)
    }

    override suspend fun checkIdInFavourites(id: String): Boolean {
        return favoriteDataBaseRepository.checkIdInFavourites(id)
    }

    override fun getFavouritesVacancies(): Flow<List<Vacancy>> {
        return favoriteDataBaseRepository.getFavouritesVacancies()
    }


}
