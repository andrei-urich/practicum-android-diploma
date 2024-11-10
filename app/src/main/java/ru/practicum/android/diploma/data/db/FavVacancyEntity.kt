package ru.practicum.android.diploma.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "fav_vacancies", indices = [Index("vacancyId", unique = true)])
data class FavVacancyEntity(
    // preview
    val name: String,
    val hirer: String,
    val areas: String,
    val salaryFrom: Int = -1,
    val salaryTo: Int = -1,
    val currency: String = "",
    val artLink: String = "",
    // full
    val experience: String = "",
    val schedule: String = "",
    val description: String = "",
    val keySkills: String = "",
    val type: String,
    val vacancyId: String = "",
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "inner_vacancy_id")
    val innerId: Int = 0
)
