package ru.practicum.android.diploma.data.dto.models

class SearchResponseDTO(
    val items: List<VacanciesDTO>,
    val pages: Int,
    val page: Int,
    val found: Int
)
