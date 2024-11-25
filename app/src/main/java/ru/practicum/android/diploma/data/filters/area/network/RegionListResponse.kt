package ru.practicum.android.diploma.data.filters.area.network

import ru.practicum.android.diploma.data.filters.area.dto.RegionDTO
import ru.practicum.android.diploma.data.search.network.Response

data class RegionListResponse (val regions: List<RegionDTO>?): Response()
