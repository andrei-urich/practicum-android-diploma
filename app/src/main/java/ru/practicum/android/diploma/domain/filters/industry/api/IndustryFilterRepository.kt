package ru.practicum.android.diploma.domain.filters.industry.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetails.ResourceDetails
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterResult

interface IndustryFilterRepository {
    fun getIndustries(): Flow<ResourceDetails<IndustryFilterResult>>
}
