package ru.practicum.android.diploma.domain.vacancydetails.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VacancyDetails(
    val id: Int,
    val name: String,
    val employerName: String,
    val employerLogoUrls: String,
    val salaryFrom: Int?,
    val salaryTo: Int?,
    val salaryCurrency: String?,
    val salaryGross: Boolean?,
    val experience: String?,
    val employment: String?,
    val schedule: String?,
    val description: String,
    val keySkills: List<String>,
    val hhVacancyLink: String,
    val city: String?,
    val building: String?,
    val street: String?,
) : Parcelable
