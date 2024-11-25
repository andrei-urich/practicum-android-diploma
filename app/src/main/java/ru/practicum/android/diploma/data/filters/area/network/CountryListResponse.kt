package ru.practicum.android.diploma.data.filters.area.network

import ru.practicum.android.diploma.data.filters.area.dto.CountryDTO
import ru.practicum.android.diploma.data.search.network.Response

data class CountryListResponse( val result: ArrayList<CountryDTO>?) : Response()
