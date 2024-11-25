package ru.practicum.android.diploma.domain.filters.area

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.area.model.Region
import ru.practicum.android.diploma.domain.search.Resource

interface AreaFilterRepository {
    suspend fun getCountriesList(): Flow<Resource<List<Region>>>
    suspend fun getAllRegions(): Flow<Resource<List<Region>>>
    suspend fun getInnerRegionsList(areaId: Int): Flow<Resource<List<Region>>>
}
