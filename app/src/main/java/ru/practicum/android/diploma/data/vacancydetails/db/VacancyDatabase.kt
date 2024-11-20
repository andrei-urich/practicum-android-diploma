package ru.practicum.android.diploma.data.vacancydetails.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(version = 4, entities = [DetailVacancyEntity::class], exportSchema = false)
abstract class VacancyDatabase : RoomDatabase() {
    abstract fun vacancyDao(): VacancyDao
}
