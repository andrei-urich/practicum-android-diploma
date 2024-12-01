package ru.practicum.android.diploma.domain.filters.industry.api

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.data.vacancydetails.ErrorType
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterModel
import ru.practicum.android.diploma.domain.filters.industry.model.IndustryFilterResult

interface IndustryFilterInteractor {
    fun getIndustries(): Flow<Pair<IndustryFilterResult?, ErrorType>>
    fun saveIndustrySettings(industry: IndustryFilterModel)
    fun getIndustrySettings(): IndustryFilterModel?
    fun deleteIndustrySettings()
    fun searchIndustries(query: String): Flow<Pair<IndustryFilterResult?, ErrorType>>
}
