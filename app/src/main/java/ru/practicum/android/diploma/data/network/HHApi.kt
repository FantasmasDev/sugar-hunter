package ru.practicum.android.diploma.data.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap
import ru.practicum.android.diploma.data.dto.models.AreaItemDTO
import ru.practicum.android.diploma.data.dto.models.DetailsResponse
import ru.practicum.android.diploma.data.dto.models.IndustryListDTO
import ru.practicum.android.diploma.data.dto.models.SearchResponseDTO

interface HHApi {
    @GET("vacancies/{vacancy_id}")
    suspend fun getVacancyDetails(
        @Path("vacancy_id") id: String
    ): Response<DetailsResponse>

    @GET("industries")
    suspend fun getIndustry(): Response<Array<IndustryListDTO>>

    @GET("areas")
    suspend fun getAreasDictionary(): Response<Array<AreaItemDTO>>

    @GET("vacancies")
    suspend fun getSearch(
        @QueryMap options: Map<String, String>
    ): Response<SearchResponseDTO>
}
