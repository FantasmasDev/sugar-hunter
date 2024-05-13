package ru.practicum.android.diploma.domain

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

interface VacanciesRepository {
    fun searchVacancies(text: String): Flow<Resource<List<Vacancy>>>
    suspend fun getVacancy(id: String): Flow<Resource<VacancyDetails>>
}
