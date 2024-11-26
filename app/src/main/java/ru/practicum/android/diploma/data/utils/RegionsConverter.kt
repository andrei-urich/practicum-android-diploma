package ru.practicum.android.diploma.data.utils

import ru.practicum.android.diploma.data.filters.area.dto.AreaDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.domain.filters.area.model.Region

class RegionsConverter {

    fun regionsToCountriesMapper(list: List<Region>): List<Region> {
        val countries = list.filter { region ->
            region.parentId == null
        }
        return countries
    }

    fun allInnerRegions(list: List<RegionDTO>): List<AreaDTO> {
        val innerList = mutableListOf<AreaDTO>()
        for (region in list) {
            if (region.innerRegions.isNotEmpty()) {
                for (innerRegion in region.innerRegions) {
                    if (!innerRegion.innerRegions.isNullOrEmpty()) {
                        for (subInnerRegion in innerRegion.innerRegions) {
                            innerList.add(
                                AreaDTO(
                                    id = subInnerRegion.id,
                                    name = subInnerRegion.name,
                                    parentId = subInnerRegion.parentId
                                )
                            )
                        }
                    }
                    innerList.add(
                        AreaDTO(
                            id = innerRegion.id, name = innerRegion.name, parentId = innerRegion.parentId
                        )
                    )
                }
            }
            innerList.add(
                AreaDTO(
                    id = region.id, name = region.name, parentId = region.parentId
                )
            )

        }
        return innerList
    }

    fun areaDTOinRegion(list: List<AreaDTO>): List<Region> {
        return list.map {
            Region(
                id = it.id,
                name = it.name,
                parentId = it.parentId
            )
        }
    }

    fun sortByAlfabeth(list: List<Region>): List<Region> {
        return list.sortedWith(compareBy { it.name })
    }
}
