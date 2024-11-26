package ru.practicum.android.diploma.data.filters.area.dto

import com.google.gson.annotations.SerializedName

data class InnerRegionDTO(
    val id: String,
    val name: String,

    @SerializedName("parent_id")
    val parentId: String?
)
