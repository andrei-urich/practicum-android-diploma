package ru.practicum.android.diploma.domain.filters.industry.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel

interface IndustryFilterRepository {
    fun getIndustries(): Flow<ResourceDetails<List<IndustryFilterModel>>>
    fun saveIndustrySettings(industry: IndustryFilterModel)
    fun getIndustrySettings(): IndustryFilterModel?
    fun deleteIndustrySettings()
    fun searchIndustries(query: String): Flow<ResourceDetails<List<IndustryFilterModel>>>
}
