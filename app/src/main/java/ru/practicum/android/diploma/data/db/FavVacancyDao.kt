package ru.practicum.android.diploma.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavVacancyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavVacancy(favVacancy: FavVacancyEntity)

    @Query("DELETE from fav_vacancies WHERE vacancyId LIKE :favVacancyId")
    suspend fun deleteFavVacancyFromDB(favVacancyId: Int)

    @Query("SELECT * from fav_vacancies")
    suspend fun getAllFavVacancies(): List<FavVacancyEntity>

    @Query("SELECT * FROM fav_vacancies WHERE vacancyId LIKE :vacancyId")
    suspend fun getFavoriteVacancy(vacancyId: Int): FavVacancyEntity?
}
