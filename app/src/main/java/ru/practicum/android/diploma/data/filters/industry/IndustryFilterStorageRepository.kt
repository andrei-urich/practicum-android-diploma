package ru.practicum.android.diploma.data.filters.industry

import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

interface IndustryFilterStorageRepository {
    fun readIndustry(): IndustryFilterModel?
    fun writeIndustry(industry: IndustryFilterModel)
    fun removeIndustry()
}
