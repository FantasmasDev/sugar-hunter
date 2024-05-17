package ru.practicum.android.diploma.domain.impl

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import ru.practicum.android.diploma.data.network.NetworkClient
import ru.practicum.android.diploma.data.network.requests.DetailsRequest
import ru.practicum.android.diploma.data.network.requests.IndustryRequest
import ru.practicum.android.diploma.data.network.requests.SearchRequest
import ru.practicum.android.diploma.data.network.responses.DetailsResponse
import ru.practicum.android.diploma.data.network.responses.IndustryResponse
import ru.practicum.android.diploma.data.network.responses.KeySkillsDTO
import ru.practicum.android.diploma.data.network.responses.SearchResponse
import ru.practicum.android.diploma.domain.VacanciesRepository
import ru.practicum.android.diploma.domain.models.Industries
import ru.practicum.android.diploma.domain.models.Industry
import ru.practicum.android.diploma.domain.models.LogoUrls
import ru.practicum.android.diploma.domain.models.Resource
import ru.practicum.android.diploma.domain.models.Salary
import ru.practicum.android.diploma.domain.models.VacanciesResponse
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.domain.models.VacancyDetails

class VacanciesRepositoryImpl(
    private val networkClient: NetworkClient
) : VacanciesRepository {

    override fun searchVacancies(
        options: Map<String, String>
    ): Flow<Resource<VacanciesResponse>> = flow {
        val response = networkClient.doRequest(SearchRequest(options))
        when (response.resultCode) {
            OK -> {
                with(response as SearchResponse) {
                    val vacancyResponse =
                        VacanciesResponse(
                            page = page,
                            pages = pages,
                            found = found,
                            items = items.map {
                                val vacancy = Vacancy(
                                    id = it.id,
                                    title = it.name,
                                    city = it.area.name,
                                    employer = it.employer.name,
                                    logos = LogoUrls(
                                        logo90 = it.employer.logoUrls?.logo90,
                                        logo240 = it.employer.logoUrls?.logo240
                                    ),
                                    salary = Salary(
                                        from = it.salary?.from,
                                        to = it.salary?.to,
                                        currency = it.salary?.currency,
                                        gross = it.salary?.gross
                                    )
                                )
                                vacancy
                            }
                        )

                    if (vacancyResponse.items.isEmpty()) {
                        emit(Resource.NotFound(NOT_FOUND_TEXT))
                    } else {
                        emit(Resource.Data(vacancyResponse))
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
                        keySkills = skillsMapper(this.keySkills),
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
    }.flowOn(Dispatchers.IO)

    override suspend fun getIndustries(): Flow<Resource<List<Industries>>> = flow {
        val response = networkClient.doRequest(IndustryRequest())
        when (response.resultCode) {
            OK -> {
                with(response as IndustryResponse) {
                    val data = container.map {
                        val industries = Industries(
                            id = it.id,
                            name = it.name,
                            industries = it.industries.map {sub ->
                                val subIndustry = Industry(
                                    id = sub.id,
                                    name = sub.name
                                )
                                subIndustry
                            }
                        )
                        industries
                    }
                    emit(Resource.Data(data))
                }
            }

            in NOT_FOUND -> emit(Resource.NotFound(NOT_FOUND_TEXT))
            else -> {
                emit(Resource.ConnectionError(CONNECTION_ERROR))
            }
        }
    }.flowOn(Dispatchers.IO)

    private fun skillsMapper(list: List<KeySkillsDTO>?): List<String>? {
        if (list.isNullOrEmpty()) {
            return null
        }
        return list.mapNotNull { it.name }
    }

    companion object {
        private const val OK = 200
        private val NOT_FOUND = listOf(400, 404)
        private const val CONNECTION_ERROR = "connection_error"
        private const val NOT_FOUND_TEXT = "not_found"
    }
}
