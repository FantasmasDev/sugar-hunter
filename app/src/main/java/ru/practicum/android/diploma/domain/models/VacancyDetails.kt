package ru.practicum.android.diploma.domain.models

import ru.practicum.android.diploma.data.dto.Area
import ru.practicum.android.diploma.data.dto.Employer
import ru.practicum.android.diploma.data.dto.Salary
import ru.practicum.android.diploma.data.dto.Contacts
import ru.practicum.android.diploma.data.dto.Employment
import ru.practicum.android.diploma.data.dto.Experience
import ru.practicum.android.diploma.data.dto.Schedule

data class VacancyDetails(
    val id: String,
    val title: String,
    val area: Area,
    val employer: Employer,
    val salary: Salary?,
    val experience: Experience?,
    val employment: Employment?,
    val schedule: Schedule?,
    val description: String?,
    val keySkills: List<String>?,
    val contacts: Contacts?
)
