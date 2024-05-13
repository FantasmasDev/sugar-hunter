package ru.practicum.android.diploma.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.practicum.android.diploma.domain.db.FavoriteRepository
import ru.practicum.android.diploma.domain.models.Vacancy

class FavoriteRepositoryDBImpl(private val database: AppDatabase, private val convertor: Convertor) :
    FavoriteRepository {
    override suspend fun addFavouriteVacancy(vacancy: Vacancy) {
        val favouriteVacancy = convertor.mapFromVacancy(vacancy)
        database.favouritesVacanciesDao().insertVacancy(favouriteVacancy)
    }

    override suspend fun deleteFavouriteVacancy(vacancy: Vacancy) {
        val favouriteVacancy = convertor.mapFromVacancy(vacancy)
        database.favouritesVacanciesDao().deleteVacancy(favouriteVacancy)
    }

    override fun getFavouritesVacancies(): Flow<List<Vacancy>> = flow {
        val favouritesVacancies =database.favouritesVacanciesDao().getVacancies()
        emit(convertFromFavouriteVacancy(favouritesVacancies))
    }

    private fun convertFromFavouriteVacancy(vacancies: List<FavouriteVacancy>): List<Vacancy> {
        return vacancies.map { convertor.mapFromFavourite(it) }
    }

}
