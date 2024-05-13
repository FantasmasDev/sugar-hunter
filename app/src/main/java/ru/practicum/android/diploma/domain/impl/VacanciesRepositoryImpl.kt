package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.data.network.DetailsRequest
import ru.practicum.android.diploma.data.network.DetailsResponse
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.SearchRequest
import ru.practicum.android.diploma.data.network.SearchResponse
import ru.practicum.android.diploma.domain.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient,
) : VacanciesRepository {
    override fun searchVacancies(
        text: String
    ): Flow<Resource<List<Vacancy>>> = flow {
        val response = networkClient.doRequest(SearchRequest(text))
        when (response.resultCode) {
            OK -> {
                with(response as SearchResponse) {
                    val data = items.map {
                        val vacancy = Vacancy(
                            id = it.id,
                            title = it.name,
                            city = it.area.name,
                            employer = it.employer.name,
                            logos = it.employer.logoUrls,
                            salary = it.salary
                        )
                        vacancy
                    }
                    if (data.isEmpty()) {
                        emit(Resource.NotFound(NOT_FOUND_TEXT))
                    } else {
                        emit(Resource.Data(data))
                    }
                }
            }
            in NOT_FOUND -> emit(Resource.NotFound(NOT_FOUND_TEXT))
            else -> {
                emit(Resource.ConnectionError(CONNECTION_ERROR))
            }
        }
    }.flowOn(Dispatchers.IO)

    override suspend fun getVacancy(
        id: String
    ): Flow<Resource<VacancyDetails>> = flow {
        val response = networkClient.doRequest(DetailsRequest(id))
        when (response.resultCode) {
            OK -> {
                with(response as DetailsResponse) {
                    val data = VacancyDetails(
                        id = this.id,
                        title = this.name,
                        area = this.area,
                        employer = this.employer,
                        salary = this.salary,
                        experience = this.experience,
                        employment = this.employment,
                        schedule = this.schedule,
                        description = this.description,
                        keySkills = this.keySkills,
                        contacts = this.contacts
                    )
                    emit(Resource.Data(data))
                }
            }
            in NOT_FOUND -> emit(Resource.NotFound(NOT_FOUND_TEXT))
            else -> {
                emit(Resource.ConnectionError(CONNECTION_ERROR))
            }
        }
    }

    companion object {
        private const val OK = 200
        private val NOT_FOUND = listOf(400, 404)
        private const val CONNECTION_ERROR = "connection_error"
        private const val NOT_FOUND_TEXT = "not_found"
    }
}
