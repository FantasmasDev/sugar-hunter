package ru.practicum.android.diploma.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.practicum.android.diploma.domain.models.Vacancy
import ru.practicum.android.diploma.ui.favourite.FavouriteViewModel
import ru.practicum.android.diploma.ui.filter.FilterViewModel
import ru.practicum.android.diploma.ui.filter.place.ChoicePlaceViewModel
import ru.practicum.android.diploma.ui.filter.place.country.CountryViewModel
import ru.practicum.android.diploma.ui.filter.place.region.RegionViewModel
import ru.practicum.android.diploma.ui.filter.sphere.ChoiceSphereViewModel
import ru.practicum.android.diploma.ui.root.RootViewModel
import ru.practicum.android.diploma.ui.search.SearchViewModel
import ru.practicum.android.diploma.ui.team.TeamViewModel
import ru.practicum.android.diploma.ui.vacancy.VacancyViewModel

val viewModelModules = module {
    viewModel {
        FavouriteViewModel(get())
    }
    viewModel {
        CountryViewModel()
    }
    viewModel {
        RegionViewModel()
    }
    viewModel {
        ChoicePlaceViewModel()
    }
    viewModel {
        ChoiceSphereViewModel()
    }
    viewModel {
        FilterViewModel()
    }
    viewModel {
        RootViewModel()
    }
    viewModel {
        SearchViewModel()
    }
    viewModel {
        TeamViewModel()
    }
    viewModel {
        (vacancy: Vacancy) ->
        VacancyViewModel(vacancy = vacancy, get())
    }
}
