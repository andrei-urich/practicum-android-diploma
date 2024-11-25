package ru.practicum.android.diploma.domain.filters.area

import kotlinx.coroutines.flow.Flow
import ru.practicum.android.diploma.domain.filters.area.model.Region

interface AreaFilterInteractor {
    suspend fun getCountriesList(): Flow<Pair<List<Region>?, Int?>>
    suspend fun getAllRegions(): Flow<Pair<List<Region>?, Int?>>
    suspend fun getInnerRegionsList(areaId: Int): Flow<Pair<List<Region>?, Int?>>
}
