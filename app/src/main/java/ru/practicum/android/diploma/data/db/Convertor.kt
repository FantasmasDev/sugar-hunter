package ru.practicum.android.diploma.data.db

import ru.practicum.android.diploma.domain.models.Vacancy

class Convertor {

    fun mapFromVacancy(vacancy: Vacancy): FavouriteVacancy{
        return FavouriteVacancy(
            vacancy.id
            // ДОПОЛНИТЬ ПОСЛЕ СОЗДАНИЯ ПОЛНОЙ МОДЕЛИ
        )
    }

    fun mapFromFavourite(vacancy: FavouriteVacancy): Vacancy {
        return Vacancy(
            vacancy.id
            // ДОПОЛНИТЬ ПОСЛЕ СОЗДАНИЯ ПОЛНОЙ МОДЕЛИ
        )
    }

}
