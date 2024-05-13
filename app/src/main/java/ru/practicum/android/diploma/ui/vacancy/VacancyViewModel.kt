package ru.practicum.android.diploma.ui.vacancy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.practicum.android.diploma.domain.db.FavoriteDataBaseInteractor
import ru.practicum.android.diploma.domain.models.Vacancy

class VacancyViewModel(
    private val vacancy: Vacancy,
    private val favoriteDataBaseInteractor: FavoriteDataBaseInteractor
) : ViewModel() {

    val mutable = MutableLiveData<Int>()

}
