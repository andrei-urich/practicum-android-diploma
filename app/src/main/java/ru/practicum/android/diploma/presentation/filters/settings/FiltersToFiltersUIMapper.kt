package ru.practicum.android.diploma.presentation.filters.settings

import ru.practicum.android.diploma.domain.filters.Filters

class FiltersToFiltersUIMapper {
    fun map(filters: Filters): FiltersUIModel {
        return FiltersUIModel(
            filters.getAreaNCityNames(),
            filters.getIndustryName(),
            filters.getSalaryTarget(),
            filters.getSalaryShowChecked()
        )
    }
}
