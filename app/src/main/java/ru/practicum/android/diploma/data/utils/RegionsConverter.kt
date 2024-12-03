package ru.practicum.android.diploma.data.utils

import ru.practicum.android.diploma.data.filters.area.dto.AreaDTO
import ru.practicum.android.diploma.data.filters.area.dto.InnerRegionDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.domain.filters.area.model.Region

class RegionsConverter {
    fun onlyCountriesDTO(list: List<RegionDTO>): List<RegionDTO> {
        val countries = list.filter { region ->
            region.parentId == null
        }
        return countries
    }

    fun allInnerRegions(list: List<RegionDTO>): List<AreaDTO> {
        val innerList = mutableListOf<AreaDTO>()
        val childlessRegions = getChildlessRegionDTO(list)
        val regionsWithChildes = notEmptyRegions(list)
        innerList.addAll(regionsDTOToAreaDTO(childlessRegions))
        innerList.addAll(regionsDTOToAreaDTO(regionsWithChildes))
        regionsWithChildes.forEach { region ->
            innerList.addAll(getInnerList(region.innerRegions))
            innerList.addAll(innerRegionsDTOToAreaDTO(region.innerRegions))
        }
        return innerList
    }

    private fun getChildlessRegionDTO(list: List<RegionDTO>): List<RegionDTO> {
        val countries = list.filter { region ->
            region.innerRegions.isEmpty()
        }
        return countries
    }

    private fun notEmptyRegions(list: List<RegionDTO>): List<RegionDTO> {
        val regions = list.filter { region ->
            region.innerRegions.isNotEmpty()
        }
        return regions
    }

    private fun getInnerList(list: List<InnerRegionDTO>): List<AreaDTO> {
        val innerList = mutableListOf<AreaDTO>()
        for (innerRegion in list) {
            if (innerRegion.innerRegions.isNotEmpty()) {
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
        }
        return innerList
    }

    fun regionsDTOToAreaDTO(list: List<RegionDTO>): List<AreaDTO> {
        val areas = list.map {
            AreaDTO(
                id = it.id,
                name = it.name,
                parentId = it.parentId
            )
        }
        return areas
    }

    private fun innerRegionsDTOToAreaDTO(list: List<InnerRegionDTO>): List<AreaDTO> {
        val areas = list.map {
            AreaDTO(
                id = it.id,
                name = it.name,
                parentId = it.parentId
            )
        }
        return areas
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
