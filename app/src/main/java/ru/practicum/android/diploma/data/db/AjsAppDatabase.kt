package ru.practicum.android.diploma.data.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [FavVacancyEntity::class]
)
abstract class AjsAppDatabase : RoomDatabase() {
    abstract fun favVacancyDao(): FavVacancyDao
}
