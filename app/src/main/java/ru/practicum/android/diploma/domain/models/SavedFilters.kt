package ru.practicum.android.diploma.domain.models

data class SavedFilters(
    val areaId: String?, // area
    val industryId: String?, // industry
    val salary: String?, // salary
    val onlyWithSalary: Boolean = false // only_with_salary=true
)
