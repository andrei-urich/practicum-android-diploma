package ru.practicum.android.diploma.data.utils

import android.util.Log
import ru.practicum.android.diploma.data.filters.area.dto.AreaDTO
import ru.practicum.android.diploma.data.filters.area.dto.InnerRegionDTO
import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.domain.filters.area.model.Region

class RegionsConverter {

    fun regionsToCountriesMapper(list: List<Region>): List<Region> {
        val countries = list.filter { region ->
            region.parentId == null
        }
        return countries
    }

    fun notEmptyRegions (list: List<RegionDTO>): List<RegionDTO> {
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

    fun regionsToAreaDTO(list: List<RegionDTO>): List<AreaDTO> {
        val countries = list.filter { region ->
            region.parentId != null
        }
        var areas = countries.map {
            AreaDTO(
                id = it.id,
                name = it.name,
                parentId = it.parentId
            )
        }

        Log.d("MY", "countries AreaDTO ${areas.toString()}")
        return areas
    }

    fun allInnerRegions(list: List<RegionDTO>): List<AreaDTO> {
        Log.d("MY", "List ${list.toString()}")
        val innerList = mutableListOf<AreaDTO>()
        val countries = onlyCountriesDTO(list)
        innerList.addAll(regionsToAreaDTO(countries))
        val notEmptyRegions = notEmptyRegions(list)
        notEmptyRegions.forEach { region ->
            innerList.addAll(getInnerList(region.innerRegions))
        }
        innerList.addAll(getRegionList(notEmptyRegions))

        Log.d("MY", "innerList ${innerList.toString()}")

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
