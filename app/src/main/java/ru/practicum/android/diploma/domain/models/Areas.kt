package ru.practicum.android.diploma.domain.models

data class Areas(
    val id: String,
    val name: String,
    val parentId: String?,
    val countryName: String
)
