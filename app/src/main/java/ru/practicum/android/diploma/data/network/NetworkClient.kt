package ru.practicum.android.diploma.data.network

import ru.practicum.android.diploma.data.dto.models.AreasDictionaryDTO
import ru.practicum.android.diploma.data.dto.models.DetailsResponse
import ru.practicum.android.diploma.data.dto.models.IndustryResponse
import ru.practicum.android.diploma.data.dto.models.SearchResponseDTO
import ru.practicum.android.diploma.domain.models.Resource

interface NetworkClient {
    suspend fun getVacancies(request: Map<String, String>): Resource<SearchResponseDTO>

    suspend fun getIndustry(): Resource<IndustryResponse>

    suspend fun getVacancyDetails(id: String): Resource<DetailsResponse>

    suspend fun getAreasDictionary(): Resource<AreasDictionaryDTO>
}
