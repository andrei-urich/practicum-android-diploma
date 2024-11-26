package ru.practicum.android.diploma.data.utils

import ru.practicum.android.diploma.data.filters.area.dto.AreaDTO
import ru.practicum.android.diploma.data.filters.area.dto.InnerRegionDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.data.filters.area.dto.SubInnerRegionDTO
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
        addAllRegions(innerList, list)
        for (region in list) {
            addAllInnerRegions(innerList, region.innerRegions)
            for (innerRegions in region.innerRegions) {
                addAllSubInnerRegions(innerList, innerRegions.innerRegions)
            }
        }
        return innerList
    }

    fun addAllRegions(innerList: MutableList<AreaDTO>, list: List<RegionDTO>) {
        for (region in list) {
            innerList.add(AreaDTO(region.id, region.name, region.parentId))
        }
    }

    fun addAllInnerRegions(innerList: MutableList<AreaDTO>, list: List<InnerRegionDTO>) {
        if (list.isNotEmpty()) {
            for (innerRegion in list) {
                innerList.add(AreaDTO(innerRegion.id, innerRegion.name, innerRegion.parentId))
            }
        }
    }

    fun addAllSubInnerRegions(innerList: MutableList<AreaDTO>, list: List<SubInnerRegionDTO>) {
        if (list.isNullOrEmpty()) {
            for (sunInnerRegion in list) {
                innerList.add(AreaDTO(sunInnerRegion.id, sunInnerRegion.name, sunInnerRegion.parentId))
            }
        }
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
