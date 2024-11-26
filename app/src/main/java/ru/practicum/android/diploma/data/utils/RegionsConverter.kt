package ru.practicum.android.diploma.data.utils

import ru.practicum.android.diploma.data.filters.area.dto.InnerRegionDTO
import ru.practicum.android.diploma.domain.filters.area.model.InnerRegion
import ru.practicum.android.diploma.domain.filters.area.model.Region

class RegionsConverter {

    fun innerRegionDTOtoInnerRegion(it: List<InnerRegionDTO>): List<InnerRegion> {
        return it.map {
            InnerRegion(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
            )
        }
    }

    fun regionsToCountriesMapper(list: List<Region>): List<Region> {
        val countries = list.filter { region ->
            region.parentId == null
        }
        return countries
    }

    fun allInnerRegions(list: List<Region>): List<Region> {
        val innerList = mutableListOf<InnerRegion>()
        for (region in list) {
            if (region.innerRegions.isNotEmpty())
                innerList.addAll(region.innerRegions)
        }
        return innerList.map {
            Region(
                id = it.id,
                name = it.name,
                parentId = it.parentId,
                innerRegions = emptyList()
            )
        }
    }
}
