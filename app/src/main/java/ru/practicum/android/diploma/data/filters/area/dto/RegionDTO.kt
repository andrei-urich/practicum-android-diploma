package ru.practicum.android.diploma.data.filters.area.dto

import com.google.gson.annotations.SerializedName

data class RegionDTO(
    val id: Int,
    val name: String,
@SerializedName("parent_id")
    val parentId: String?
)
