package ru.practicum.android.diploma.data.dto

import ru.practicum.android.diploma.data.network.Response

data class IndustryDTO(
    val container: List<IndustryList>
) : Response()
class IndustryList(
    val id: String,
    val industries: List<Industry>,
    val name: String
)
data class Industry(
    val id: String,
    val name: String
)
