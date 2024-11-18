package ru.practicum.android.diploma.data.search.dto

import com.google.gson.annotations.SerializedName

data class EmployerDTO(
    val name: String,
    @SerializedName ("logo_urls")
    val logoUrls: LogoDTO?
)
