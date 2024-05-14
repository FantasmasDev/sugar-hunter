package ru.practicum.android.diploma.data.db

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import ru.practicum.android.diploma.domain.db.FavouriteDataBaseRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavouriteDataBaseDataBaseRepositoryImpl(private val database: AppDatabase, private val convertor: Convertor) :
    FavouriteDataBaseRepository {
    override suspend fun addFavouriteVacancy(vacancy: Vacancy) {
        val favouriteVacancy = convertor.mapFromVacancy(vacancy)
        database.favouritesVacanciesDao().insertVacancy(favouriteVacancy)
    }

    override suspend fun deleteFavouriteVacancy(vacancy: Vacancy) {
        val favouriteVacancy = convertor.mapFromVacancy(vacancy)
        database.favouritesVacanciesDao().deleteVacancy(favouriteVacancy)
    }

    override suspend fun checkIdInFavourites(id: String): Boolean {
        return withContext(Dispatchers.IO) {
            val favouritesIds = database.favouritesVacanciesDao().getFavoritesIds()
            favouritesIds.contains(id)
        }
    }

    override fun getFavouritesVacancies(): Flow<List<Vacancy>> = flow {
        val favouritesVacancies = database.favouritesVacanciesDao().getVacancies()
        emit(convertFromFavouriteVacancy(favouritesVacancies))
    }

    private fun convertFromFavouriteVacancy(vacancies: List<FavouriteVacancy>): List<Vacancy> {
        return vacancies.map { convertor.mapFromFavourite(it) }
    }

}


