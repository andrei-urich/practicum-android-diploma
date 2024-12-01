package ru.practicum.android.diploma.data.search.filters

import ru.practicum.android.diploma.domain.filters.Filters

class FilterToFilterDTOConverter {
    fun map(filters: Filters): FilterDTO {
        return FilterDTO(
            filters.getAreaId(),
            filters.getIndustryId(),
            filters.getSalaryTarget(),
            filters.getSalaryShowChecked().toString()
        )
    }
}
