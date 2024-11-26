package ru.practicum.android.diploma.data.utils

import ru.practicum.android.diploma.data.filters.area.dto.AreaDTO
import ru.practicum.android.diploma.data.filters.area.dto.InnerRegionDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.domain.filters.area.model.Region

class RegionsConverter {
    fun notEmptyRegions(list: List<RegionDTO>): List<RegionDTO> {
        val regions = list.filter { region ->
            region.innerRegions.isNotEmpty()
        }
        return regions
    }

    fun onlyCountriesDTO(list: List<RegionDTO>): List<RegionDTO> {
        val countries = list.filter { region ->
            region.parentId == null
        }
        return countries
    }

    fun bigCitiesDTO(list: List<RegionDTO>): List<RegionDTO> {
        val cities = list.filter { region ->
            region.innerRegions.isEmpty()
        }
        return cities
    }

    fun getRegionList(list: List<RegionDTO>): List<AreaDTO> {
        val innerList = mutableListOf<AreaDTO>()
        for (region in list) {
            if (region.innerRegions.isNotEmpty()) {
                innerList.add(
                    AreaDTO(
                        id = region.id,
                        name = region.name,
                        parentId = region.parentId
                    )
                )
            }
        }
        return innerList
    }

    fun getInnerList(list: List<InnerRegionDTO>): List<AreaDTO> {
        val innerList = mutableListOf<AreaDTO>()
        for (region in list) {
            if (region.innerRegions.isNotEmpty()) {
                for (subInnerRegion in region.innerRegions) {
                    innerList.add(
                        AreaDTO(
                            id = subInnerRegion.id,
                            name = subInnerRegion.name,
                            parentId = subInnerRegion.parentId
                        )
                    )
                }
                innerList.add(
                    AreaDTO(
                        id = region.id,
                        name = region.name,
                        parentId = region.parentId
                    )
                )
            }
        }
        return innerList
    }

    fun regionsDTOToAreaDTO(list: List<RegionDTO>): List<AreaDTO> {
        var areas = list.map {
            AreaDTO(
                id = it.id,
                name = it.name,
                parentId = it.parentId
            )
        }
        return areas
    }

    fun allInnerRegions(list: List<RegionDTO>): List<AreaDTO> {
        val innerList = mutableListOf<AreaDTO>()
        val countries = onlyCountriesDTO(list)
        innerList.addAll(regionsDTOToAreaDTO(countries))
        val bigCities = bigCitiesDTO(list)
        innerList.addAll(regionsDTOToAreaDTO(bigCities))
        val notEmptyRegions = notEmptyRegions(list)
        notEmptyRegions.forEach { region ->
            innerList.addAll(getInnerList(region.innerRegions))
        }
        innerList.addAll(getRegionList(notEmptyRegions))
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
