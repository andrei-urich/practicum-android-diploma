package ru.practicum.android.diploma.domain.filters.area

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.search.Resource
import ru.practicum.android.diploma.domain.search.models.VacancyShort

interface AreaFilterInteractor {
    suspend fun getCountriesList(): Flow<Pair<List<Region>?, Int?>>
    suspend fun getAllRegions(): Flow<Pair<List<Region>?, Int?>>
    suspend fun getInnerRegionsList(areaId: Int): Flow<Pair<List<Region>?, Int?>>
}
