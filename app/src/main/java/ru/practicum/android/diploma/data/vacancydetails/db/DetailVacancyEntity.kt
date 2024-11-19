package ru.practicum.android.diploma.data.vacancydetails.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vacancy_table")
data class DetailVacancyEntity(
    val hhID: String,
    val name: String,
    val isFavorite: Boolean,
    val areaName: String,
    val employerName: String,
    val employerLogoUrl: String?,
    val salaryTo: Int?,
    val salaryFrom: Int?,
    val salaryCurrency: String?,
    val addressCity: String?,
    val addressBuilding: String?,
    val addressStreet: String?,
    val addressDescription: String?,
    val experienceId: String?,
    val experienceName: String?,
    val employmentId: String?,
    val employmentName: String?,
    val scheduleId: String?,
    val scheduleName: String?,
    val description: String,
    val keySkills: String,
    val hhVacancyLink: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inner_vacancy_id")
    val innerId: Int = 0
)
