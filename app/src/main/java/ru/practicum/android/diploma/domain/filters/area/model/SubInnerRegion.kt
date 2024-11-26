package ru.practicum.android.diploma.domain.filters.area.model

data class SubInnerRegion(
    val id: String,
    val name: String,
    val parentId: String?,
    val innerRegions: List<SubSubInnerRegion>?
)
